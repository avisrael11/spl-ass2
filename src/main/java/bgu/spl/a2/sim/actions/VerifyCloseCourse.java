package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

/**
 * Action belong to course actor
 */
public class VerifyCloseCourse extends Action<Boolean> {

    private String courseName;
    private String departmentName;

    private final String actionName = "Verify Close Course";


    public VerifyCloseCourse(String courseName, String departmentName){
        this.courseName     = courseName;
        this.departmentName = departmentName;

        setActionName(actionName);
    }

    /**
     * remove course from all registered student's grade shit
     */
    protected void start(){
        for(String student : ((CoursePrivateState)privateState).getRegStudents() ){
            ((StudentPrivateState)actorThreadPool.getPrivateState(student)).removeCourse(courseName);
        }

        ((CoursePrivateState) this.privateState).getRegStudents().clear();
        ((CoursePrivateState) this.privateState).setRegistered(0);
        ((CoursePrivateState) this.privateState).setAvailableSpots(-1);
        complete(true);

    }

    public String getId(){
        return Id;
    }
}
