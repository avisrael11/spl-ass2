package bgu.spl.a2.sim;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.sim.actions.*;
import bgu.spl.a2.sim.privateStates.ActionAndPrivateState;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * utility class for {@ComputerBuilder} and {@PhaseBuilder}, uses by {@Simulator}.
 *
 */

public class ActionFactory {

    /**
     * utility static method for Compuer building, uses by {@Simulator}.
     *
     * @param ComputersCollection
     *      JsonArray contain the information to build the computers
     */
    public static HashMap<String,Computer> ComputerBuilder(JsonArray ComputersCollection){
        HashMap<String,Computer> ComputersHashMap = new HashMap<>();
        for(int i = 0;i < ComputersCollection.size();i++){

            JsonObject Comp = ComputersCollection.get(i).getAsJsonObject();

            String type     = Comp.get("Type").getAsString();
            long SigSuccess = Comp.get("Sig Success").getAsLong();
            long SigFail    = Comp.get("Sig Fail").getAsLong();

            Computer x = new Computer(type);
            x.setSuccessSig(SigSuccess);
            x.setFailSig(SigFail);

            ComputersHashMap.put(type,x);
        }
        return ComputersHashMap;
    }

    /**
     * utility static method for run tasks in the part of Phases in JSON file, uses by {@Simulator}.
     *
     * @param actionsArray
     *      JsonArray contain the information to build the computers
     *
     * @param actionsArray
     *
     */
    public static LinkedList<ActionAndPrivateState> PhaseBuilder(JsonArray actionsArray, Warehouse warehouse){

        LinkedList<ActionAndPrivateState> actionAndPrivateStateLinkedList = new LinkedList<>();
        for (int i = 0; i < actionsArray.size(); i++) {
            JsonObject action = actionsArray.get(i).getAsJsonObject();
            String actionName = action.get("Action").getAsString();

            String departmentName;
            String courseName;
            String student;
            int space;

            switch (actionName){
                case "Open Course":
                    departmentName          = action.get("Department").getAsString();
                    courseName              = action.get("Course").getAsString();
                    JsonArray jsonPreReq    = action.get("Prerequisites").getAsJsonArray();
                    space                   = action.get("Space").getAsInt();
                    Vector<String> preReq   = new Vector<>();


                    for (JsonElement jsonElement : jsonPreReq) {
                        preReq.add(jsonElement.getAsString());
                    }


                    OpenCourseAction openNewCourse  = new OpenCourseAction(courseName,departmentName,space,preReq);
                    actionAndPrivateStateLinkedList.add(new ActionAndPrivateState(openNewCourse, new DepartmentPrivateState()));
                    break;

                case "Add Student":
                    departmentName  = action.get("Department").getAsString();
                    student         = action.get("Student").getAsString();

                    AddStudentAction addStudent = new AddStudentAction(student, departmentName);
                    actionAndPrivateStateLinkedList.add(new ActionAndPrivateState(addStudent, new DepartmentPrivateState()));
                    break;

                case "Participate In Course":
                    courseName          = action.get("Course").getAsString();
                    student             = action.get("Student").getAsString();
                    String tempGrade    = action.get("Grade").getAsString();
                    int grade           = -1;

                    if (!tempGrade.equals("-"))
                        grade = Integer.parseInt(tempGrade);

                    ParticipateInCourseAction participateInCourse = new ParticipateInCourseAction(student, courseName, grade);
                    actionAndPrivateStateLinkedList.add(new ActionAndPrivateState(participateInCourse, new CoursePrivateState()));
                    break;

                case "Unregister":
                    courseName  = action.get("Course").getAsString();
                    student     = action.get("Student").getAsString();
                    UnregisterAction unregister = new UnregisterAction(student, courseName);
                    actionAndPrivateStateLinkedList.add(new ActionAndPrivateState(unregister, new CoursePrivateState()));
                    break;

                case "Administrative Check":
                    departmentName     = action.get("Department").getAsString();
                    JsonArray students = action.get("Students").getAsJsonArray();

                    List<String> studentsId = new LinkedList<>();
                    for (JsonElement jsonElement : students) {//list of students
                        studentsId.add(jsonElement.getAsString());
                    }

                    JsonArray jsonConditions     = action.get("Conditions").getAsJsonArray();
                    List<String> conditionsList  = new LinkedList<>();
                    for (JsonElement jsonElement : jsonConditions) {
                        conditionsList.add(jsonElement.getAsString());
                    }

                    String computer = action.get("Computer").getAsString();
                    AdministrativeCheckAction administrativeCheck = new AdministrativeCheckAction(departmentName, studentsId,conditionsList,computer, warehouse);
                    actionAndPrivateStateLinkedList.add(new ActionAndPrivateState(administrativeCheck, new DepartmentPrivateState()));
                    break;

                case "Add Spaces":
                    courseName = action.get("Course").getAsString();
                    int spaces = action.get("Number").getAsInt();

                    AddSpacesAction addSpaces = new AddSpacesAction(courseName,spaces);
                    actionAndPrivateStateLinkedList.add(new ActionAndPrivateState(addSpaces, new CoursePrivateState()));
                    break;

                case "Register With Preferences":
                    student                 = action.get("Student").getAsString();

                    JsonArray coursesTemp   = action.get("Preferences").getAsJsonArray();
                    List<String> courses    = new LinkedList<>();
                    for (JsonElement jsonElement : coursesTemp) {//list of students
                        courses.add(jsonElement.getAsString());
                    }

                    JsonArray gradesTemp = action.get("Grade").getAsJsonArray();//grades!!
                    List<Integer> grades = new LinkedList<>();
                    for (JsonElement jsonElement : gradesTemp) {
                        if(jsonElement.getAsString().equals("-"))
                            grades.add(-1);
                        else
                            grades.add(jsonElement.getAsInt());
                    }

                    RegisterWithPreferencesAction registerStudent = new RegisterWithPreferencesAction(student,courses, grades);
                    actionAndPrivateStateLinkedList.add(new ActionAndPrivateState(registerStudent, new StudentPrivateState()));
                    break;

                case "Close Course":
                    departmentName   = action.get("Department").getAsString();
                    courseName       = action.get("Course").getAsString();

                    CloseCourseAction closeCourse = new CloseCourseAction(courseName,departmentName);
                    actionAndPrivateStateLinkedList.add(new ActionAndPrivateState(closeCourse, new DepartmentPrivateState()));
                    break;

                default:
                    break;
            }


        }
        return actionAndPrivateStateLinkedList;

    }
}

