package Labb2;

import Labb1.MessageOne;
import Sim.*;

import java.util.HashMap;

public class NodeSinker extends Node {
    private int _rcvdmsg=0;
    private double prevTime = 0;
    private HashMap<Integer, Integer> map = new HashMap<>();
    private int timeStamps[] = new int[1000000];
    private int counter = 0;

    public NodeSinker(int network, int node) {
        super(network, node);
    }

    // Sets so there are only two decimals in the value.
    private double setDecimals(double value) {
        value = value * Math.pow(10, 2);
        value = Math.floor(value);
        value = value / Math.pow(10, 2);
        return value;
    }

    public void log(int key) {
        Integer temp = map.get(key);
        if (temp == null) {
            temp = 0;
        }
        if (map.replace(key, temp + 1) == null) {
            map.put(key, temp + 1);
        }
    }

    public void printarr() {
        for (int i = 0; i<timeStamps.length; i++) {
            System.out.println(timeStamps[i]);
        }
    }

    public void recv(SimEnt src, Event ev) {
        if (ev instanceof Message)
        {
            // Relative sending time between each new message/package.
            double timeDiff = (_rcvdmsg > 0) ? (SimEngine.getTime() - prevTime) : 0;
            prevTime = ((MessageOne) ev).createdAt();
            log((int) timeDiff);
            timeStamps[counter] = (int)SimEngine.getTime();
            counter++;

            System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "
                    +((Message) ev).seq() + " at time "+setDecimals(SimEngine.getTime()) + "ms " +
                    " - Time relative last package sent: " + timeDiff + "ms" + "\n");
            _rcvdmsg++;
        }
    }

}
