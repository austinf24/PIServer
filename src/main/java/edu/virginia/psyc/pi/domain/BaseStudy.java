package edu.virginia.psyc.pi.domain;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 6/12/14
 * Time: 8:21 AM
 * The Participants progress through a series of sessions - This class is abstract
 * providing some common implementations of most Studies.
 */
public abstract class BaseStudy implements Study {

    private String currentName;
    private int taskIndex;
    private Date lastSessionDate;
    private List<TaskLog> taskLogs;

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BaseStudy.class);

    public BaseStudy(String currentName, int taskIndex, Date lastSessionDate, List<TaskLog> taskLogs) {
        this.currentName = currentName;
        this.taskIndex = taskIndex;
        this.lastSessionDate = lastSessionDate;
        this.taskLogs = taskLogs;
    }

    /**
     * @return Number of days since the last completed session.
     * Returns 99 if the user never logged in or completed a session.
     */
    public int daysSinceLastSession() {
        DateTime last;
        DateTime now;

        last = new DateTime(lastSessionDate);
        now  = new DateTime();
        return Days.daysBetween(last, now).getDays();
    }

    @Override
    public Session nextGiftSession() {

        boolean toCurrent = false;
        for (Session s : getSessions()) {
            if (toCurrent && s.getGiftAmount() > 0) return s;
            if (s.isCurrent()) toCurrent = true;
        }
        return null;
    }

    @Override
    public Session getCurrentSession() {
        List<Session> sessions = getSessions();
        for(Session s  : getSessions()) {
            if (s.isCurrent()) {
                return s;
            }
        }
        // If there is no current session, return the first session.
        sessions.get(0).setCurrent(true);
        return sessions.get(0);
    }

    @Override
    public int getCurrentTaskIndex() {
        return taskIndex;
    }

    @Override
    public Date getLastSessionDate() {
        return lastSessionDate;
    }

    @Override
    public void setLastSessionDate(Date date) {
        this.lastSessionDate = date;
    }

    @Override
    public void completeCurrentTask() {
        // If this is the last task in a session, then we move to the next session.
        if(taskIndex +1 >= getCurrentSession().getTasks().size()) {
            completeSession();
        } else { // otherwise we just increment the task index.
            this.taskIndex = taskIndex + 1;
        }
    }

    void completeSession() {
        List<Session> sessions = getSessions();
        boolean next = false;

        Session nextSession = getCurrentSession();
        for(Session s: sessions) {
            if(next == true) { nextSession = s; break; }
            if(s.isCurrent()) next = true;
        }

        this.taskIndex = 0;
        this.currentName = nextSession.getName();
        this.lastSessionDate = new Date();
    }





    public List<Session> getSessions() {
        List<Session> sessions = new ArrayList<Session>();
        boolean completed = true;
        boolean current = false;
        boolean gift;
        Session session;
        NAME  curName = NAME.valueOf(currentName);

        for (NAME name : NAME.values()) {
            if (name == curName) {
                completed = false;
                current = true;
            }
            gift = false;
            if(giftAmountCents(name) > 0) gift = true;
            if (!name.equals(NAME.ELIGIBLE)) {
                session = new Session(calcIndex(name), name.toString(), calculateDisplayName(name), completed, current, giftAmountCents(name), getTasks(name, taskIndex));
                sessions.add(session);
            }
            current = false;  // only one can be current.
        }
        return sessions;
    }



    /**
     * Given a session name, returns the next session name.
     */
    private static NAME nextSessionName(NAME last) {
        boolean nextOne = false;

        for (NAME name : NAME.values()) {
            if (nextOne) return name;
            if (name == last) nextOne = true;
        }
        return null;
    }

    public Session getLastSession() {
        NAME last = NAME.ELIGIBLE;

        for (NAME name : NAME.values()) {
            if (name == NAME.valueOf(currentName)) break;
            last = name;
        }
        return new Session(calcIndex(last), last.toString(), calculateDisplayName(last), true, false, giftAmountCents(last), getTasks(last,0));
    }




    /**
     * This method churns through the list of tasks, setting the "current" and "complete" flags based on the
     * current task index. It also uses the task logs to determine the completion date.
     */
    protected void setTaskStates(NAME sessionName, List<Task> tasks, int taskIndex) {
        int index = 0;
        for (Task t : tasks) {
            t.setCurrent(taskIndex == index);
            t.setComplete(taskIndex > index);
            for(TaskLog log : taskLogs) {
                if (log.getSessionName().equals(sessionName.toString()) && log.getTaskName().equals(t.getName())) {
                    t.setDateCompleted(log.getDateCompleted());
                    break;
                }
            }
            index++;
        }
    }

    /**
     * Returns true if the session is completed, false otherwise.
     * @param session
     * @return
     */
    public boolean completed(String session) {
        Session currentSession = getCurrentSession();
        if(session == currentSession.getName()) return false;
        for(NAME s : NAME.values()) {
            String strName = s.toString();
            if (strName.equals(session)) return true;
            if (strName.equals(currentSession.getName())) return false;
        }
        // You can't really get here, since we have looped through all
        // the possible values of session.
        return false;
    }


    @Override
    public STUDY_STATE getState() {

        if (getCurrentSession().getName().equals(NAME.COMPLETE.toString())) {
            return STUDY_STATE.ALL_DONE;
        }

        if (taskIndex != 0) return STUDY_STATE.IN_PROGRESS;

        // Pre Assessment, Session 1, and 'Complete' can be accessed immediately.
        if(getCurrentSession().getName().equals(NAME.PRE.toString()) ||
                getCurrentSession().getName().equals(NAME.SESSION1.toString()))
            return STUDY_STATE.READY;

        // If you are on the POST assessment, you need to wait 60 days after completing
        // session8
        if(getCurrentSession().getName().equals(NAME.POST.toString()) && daysSinceLastSession() < 60) {
            return STUDY_STATE.WAIT_FOR_FOLLOWUP;
        }

        // Otherwise, you must wait at least one day before starting the next
        // session.
        if(daysSinceLastSession() == 0 && lastSessionDate != null) return STUDY_STATE.WAIT_A_DAY;

        // Otherwise it's time to start.
        return STUDY_STATE.READY;
    }


}
