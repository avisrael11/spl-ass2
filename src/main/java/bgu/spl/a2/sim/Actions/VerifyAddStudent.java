package bgu.spl.a2.sim.Actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class VerifyAddStudent extends Action<Boolean> {
    private String studentId;
    private String departmentName;

    private final String actionName = "Verify Add Student";

    public VerifyAddStudent(String student, String departmentName){
        studentId           = student;
        this.departmentName = departmentName;

        Id = studentId;
        setActionName(actionName);
    }


    protected void start(){
        ((DepartmentPrivateState)actorThreadPool.getPrivateState(departmentName)).addStudent(studentId);

        complete(true);
    }

    public String getId(){
        return Id;
    }
}
