package Labb3;

import Sim.*;

import javax.naming.Binding;
import java.util.Arrays;

public class NRouter extends Router {
    protected int _currInterface;
    public NRouter(int interfaces) {
        super(interfaces);
    }

    // Switch node's interface.
    public RouteTableEntry changeInterface(int oldNetworkID, int newNetworkID) {
        // The index in the array that we will add a (node, link)
        int newInterface = newNetworkID - 1;
        // The index in which we will remove the element.
        int oldInterface = oldNetworkID - 1;
        //System.out.println("Original array: " + Arrays.toString(_routingTable));

        RouteTableEntry tableEnt = new RouteTableEntry();
        // Retrieve the entry from the routing table.
        for (int i = 0; i < _routingTable.length; i++) {
            if(_routingTable != null && i == oldInterface) {
                tableEnt = _routingTable[i];
                // Update to new network id.
                ((Node) tableEnt.node())._id.set_networkId( newNetworkID );
                _routingTable[i] = null;
            }
        }
        // Update the routing table with the entry at the new interface.
        for (int i = 0; i < _routingTable.length; i++) {
            if (i == newInterface && tableEnt != null) {
                _routingTable[i] = tableEnt;
                ((Link) tableEnt.link()).setConnector( this );
                _currInterface = newInterface;
            }
        }
        return tableEnt;

    }

    public SimEnt getInterface(int networkAddress)
    {
        SimEnt routerInterface=null;
        for(int i=0; i<_interfaces; i++)
            if (_routingTable[i] != null)
            {
                // What happens if it exist multiple hosts in the same network?
                // I expect i wont be able to send to all host then, it will only send to one "the latest match in the
                // if sats" or do we need to implement a switch in that case that handles multiple hosts in the same LAN.
                if (((Node) _routingTable[i].node()).getAddr().networkId() == networkAddress)
                {
                    routerInterface = _routingTable[i].link();
                    _currInterface = i;
                }
            }
        return routerInterface;
    }

    public int[] getRoutingTable(int networkAddress) {
        int arr[] = new int[3];

        for (int i = 0; i < _interfaces; i++) {
            if (_routingTable[i] != null) {
                if (((Node) _routingTable[i].node()).getAddr().networkId() == networkAddress) {
                    arr[0] = ((Node) _routingTable[i].node())._id.networkId();
                    arr[1] = ((Node) _routingTable[i].node())._id.nodeId();
                    arr[2] = i;
                    _currInterface = i;
                }
            }
        }
        return arr;
    }

    // Fundera hur du ska göra med nätverks ID, ska det sparas eller ska man strunta i att ändra det?

    public void recv(SimEnt source, Event event)
    {

        if (event instanceof Message)
        {
            System.out.println("Router handles packet with seq: " + ((Message) event).seq()+" from node: "+((Message) event).source().networkId()+"." + ((Message) event).source().nodeId() );
            SimEnt sendNext = getInterface(((Message) event).destination().networkId());
            if (sendNext != null) {
                System.out.println("Router sends to node: " + ((Message) event).destination().networkId()+"." + ((Message) event).destination().nodeId());
                send (sendNext, event, _now);
            } else {
                System.out.println("Msg got dropped, no node registered in table at: " + ((Message) event).destination().networkId()+"." + ((Message) event).destination().nodeId());
            }


        }
        if (event instanceof SwitchEvent) {
            //System.out.println("Node: " + ((SwitchEvent) event).get_id().nodeId() + "want to register to network: " + ((SwitchEvent) event).get_id().networkId());
            int temp[] = getRoutingTable( ((SwitchEvent) event).get_currentNetworkID() );
            System.out.println("Routing table: " + "interface: " + _currInterface + " Node: " +
                    temp[1] + " Network: " + temp[0] );
            System.out.println("Router updates interface....");
            RouteTableEntry ent = changeInterface( ((SwitchEvent) event).get_currentNetworkID(), ((SwitchEvent) event).get_futureNetworkID() );
            System.out.println("Routing table updated: " + "interface: " + _currInterface + " Node: " +
                    ((Node) ent.node())._id.nodeId() + " Network: " + ((Node) ent.node())._id.networkId() );
            // Skapa ett nytt message till source att ett interfacet har byts.
            String msg = "Router has succesfully registered Node:" + ((Node) ent.node())._id.nodeId() + " At network:" + ((Node) ent.node())._id.networkId() +
                    " to interface:" + _currInterface;
            send(ent.link(), new BindingUpdate( msg, ((Node) ent.node())._id), 0);
        }
        if (event instanceof BindingUpdate) {
            System.out.println("Router received BindingUpdate");
            System.out.println("Router sends to:" + ((BindingUpdate) event).get_toNode().networkId() + "." + ((BindingUpdate) event).get_toNode().nodeId());
            SimEnt sendNext = getInterface(((BindingUpdate) event).get_toNode().networkId());
            send(sendNext, event, 0);
        }
    }
}
