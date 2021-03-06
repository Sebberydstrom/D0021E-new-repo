package Labb1;

// An example of how to build a topology and starting the simulation engine

import Sim.Router;
import Sim.SimEngine;


public class Run {
	public static void main (String [] args)
	{

			//runOne();
			runTwo();

			// Start the simulation engine and of we go!
			Thread t = new Thread( SimEngine.instance() );

			t.start();
			try {
				t.join();
			} catch (Exception e) {
				System.out.println( "The motor seems to have a problem, time for service?" );
			}



	}

	public static void runTwo() {
		//Creates two links
		LossyLink link1 = new LossyLink(1, 0.5, 10, "link1");
		LossyLink link2 = new LossyLink(1, 0.5, 20, "link2");

		// Create two end hosts that will be
		// communicating via the router
		NodeOne host1 = new NodeOne(1,1);
		NodeOne host2 = new NodeOne(2,1);

		//Connect links to hosts
		host1.setPeer(link1);
		host2.setPeer(link2);

		// Creates as router and connect
		// links to it. Information about
		// the host connected to the other
		// side of the link is also provided
		// Note. A switch is created in same way using the Switch class
		Router routeNode = new Router(2);
		routeNode.connectInterface(0, link1, host1);
		routeNode.connectInterface(1, link2, host2);

		// Generate some traffic
		// host1 will send 3 messages with time interval 5 to network 2, node 1. Sequence starts with number 1

		host1.StartSending( 2, 1, 3, 5, 1 );
			// host2 will send 2 messages with time interval 10 to network 1, node 1. Sequence starts with number 10
			//host2.StartSending(1, 1, 2, 10, 10);

	}

}
