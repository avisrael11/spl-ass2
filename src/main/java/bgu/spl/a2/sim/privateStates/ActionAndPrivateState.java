package bgu.spl.a2.sim.privateStates;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;

public class ActionAndPrivateState{
    Action action;
    PrivateState privateState;

    public ActionAndPrivateState(Action action, PrivateState privateState){
        this.action         = action;
        this.privateState   = privateState;
    }

    public Action getAction() {
        return action;
    }

    public PrivateState getPrivateState() {
        return privateState;
    }
}