package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

/**
 * Action belong to student actor
 */
public class VerifyParticipateInCourse extends Action<Boolean> {
    private String studentId;
    private String courseName;
    private int grade;

    private final String actionName = "Verify Participate In Course";

    public VerifyParticipateInCourse(String studentId, String courseName, int grade){
        this.studentId  = studentId;
        this.courseName = courseName;
        this.grade      = grade;

        setActionName(actionName);

    }

    /**
     * add course to student grade shit
     */
    protected void start(){
        ((StudentPrivateState)privateState).addCourse(courseName, grade);
        complete(true);
    }

    @Override
    public String getId() {
        return null;
    }
}
