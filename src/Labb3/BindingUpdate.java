package Labb3;

import Sim.Event;
import Sim.NetworkAddr;
import Sim.SimEnt;

public class BindingUpdate implements Event {
    private String _message;
    private NetworkAddr _toNode;
    private NetworkAddr _from;

    public BindingUpdate(String message, NetworkAddr to) {
        _toNode = to;
        _message = message;
    }

    public void set_from(NetworkAddr from) {
        _from = from;
    }

    public NetworkAddr get_from() {
        return _from;
    }

    public String getMessage() {
        return _message;
    }

    public NetworkAddr get_toNode() {
        return _toNode;
    }

    @Override
    public void entering(SimEnt locale) {

    }
}
