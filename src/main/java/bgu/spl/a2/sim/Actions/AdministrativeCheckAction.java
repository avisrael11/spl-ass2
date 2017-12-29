package bgu.spl.a2.sim.Actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.Warehouse;

import java.util.List;

public class AdministrativeCheckAction extends Action{

    List<String> students;
    List<String> conditions;
    String computerType;
    Warehouse warehouse;


    public AdministrativeCheckAction(String departmentName, List<String> studentsList, List<String> conditionsList, String computerType, Warehouse warehouse){
        students        = studentsList;
        conditions      = conditionsList;
        computerType    = computerType;
        this.warehouse  = warehouse;
    }

    public void start(){

    }
}
