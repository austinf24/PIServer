package edu.virginia.psyc.pi.domain;

import edu.virginia.psyc.pi.service.EmailService;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 6/26/14
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParticipantTest {


    @Test
    public void testParticipantKnowsDaysSinceLastMilestone() {

        DateTime dateTime;

        Participant p;
        p = new Participant(1, "Dan Funk", "daniel.h.funk@gmail.com", false);
        dateTime = new DateTime().minus(Period.days(3));
        p.setStudy(new CBMStudy(CBMStudy.NAME.PRE.toString(), 0, dateTime.toDate(), new ArrayList<TaskLog>()));

        assertEquals(3,p.daysSinceLastMilestone());

        /*
        DateTime dt = new DateTime(2005, 3, 26, 12, 0, 0, 0);
        DateTime plusPeriod = dt.plus(Period.days(1));
        DateTime plusDuration = dt.plus(new Duration(24L*60L*60L*1000L));
        */

    }

    @Test
    public void testParticipantKnowsDaysSinceLastEmail() {

        Participant p;
        DateTime    dateTime;
        EmailLog    emailLog;

        dateTime = new DateTime().minus(Period.days(7));
        emailLog = new EmailLog(EmailService.TYPE.day7, dateTime.toDate());

        p = new Participant(1, "Dan Funk", "daniel.h.funk@gmail.com", false);
        assertEquals(99, p.daysSinceLastEmail());

        p.addEmailLog(emailLog);
        assertEquals(7, p.daysSinceLastEmail());

        dateTime = new DateTime().minus(Period.days(18));
        emailLog = new EmailLog(EmailService.TYPE.day18, dateTime.toDate());
        p.addEmailLog(emailLog);
        assertEquals(7, p.daysSinceLastEmail());

        dateTime = new DateTime().minus(Period.days(2));
        emailLog = new EmailLog(EmailService.TYPE.day2, dateTime.toDate());
        p.addEmailLog(emailLog);
        assertEquals(2, p.daysSinceLastEmail());


    }

    @Test
    public void testParticipantKnowsDateOfLastMilestone() {
        DateTime dateTime;
        Date loginDate;

        Participant p;
        p = new Participant(1, "Dan Funk", "daniel.h.funk@gmail.com", false);

        assertNull(p.lastMilestone());

        loginDate = new Date();
        p.setLastLoginDate(loginDate);
        assertNotNull(p.lastMilestone());

        dateTime = new DateTime().minus(Period.days(3));
        p.setStudy(new CBMStudy(CBMStudy.NAME.PRE.toString(), 0, dateTime.toDate(), new ArrayList<TaskLog>()));

        assertNotSame(p.lastMilestone(), loginDate);

    }



    @Test
    public void testNewParticipantGetsRandomCBMCondition() {

        Participant p;
        boolean is50 = false;
        boolean isPos = false;
        boolean isNeutral = false;


        p = new Participant(1, "Dan Funk", "daniel.h.funk@gmail.com", false);

        assertNotNull(p.getCbmCondition());

        for(int i = 0; i< 100; i++)  {
            p = new Participant(1, "Dan Funk", "daniel.h.funk@gmail.com", false);
            if(p.getCbmCondition().equals(Participant.CBM_CONDITION.FIFTY_FIFTY)) is50 = true;
            if(p.getCbmCondition().equals(Participant.CBM_CONDITION.POSITIVE)) isPos = true;
            if(p.getCbmCondition().equals(Participant.CBM_CONDITION.NEUTRAL)) isNeutral = true;
        }

        assertTrue("after 100 iterations, 50/50 should have occurred at least once", is50);
        assertTrue("after 100 iterations, Positive should have occurred at least once", isPos);
        assertTrue("after 100 iterations, Neutral should have occurred at least once", isNeutral);

    }

    @Test
    public void testNewParticipantGetsRandomPrime() {

        Participant p;
        boolean isAnxious = false;
        boolean isNeutral = false;

        p = new Participant(1, "Dan Funk", "daniel.h.funk@gmail.com", false);

        assertNotNull(p.getPrime());

        for(int i = 0; i< 100; i++)  {
            p = new Participant(1, "Dan Funk", "daniel.h.funk@gmail.com", false);
            if(p.getPrime().equals(Participant.PRIME.ANXIETY)) isAnxious = true;
            if(p.getPrime().equals(Participant.PRIME.NEUTRAL)) isNeutral = true;
        }

        assertTrue("after 100 iterations, ANXIETY should have occurred at least once", isAnxious);
        assertTrue("after 100 iterations, Neutral should have occurred at least once", isNeutral);

    }

    @Test
    public void testParticipantKnowsIfEmailOfTypeSentPreviously() {
        Participant p;

        p = new Participant(1, "Dan Funk", "daniel.h.funk@gmail.com", false);
        assertFalse(p.previouslySent(EmailService.TYPE.followup));
        p.addEmailLog(new EmailLog(EmailService.TYPE.followup, new Date()));
        assertTrue(p.previouslySent(EmailService.TYPE.followup));

    }

    @Test
    public void testNeutralParticipantInNeutralStudy() {

        Participant p;
        boolean isNeutral = false;

        for(int i = 0; i< 100; i++)  {
            p = new Participant(1, "Dan Funk", "daniel.h.funk@gmail.com", false);
            if(p.getCbmCondition().equals(Participant.CBM_CONDITION.NEUTRAL)) {
                isNeutral = true;
                assertTrue(p.getStudy() instanceof CBMNeutralStudy);
            }
        }

        assertTrue("after 100 iterations, Neutral should have occurred at least once", isNeutral);
    }

    @Test
    public void testPostStudy() {

        Participant p;
        p = new Participant(1, "Dan Funk", "daniel.h.funk@gmail.com", false);
        p.enterPostStudy();
        assertTrue(p.getStudy() instanceof CBMNeutralStudy);
    }

}
