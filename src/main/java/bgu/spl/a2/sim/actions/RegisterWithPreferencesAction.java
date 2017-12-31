package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.ArrayList;
import java.util.List;

public class RegisterWithPreferencesAction extends Action<Boolean> {

    private String studentId;
    private List<String> courses;
    private List<Integer> grades;

    private final String actionName = "Register With Preferences";

    public RegisterWithPreferencesAction(String studentId, List<String> courses, List<Integer> grades ){
        this.studentId  = studentId;
        this.courses    = courses;
        this.grades     = grades;

        setActionName(actionName);
        Id = studentId;
    }


    public void start(){
        privateState.addRecord(actionName);

        tryRegisterSequently();
    }

    private void tryRegisterSequently(){

        if (courses.size() > 0) {
            String courseName = courses.remove(0);
            Action<Boolean> participateInCourse = new ParticipateInCourseAction(studentId, courseName, grades.remove(0));

            sendMessage(participateInCourse, courseName, new CoursePrivateState());
            List<Action<Boolean>> actions = new ArrayList<>();
            actions.add(participateInCourse);

            then(actions, ()->{
                if(actions.get(0).getResult().get()){
                    complete(true);
                }
                else {
                    tryRegisterSequently();
                }
            });
        }
        else {
            complete(false);
        }
    }

    public String getId(){
        return Id;
    }
}
