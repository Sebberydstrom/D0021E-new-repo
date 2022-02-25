package Labb1;

import Sim.Event;
import Sim.Link;
import Sim.SimEnt;
import Sim.Message;

import java.util.Random;
import java.lang.Math;

/*
 From lab spec: Shall be delayed according to the { delay configured and jitter inferred }.
 (TODO):
 Need to fix code documentation.

 If arrivalTime = delay+jitter.
*/


public class LossyLink extends Link {
    // The delay time of the link.
    private double delay;
    // Jitter value decides the range that the delay will vary between.
    private double jitterRange;
    // Probability to loose a packet, 1, sets the packet loss probability to 1% and 10 to 10% etc.
    private double packetProbabilityLoss;
    // Identification for link, the links name
    private String name;
    // Random number generator.
    private Random random;

    // Constructor for Link, inherits SimEnt.
    public LossyLink(double delay, double jitterRange, double packetProbabilityLoss, String name) {
        super();
        if (jitterRange >= 0) {
            this.delay = delay;
            this.jitterRange = jitterRange;
            this.packetProbabilityLoss = packetProbabilityLoss;
            this.name = name;
            this.random = new Random();
        } else {
            throw new RuntimeException( "The jitter can never be negative" );
        }

    }

    // Returns a new random delay value - calculates the new delay time.
    // It will return a random value in range: (delaytime, (delaytime + value of jitterRange)).
    private double delayWithJitter() {
        double min = this.delay;
        double max = this.delay + this.jitterRange;
        return setDecimals( random.doubles( min, max ).findFirst().getAsDouble() );
    }

    // Sets so there are only two decimals in the delay value.
    private double setDecimals(double delayValue) {
        delayValue = delayValue * Math.pow( 10, 2 );
        delayValue = Math.floor( delayValue );
        delayValue = delayValue / Math.pow( 10, 2 );
        return delayValue;
    }

    // Function that gets called each time recv is called in the link,
    // determines if a packet should get dropped or not.
    private boolean willPackageGetDropped(Event ev) {
        int rand_value = random.ints( 1, 101 ).findFirst().getAsInt();
        if (rand_value < this.packetProbabilityLoss) {
            System.out.println("\nOps packet with seq: " + ((Message) ev).seq() + " from node: "
                    + ((Message) ev).source().networkId() + "." + ((Message) ev).source().nodeId()
                    + " got dropped at link: " + this.name + "\n"
            );
            return true;
        }
        return false;
    }


    // This is where the link will look at the Source and create a new event with send function
    // depending on if the source is connectorA or connectorB. Losing a packet on the link should be
    // the same as not creating a new event, cuz the SimEngine will automatically erase it after recv.
    public void recv(SimEnt src, Event ev) {

        if (ev instanceof Message) {
            if (!willPackageGetDropped( ev )) {
                System.out.println( "LossyLink recv msg with seq: " + ((Message) ev).seq() + " from node: " +
                        ((Message) ev).source().networkId() + "." + ((Message) ev).source().nodeId() );
                //System.out.print(" delay=" + this.delay + ", jitter=" + setDecimals(delayWithJitter() - this.delay) + "\n");
                if (src == _connectorA) {
                    send( _connectorB, ev, delayWithJitter() );
                } else {
                    send( _connectorA, ev, delayWithJitter() );
                }
            }

        }
    }
}
