package Labb1;

// This class implements a node (host) it has an address, a peer that it communicates with
// and it count messages send and received.

import Sim.*;

public class Node extends SimEnt {
	private NetworkAddr _id;
	private SimEnt _peer;
	private int _sentmsg=0;
	private int _rcvdmsg=0;
	private int _seq = 0;
	public double prevPkgTime = 0;

	
	public Node (int network, int node)
	{
		super();
		_id = new NetworkAddr(network, node);
	}	
	
	
	// Sets the peer to communicate with. This node is single homed
	
	public void setPeer (SimEnt peer)
	{
		_peer = peer;
		
		if(_peer instanceof Link)
		{
			 ((Link) _peer).setConnector(this);
		}
	}

	// Sets so there are only two decimals in the value.
	private double setDecimals(double value) {
		value = value * Math.pow(10, 2);
		value = Math.floor(value);
		value = value / Math.pow(10, 2);
		return value;
	}
	
	
	public NetworkAddr getAddr()
	{
		return _id;
	}
	
//**********************************************************************************	
	// Just implemented to generate some traffic for demo.
	// In one of the labs you will create some traffic generators

	private int _stopSendingAfter = 0; //messages
	private int _timeBetweenSending = 10; //time between messages
	private int _toNetwork = 0;
	private int _toHost = 0;
	
	public void StartSending(int network, int node, int number, int timeInterval, int startSeq)
	{
		_stopSendingAfter = number;
		_timeBetweenSending = timeInterval;
		_toNetwork = network;
		_toHost = node;
		_seq = startSeq;
		// The host node generates an event for itself as destination, this means that
		// The target will be itself, and its own recv function will be called as a next event from
		// SimEngine.
		send(this, new TimerEvent(),0);
	}
	
//**********************************************************************************	
	
	// This method is called upon that an event destined for this node triggers.
	public void recv(SimEnt src, Event ev)
	{
		if (ev instanceof TimerEvent)
		{			
			if (_stopSendingAfter > _sentmsg)
			{
				_sentmsg++;
				send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost),_seq, SimEngine.getTime()),0);
				send(this, new TimerEvent(),_timeBetweenSending);
				System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" sent message with seq: "+_seq +
						" at time "+setDecimals(SimEngine.getTime()));
				_seq++;
			}
		}
		if (ev instanceof Message)
		{
			double currPkgTime = setDecimals(SimEngine.getTime() - ((Message) ev).createdAt());
			double devTime = (_rcvdmsg > 0) ? (this.prevPkgTime - currPkgTime) : 0;
			if (devTime < 0) { devTime = -devTime; };
			this.prevPkgTime = currPkgTime;

			System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "
					+((Message) ev).seq() + " at time "+setDecimals(SimEngine.getTime()) +
					" - Relative transit time: " + currPkgTime + "ms" + " - Deviation from last package: "
					+ setDecimals(devTime) + "\n");
			_rcvdmsg++;
		}
	}
}
