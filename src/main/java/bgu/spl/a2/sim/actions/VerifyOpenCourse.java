package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.List;

public class VerifyOpenCourse extends Action<Boolean> {

    private String       courseName;
    private String       departmentName;
    private int          maxStudents;
    private List<String> preRequisites;

    public VerifyOpenCourse(String courseName, String departmentName, int maxStudents, List<String> preRequisites){
            this.courseName     = courseName;
            this.departmentName = departmentName;
            this.maxStudents    = maxStudents;
            this.preRequisites  = preRequisites;
    }

    protected void start(){
        ((CoursePrivateState)this.privateState).setPrequisites(this.preRequisites);
        ((CoursePrivateState)this.privateState).setAvailableSpots(this.maxStudents);
        complete(true);
    }

    @Override
    public String getId() {
        return null;
    }
}
