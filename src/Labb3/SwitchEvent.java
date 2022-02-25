package Labb3;

import Sim.Event;
import Sim.NetworkAddr;
import Sim.SimEnt;

public class SwitchEvent implements Event {
    // Behöver source - NetworkID och HostID.
    private int _currentNetworkID;
    private int _futureNetworkID;
    private int _nodeId;

    public SwitchEvent(int currentNetworkID, int futureNetworkID, int nodeId) {
        _currentNetworkID = currentNetworkID;
        _futureNetworkID = futureNetworkID;
        _nodeId = nodeId;
    }

    public int get_futureNetworkID() {
        return _futureNetworkID;
    }

    public int get_currentNetworkID() {
        return _currentNetworkID;
    }

    public int get_nodeId() {
        return _nodeId;
    }

    @Override
    public void entering(SimEnt locale) {

    }
}
