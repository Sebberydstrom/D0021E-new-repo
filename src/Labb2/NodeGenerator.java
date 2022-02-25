package Labb2;
import Labb1.MessageOne;
import Sim.*;

public class NodeGenerator extends Node {
    private int _rcvdmsg=0;
    private double prevPkgTime = 0;

    public NodeGenerator(int network, int node) {
        super(network, node);
    }

    // Sets so there are only two decimals in the value.
    private double setDecimals(double value) {
        value = value * Math.pow(10, 2);
        value = Math.floor(value);
        value = value / Math.pow(10, 2);
        return value;
    }

    public void startGenerator(TimerGenerator ev) {
        _toNetwork = ev.get_toNetwork();
        _toHost = ev.get_toHost();
        _seq = ev.get_startSeq();
        _stopSendingAfter = ev.get_StopSendingAfter();
        _timeBetweenSending = ev.get_timeBetweenSending();

        if (ev instanceof CBRGenerator) {
            send( this, ev, 0 );
        }
        if (ev instanceof PoissonGenerator) {
            send(this, ev, ((PoissonGenerator) ev).getRandomTime());
        }
        if (ev instanceof GaussianGenerator) {
            send(this, ev, ((GaussianGenerator) ev).getGaussianTime());
        }
    }

    public void recv(SimEnt src, Event ev) {

        if (ev instanceof TimerGenerator)
        {
            if (_stopSendingAfter > _sentmsg) {
                if (ev instanceof CBRGenerator) {
                        _sentmsg++;
                        send( _peer, new MessageOne( _id, new NetworkAddr( _toNetwork, _toHost ), _seq, SimEngine.getTime() ), 0 );
                        send( this, ev, _timeBetweenSending );
                        System.out.println( "Node " + _id.networkId() + "." + _id.nodeId() + " sent message with seq: " + _seq + " at time " + SimEngine.getTime() +
                                "ms");
                        _seq++;
                }

                if (ev instanceof PoissonGenerator) {
                        _sentmsg++;
                        int genTime = ((PoissonGenerator) ev).getRandomTime();
                        send( _peer, new MessageOne( _id, new NetworkAddr( _toNetwork, _toHost ), _seq, SimEngine.getTime() ), 0 );
                        send( this, ev, genTime );
                        System.out.println( "Node " + _id.networkId() + "." + _id.nodeId() + " sent message with seq: " + _seq + " at time " + SimEngine.getTime() +
                                "ms");
                        _seq++;

                }

                if (ev instanceof GaussianGenerator) {
                        _sentmsg++;
                        send( _peer, new MessageOne( _id, new NetworkAddr( _toNetwork, _toHost ), _seq, SimEngine.getTime() ), 0 );
                        send( this, ev, ((GaussianGenerator) ev).getGaussianTime() );
                        System.out.println( "Node " + _id.networkId() + "." + _id.nodeId() + " sent message with seq: " + _seq + " at time " + SimEngine.getTime() +
                              "ms");
                        _seq++;
                }
            }
        }

    }


}
