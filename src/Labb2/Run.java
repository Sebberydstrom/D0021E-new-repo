package Labb2;

import Sim.SimEngine;


public class Run {
    public static void main (String [] args)
    {
        // Create the network and set its components.
       NodeSinker sinker = setNetwork();

        // Start the simulation engine and of we go!
        Thread t = new Thread( SimEngine.instance());

        t.start();
        try
        {
            t.join();
        }
        catch (Exception e)
        {
            System.out.println("The motor seems to have a problem, time for service?");
        }
        //sinker.printarr();

    }

    public static NodeSinker setNetwork() {
        //Creates two links
        SimpleLink link1 = new SimpleLink();
        SimpleLink link2 = new SimpleLink();

        // Create two end hosts that will be
        // communicating via the router
        NodeGenerator host1 = new NodeGenerator(1,1);
        NodeSinker host2 = new NodeSinker(2,1);

        //Connect links to hosts
        host1.setPeer(link1);
        host2.setPeer(link2);

        // Creates as router and connect
        // links to it. Information about
        // the host connected to the other
        // side of the link is also provided
        // Note. A switch is created in same way using the Switch class
        SimpleRouter routeNode = new SimpleRouter(2);
        routeNode.connectInterface(0, link1, host1);
        routeNode.connectInterface(1, link2, host2);

        // Create CBR TimerEvent.
        CBRGenerator cbr = new CBRGenerator( 2, 1, 3, 5, 1 );
        // Create a Poisson TimerEvent.
        PoissonGenerator poi = new PoissonGenerator( 2, 1, 1000000, 1 );
        // Create a Gaussian TimerEvent.
        //GaussianGenerator gau = new GaussianGenerator( 2, 1, 10, 1 );

        // Generate some traffic
        host1.startGenerator(poi);
        return host2;
    }
}
