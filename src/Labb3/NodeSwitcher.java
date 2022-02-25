package Labb3;

import Sim.*;

public class NodeSwitcher extends Node {
    protected int oldNetworkID;
    protected int _switchAtTime, _switchToNetwork;
    private boolean switcher = false;

    public NodeSwitcher(int network, int node) {
        super(network, node);
    }

    // Settings when to switch network and to which interface/network at router.
    public void setSwitchData(int switchAtTime, int switchNetwork, int toHost, int toNetwork) {
        _switchAtTime = switchAtTime;
        _switchToNetwork = switchNetwork;
        _toHost = toHost;
        _toNetwork = toNetwork;
    }

    // Change network. If you switch to a new interface on router the node need to update it's network id.
    public void saveOldNetworkId() {
        oldNetworkID = _id.networkId();
    }


    public void recv(SimEnt src, Event ev)
    {
        if (SimEngine.getTime() == _switchAtTime) {
            saveOldNetworkId();
            send(_peer, new SwitchEvent( _id.networkId(), _switchToNetwork, _id.nodeId() ), 2);
            System.out.println("Node: " + _id.networkId() + "." + _id.nodeId() + " Sends new request to Router to switch to network: " + _switchToNetwork);
            switcher = true;
        }

        if (ev instanceof TimerEvent)
        {

            if (_stopSendingAfter > _sentmsg)
            {
                _sentmsg++;
                send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost),_seq),switcher ? 10 : 0);
                send(this, new TimerEvent(),_timeBetweenSending);
                System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" sent message with seq: "+_seq + " at time "+SimEngine.getTime());
                _seq++;
                switcher = false;
            }
        }

        if (ev instanceof Message)
        {
            System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());

        }
        if (ev instanceof BindingUpdate) {
            //updateNetwork(); - Om man ändrar i router för node objektet ändras de även här
            // Fixa så att den gamla addressen alltid sparas/den senaste.
            System.out.println("Node "+ oldNetworkID + "." + _id.nodeId() +" receives BindingUpdate: " +((BindingUpdate) ev).getMessage() );
            // Skicka uppdatering till host2.
            BindingUpdate bind = new BindingUpdate( "Sends new Network ID", new NetworkAddr( _toNetwork, _toHost ));
            bind.set_from( _id );
            send(_peer, bind, 0);
        }
    }

}
