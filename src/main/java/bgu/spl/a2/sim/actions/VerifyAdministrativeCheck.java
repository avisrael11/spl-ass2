package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

/**
 * Action class belong to student actor
 */
public class VerifyAdministrativeCheck extends Action<Boolean> {

    private long signature;

    private final String actionName = "Verify Administrative Check";

    public VerifyAdministrativeCheck(long signature){
        this.signature = signature;

        setActionName(actionName);
    }

    /**
     * assigning signature to private state
     */
    public void start(){
        ((StudentPrivateState)privateState).setSignature(signature);
        complete(true);
    }

    @Override
    public String getId() {
        return null;
    }
}
