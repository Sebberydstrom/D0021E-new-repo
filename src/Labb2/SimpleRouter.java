package Labb2;

import Sim.Event;
import Sim.Message;
import Sim.Router;
import Sim.SimEnt;

public class SimpleRouter extends Router {

    SimpleRouter(int interfaces) {
        super(interfaces);
    }

    // When messages are received at the router this method is called

    public void recv(SimEnt source, Event event)
    {

        if (event instanceof Message)
        {
            //System.out.println("Router handles packet with seq: " + ((Message) event).seq()+" from node: "+((Message) event).source().networkId()+"." + ((Message) event).source().nodeId() );
            SimEnt sendNext = getInterface(((Message) event).destination().networkId());
            //System.out.println("Router sends to node: " + ((Message) event).destination().networkId()+"." + ((Message) event).destination().nodeId());
            send (sendNext, event, _now);

        }
    }
}
