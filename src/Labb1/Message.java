package Labb1;

// This class implements an event that send a Message, currently the only
// fields in the message are who the sender is, the destination and a sequence 
// number

import Sim.Event;
import Sim.NetworkAddr;
import Sim.SimEnt;

public class Message implements Event {
	private NetworkAddr _source;
	private NetworkAddr _destination;
	private double _sentAt;
	private int _seq=0;
	
	Message (NetworkAddr from, NetworkAddr to, int seq, double sentAt)
	{
		_source = from;
		_destination = to;
		_seq=seq;
		_sentAt=sentAt;
	}
	
	public NetworkAddr source()
	{
		return _source; 
	}
	
	public NetworkAddr destination()
	{
		return _destination; 
	}
	
	public int seq()
	{
		return _seq; 
	}

	public double createdAt() {
		return _sentAt;
	}

	public void entering(SimEnt locale)
	{
	}
}
	
