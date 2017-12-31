package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class AddStudentAction extends Action<Boolean>{
    private String studentId;
    private String departmentName;

    private final String actionName = "Add Student";

    public AddStudentAction(String studentId, String departmentName){
        this.studentId      = studentId;
        this.departmentName = departmentName;

        Id = departmentName;
        setActionName(actionName);
    }

    public void start(){
        privateState.addRecord(actionName);

        Action<Boolean> addStudent      = new VerifyAddStudent(studentId, departmentName);
        List<Action<Boolean>> actions   = new ArrayList<>();
        actions.add(addStudent);

        sendMessage(addStudent, studentId, new StudentPrivateState());

        then(actions, ()->{
            complete(actions.get(0).getResult().get());
        });

    }

    public String getId(){
        return Id;
    }
}
