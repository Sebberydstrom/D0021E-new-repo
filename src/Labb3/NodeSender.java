package Labb3;

import Sim.*;

public class NodeSender extends Node {

    public NodeSender(int network, int node) {
        super(network, node);
    }

    public void recv(SimEnt src, Event ev)
    {
        if (ev instanceof TimerEvent)
        {
            if (_stopSendingAfter > _sentmsg)
            {
                _sentmsg++;
                send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost),_seq),0);
                send(this, new TimerEvent(),_timeBetweenSending);
                System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" sent message with seq: "+_seq + " at time "+SimEngine.getTime());
                _seq++;
            }
        }
        if (ev instanceof Message)
        {
            System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());

        }
        if (ev instanceof BindingUpdate) {
            System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives BindingUpdate at time "+SimEngine.getTime());
            _toNetwork = ((BindingUpdate) ev).get_from().networkId();
            _toHost = ((BindingUpdate) ev).get_from().nodeId();
        }
    }
}
