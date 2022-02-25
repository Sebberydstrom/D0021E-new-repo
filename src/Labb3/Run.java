package Labb3;

import Sim.Link;
import Sim.Node;
import Sim.Router;
import Sim.SimEngine;

public class Run {
    public static void main (String [] args)
    {
        // Creates the network and all its components.
        setNetwork();

        // Start the simulation engine and of we go!
        Thread t=new Thread( SimEngine.instance());

        t.start();
        try
        {
            t.join();
        }
        catch (Exception e)
        {
            System.out.println("The motor seems to have a problem, time for service?");
        }

    }

    public static void setNetwork() {
        //Creates two links
        Link link1 = new Link();
        Link link2 = new Link();

        // Create two end hosts that will be
        // communicating via the router
        NodeSwitcher host1 = new NodeSwitcher(1,1);
        host1.setSwitchData( 20, 3, 1, 2 );
        NodeSender host2 = new NodeSender(2,1);

        //Connect links to hosts
        host1.setPeer(link1);
        host2.setPeer(link2);

        // Creates as router and connect
        // links to it. Information about
        // the host connected to the other
        // side of the link is also provided
        // Note. A switch is created in same way using the Switch class
        NRouter routeNode = new NRouter(4);
        routeNode.connectInterface(0, link1, host1);
        routeNode.connectInterface(1, link2, host2);

        // Generate some traffic
        // host1 will send 3 messages with time interval 5 to network 2, node 1. Sequence starts with number 1
        //host1.StartSending(2, 1, 5, 5, 1);
        // host2 will send 2 messages with time interval 10 to network 1, node 1. Sequence starts with number 10
        host2.StartSending(1, 1, 5, 10, 10);
    }
}
