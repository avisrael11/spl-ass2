package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
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

        this.Id         = courseName;

        setActionName(actionName);
    }

    public void start(){
        privateState.addRecord(actionName);

        System.out.println(" Participate: start " + "student: " + studentId +" course: " + courseName + " " + Thread.currentThread().getId());

        if(isRegistered() || !isSpace() || !preRequisitesMet()){
            complete(false);
        }
        else{
            ((CoursePrivateState)privateState).registerStudent(studentId);

            List<Action<Boolean>> actions = new ArrayList<>();
            Action<Boolean> verifyParticipateInCourse = new VerifyParticipateInCourse(studentId, courseName, grade);
            actions.add(verifyParticipateInCourse);

            sendMessage(verifyParticipateInCourse, studentId, new StudentPrivateState());

            then(actions, ()->{
                System.out.println("running lambda " + Thread.currentThread().getId());
                complete(actions.get(0).getResult().get());
            });
        }
    }

    private boolean isRegistered(){
        return ((CoursePrivateState)privateState).getRegStudents().contains(studentId);
    }

    private boolean preRequisitesMet(){
        List<String> preRequisites = ((CoursePrivateState)privateState).getPrequisites();
        System.out.println(" Participate: preRequisitesMet " + "student: " + studentId +" course: " + courseName + " " + Thread.currentThread().getId());
        PrivateState p = actorThreadPool.getPrivateState(studentId);
        if(p == null){
            System.out.println("pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp Participate: student private state not found " + "student: " + studentId +" course: " + courseName + " " + Thread.currentThread().getId());
        }
        HashMap<String, Integer> studentGrades = (((StudentPrivateState)actorThreadPool.getPrivateState(studentId)).getGrades());
        for(String course : preRequisites){
            if ( !studentGrades.containsKey(course) || !(studentGrades.get(course) >= passGrade) ){
                return false;
            }
        }
        return true;
    }

    private boolean isSpace(){
        return ((CoursePrivateState)privateState).getAvailableSpots() > ((CoursePrivateState)privateState).getRegistered() ;
    }

    public String getId(){
        return Id;
    }
}
