package bgu.spl.a2.sim.Actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class AddSpacesAction extends Action<Boolean> {

    private int spaces;


    private final String actionName = "Add Spaces";

    public AddSpacesAction(String courseName, int spaces){
        this.spaces = spaces;
        this.Id     = courseName;

        this.setActionName(actionName);
    }

    @Override
    protected void start(){
        privateState.addRecord(getActionName());

        boolean succeeded = ((CoursePrivateState)privateState).addSpaces(spaces);
        complete(succeeded);
    }

    @Override
    public String getId() {
        return Id;
    }
}
