package Labb2;

import Sim.TimerEvent;

public class TimerGenerator extends TimerEvent{
    private int _toNetwork;
    private int _toHost;
    private int _StopSendingAfter;
    private int _timeBetweenSending;
    private int _startSeq;

    public TimerGenerator(int network, int node, int number, int timeInterval, int startSeq) {
        super();
        _toNetwork = network;
        _toHost = node;
        _StopSendingAfter = number;
        _timeBetweenSending = timeInterval;
        _startSeq = startSeq;
    }

    // Getters for the node, to set values.
    public int get_toNetwork() {
        return _toNetwork;
    }
    public int get_toHost() {
        return _toHost;
    }
    public int get_StopSendingAfter() {
        return _StopSendingAfter;
    }
    public int get_timeBetweenSending() {
        return _timeBetweenSending;
    }
    public int get_startSeq() {
        return _startSeq;
    }
}
