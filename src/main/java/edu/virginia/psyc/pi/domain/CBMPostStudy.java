package edu.virginia.psyc.pi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 6/12/14
 * Time: 8:21 AM
 * The Participants progress through a series of sessions
 */
public class CBMPostStudy implements Study {

    public CBMPostStudy(String currentName, int taskIndex, Date lastSessionDate, List<TaskLog> taskLogs) {
    }

    @Override
    protected List<Task> getTasks(CBMStudy.NAME name, int taskIndex) {

        List<Task> tasks = new ArrayList<Task>();
        switch (name) {
            case SESSION1:
                tasks.add(new Task("FirstSessionSentences", "Training stories", Task.TYPE.playerScript, 20));
                tasks.add(new Task("OA", "Anxiety review", Task.TYPE.questions, 1));
                break;
            case SESSION2:
                tasks.add(new Task("SecondSessionSentences", "Training stories", Task.TYPE.playerScript, 20));
                tasks.add(new Task("OA", "Anxiety review", Task.TYPE.questions, 1));
                break;
            case SESSION3:
                tasks.add(new Task("ThirdSessionSentences", "Training stories", Task.TYPE.playerScript, 20));
                tasks.add(new Task("OA", "Anxiety review", Task.TYPE.questions, 1));
                break;
            case SESSION4:
                tasks.add(new Task("FourthSessionSentences", "Training stories", Task.TYPE.playerScript, 20));
                tasks.add(new Task("OA", "Anxiety review", Task.TYPE.questions, 1));
                break;
            case SESSION5:
                tasks.add(new Task("FifthSessionSentences", "Training stories", Task.TYPE.playerScript, 20));
                tasks.add(new Task("OA", "Anxiety review", Task.TYPE.questions, 1));
                break;
            case SESSION6:
                tasks.add(new Task("SixthSessionSentences", "Training stories", Task.TYPE.playerScript, 20));
                tasks.add(new Task("OA", "Anxiety review", Task.TYPE.questions, 1));
                break;
            case SESSION7:
                tasks.add(new Task("SeventhSessionSentences", "Training stories", Task.TYPE.playerScript, 20));
                tasks.add(new Task("OA", "Anxiety review", Task.TYPE.questions, 1));
                break;
            case SESSION8:
                tasks.add(new Task("EighthSessionSentences", "Training stories", Task.TYPE.playerScript, 20));
                tasks.add(new Task("OA", "Anxiety review", Task.TYPE.questions, 1));
                break;
        }
        setTaskStates(name, tasks, taskIndex);
        return tasks;
    }


    @Override
    public List<Session> getSessions() {
        return null;
    }

    @Override
    public Session getLastSession() {
        return null;
    }

    @Override
    public Session getCurrentSession() {
        return null;
    }

    @Override
    public Session nextGiftSession() {
        return null;
    }

    @Override
    public void completeCurrentTask() {

    }

    @Override
    public int getCurrentTaskIndex() {
        return 0;
    }

    @Override
    public Date getLastSessionDate() {
        return null;
    }

    @Override
    public void setLastSessionDate(Date date) {

    }

    @Override
    public STUDY_STATE getState() {
        return null;
    }

    @Override
    public boolean completed(String sessionName) {
        return false;
    }
}
