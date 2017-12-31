package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

/**
 * Action belong to student actor
 */
public class VerifyUnregister extends Action<Boolean> {

    private String courseName;

    private final String actionName = "Verify Unrefister";

    public VerifyUnregister( String courseName){
        this.courseName = courseName;

        setActionName(actionName);
    }

    /**
     * removing course from private state's grades list
     */
    protected void start(){
        ((StudentPrivateState)privateState).getGrades().remove(courseName);
        complete(true);
    }

    @Override
    public String getId() {
        return null;
    }
}
