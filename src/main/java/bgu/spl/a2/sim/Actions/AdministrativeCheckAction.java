package bgu.spl.a2.sim.Actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;
import java.util.List;

public class AdministrativeCheckAction extends Action{

    List<String> students;
    List<String> conditions;
    String departmentName;
    String computerType;
    Warehouse warehouse;

    private final String actionName = "Administrative Check";


    public AdministrativeCheckAction(String departmentName, List<String> studentsList, List<String> conditionsList, String computerType, Warehouse warehouse){
        students             = studentsList;
        conditions           = conditionsList;
        this.computerType    = computerType;
        this.departmentName  = departmentName;
        this.warehouse       = warehouse;

        setActionName(actionName);
        Id = departmentName;
    }

    public void start(){
        privateState.addRecord(actionName);

        Promise<Computer> promiseComputer = warehouse.getComputer(computerType);
        List<Action<Boolean>> actions = new ArrayList<>();

        promiseComputer.subscribe(()->{
            for (String studentName : students){
                long sig = promiseComputer.get().checkAndSign(conditions, ((StudentPrivateState)actorThreadPool.getPrivateState(studentName)).getGrades());
                Action<Boolean> action = new VerifyAdministrativeCheck(sig);
                actions.add(action);
                sendMessage(action,studentName, new StudentPrivateState());
            }
            promiseComputer.get().free();
        });

        then(actions,()->{
            complete(true);
        });
    }

    public String getId(){
        return departmentName;
    }
}
