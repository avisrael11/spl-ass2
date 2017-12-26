package bgu.spl.a2.sim.Actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class CloseCourseAction extends Action<Boolean> {
    private String courseName;
    private String departmentName;

    private final String actionName = "Close Course";

    public CloseCourseAction(String courseName, String departmentName){
        this.courseName     = courseName;
        this.departmentName = departmentName;

        this.Id = departmentName;
        setActionName(actionName);
    }


    protected void start(){
        ((DepartmentPrivateState)privateState).removeCourse(courseName);
        privateState.addRecord(actionName);

        List<Action<Boolean>> actions          = new ArrayList<>();
        Action<Boolean> closeCourse = new VerifyCloseCourse(courseName, departmentName);

        actions.add(closeCourse);
        sendMessage(closeCourse, courseName, new CoursePrivateState());

        then(actions, () ->{
            complete(actions.get(0).getResult().get());
        });

    }

    public String getId(){
        return Id;
    }
}
