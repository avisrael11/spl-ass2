package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class UnregisterAction extends Action<Boolean> {

    private String studentId;
    private String courseName;

    private final String actionName = "Unregister";

    public UnregisterAction(String studentId, String courseName){
        this.studentId  = studentId;
        this.courseName = courseName;

        this.Id = courseName;
        setActionName(actionName);
    }
    public void start(){
        privateState.addRecord(actionName);
        ((CoursePrivateState)privateState).unRegisterStudent(studentId);

        Action<Boolean> verifyUnrefister = new VerifyUnregister(courseName);
        List<Action<Boolean>> actions    = new ArrayList<>();
        actions.add(verifyUnrefister);

        sendMessage(verifyUnrefister, studentId, new StudentPrivateState());

        then(actions, ()->{
            complete(actions.get(0).getResult().get());
        });
    }

    public String getId() {
        return Id;
    }
}
