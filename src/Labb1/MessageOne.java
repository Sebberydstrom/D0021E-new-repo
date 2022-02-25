package Labb1;
import Sim.Message;
import Sim.NetworkAddr;

public class MessageOne extends Message {
    private double _sentAt;

    public MessageOne(NetworkAddr from, NetworkAddr to, int seq, double sentAt)
    {
        super(from, to, seq);
        _sentAt=sentAt;
    }

    public double createdAt() {
        return _sentAt;
    }
}
