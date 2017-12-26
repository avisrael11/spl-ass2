package bgu.spl.a2.sim.Actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class AddSpacesAction extends Action {

    private int spaces;
    private String Id;

    private final String actionName = "Add Spaces";

    public AddSpacesAction(String courseName, int spaces){

        this.spaces = spaces;
        this.Id = courseName;
        this.setActionName(actionName);
    }

    protected void start(){
        privateState.addRecord(getActionName());

        boolean succeeded = ((CoursePrivateState)privateState).addSpaces(spaces);
        complete(succeeded);
    }

    public String getId() {
        return Id;
    }
}
