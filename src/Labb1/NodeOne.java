package Labb1;
import Sim.*;
import Sim.Node;

public class NodeOne extends Node{
    private int _rcvdmsg=0;
    private double prevPkgTime = 0;

    public NodeOne(int network, int node) {
        super(network, node);
    }

    // Sets so there are only two decimals in the value.
    private double setDecimals(double value) {
        value = value * Math.pow(10, 2);
        value = Math.floor(value);
        value = value / Math.pow(10, 2);
        return value;
    }

    // This method is called upon that an event destined for this node triggers.
    public void recv(SimEnt src, Event ev)
    {
        if (ev instanceof TimerEvent)
        {
            if (_stopSendingAfter > _sentmsg)
            {
                _sentmsg++;
                send(_peer, new MessageOne(_id, new NetworkAddr(_toNetwork, _toHost),_seq, SimEngine.getTime()),0);
                send(this, new TimerEvent(),_timeBetweenSending);
                System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" sent message with seq: "+_seq +
                        " at time "+setDecimals(SimEngine.getTime()));
                _seq++;
            }
        }
        if (ev instanceof Message)
        {
            double currPkgTime = setDecimals(SimEngine.getTime() - ((MessageOne) ev).createdAt());
            double devTime = (_rcvdmsg > 0) ? (this.prevPkgTime - currPkgTime) : 0;
            if (devTime < 0) { devTime = -devTime; };
            this.prevPkgTime = currPkgTime;

            System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "
                    +((Message) ev).seq() + " at time "+setDecimals(SimEngine.getTime()) +
                    " - Relative transit time: " + currPkgTime + "ms" + " - Deviation from last package: "
                    + setDecimals(devTime) + "\n");
            _rcvdmsg++;
        }
    }

}

