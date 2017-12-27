package bgu.spl.a2.sim.Actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParticipateInCourseAction extends Action<Boolean> {

    private String studentId;
    private String courseName;
    private int grade;

    private final int passGrade = 56;

    private final String actionName = "Participate In Course";

    public ParticipateInCourseAction(String studentId, String courseName, int grade){
        this.courseName = courseName;
        this.studentId  = studentId;
        this.grade      = grade;

        setActionName(actionName);
    }

    public void start(){
        privateState.addRecord(actionName);

        if(isRegistered() || !preRequisitesMet() || !isSpace()){
            complete(false);
        }
        else{
            List<Action<Boolean>> actions = new ArrayList<>();
            Action<Boolean> verifyParticipateInCourse = new VerifyParticipateInCourse(studentId, courseName, grade);
            actions.add(verifyParticipateInCourse);

            sendMessage(verifyParticipateInCourse, courseName, actorThreadPool.getPrivateState(studentId));

            then(actions, ()->{
                complete(actions.get(0).getResult().get());
            });
        }
    }

    private boolean isRegistered(){
        return ((CoursePrivateState)privateState).getRegStudents().contains(studentId);
    }

    private boolean preRequisitesMet(){
        List<String> preRequisites = ((CoursePrivateState)actorThreadPool.getPrivateState(courseName)).getPrequisites();
        HashMap<String, Integer> studentGrades = (((StudentPrivateState)actorThreadPool.getPrivateState(courseName)).getGrades();
        for(String course : preRequisites){
            if ( !studentGrades.containsKey(course) || !(studentGrades.get(course) >= passGrade) ){
                return false;
            }
        }
        return true;
    }

    private boolean isSpace(){
        return ((CoursePrivateState)privateState).getAvailableSpots() > ((CoursePrivateState)privateState).getRegistered();
    }
}
