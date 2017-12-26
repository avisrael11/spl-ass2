package bgu.spl.a2.sim.Actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class OpenCourseAction extends Action {

    private String  courseName;
    private int     maxStudents;
    List            preRequisites;

    private final String actionName = "Open Course";

    public OpenCourseAction(String courseName, String departmentName, int maxStudents, List preRequisits){
        this.courseName     = courseName;
        this.Id             = departmentName;
        this.maxStudents    = maxStudents;
        this.preRequisites  = preRequisits;

        setActionName(actionName);
    }

    protected void start(){
        privateState.addRecord(actionName);

        List<Action<Boolean>> actions = new ArrayList<>();

        VerifyOpenCourse openCourse = new VerifyOpenCourse(courseName, Id, maxStudents, preRequisites);
        actions.add(openCourse);

        sendMessage(openCourse, courseName, new CoursePrivateState());

        then(actions,()->{
            boolean result = actions.get(0).getResult().get();

            if(result){
                ((DepartmentPrivateState)privateState).addCourse(courseName);
            }
            complete(result);
        });



    }

    public String getId() {
        return Id;
    }
}
