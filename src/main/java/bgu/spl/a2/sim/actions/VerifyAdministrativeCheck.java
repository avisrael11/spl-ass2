package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class VerifyAdministrativeCheck extends Action<Boolean> {

    private long signature;

    private final String actionName = "Verify Administrative Check";

    public VerifyAdministrativeCheck(long signature){
        this.signature = signature;

        setActionName(actionName);
    }

    public void start(){
        ((StudentPrivateState)privateState).setSignature(signature);
        complete(true);
    }

    @Override
    public String getId() {
        return null;
    }
}
