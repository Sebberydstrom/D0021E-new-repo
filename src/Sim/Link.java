package Sim;

// This class implements a link without any loss, jitter or delay


import Labb3.BindingUpdate;
import Labb3.SwitchEvent;

import javax.naming.Binding;

public class Link extends SimEnt{
	public SimEnt _connectorA=null;
	public SimEnt _connectorB=null;
	public int _now=0;
	
	public Link()
	{
		super();	
	}
	
	// Connects the link to some simulation entity like
	// a node, switch, router etc.
	
	public void setConnector(SimEnt connectTo)
	{
		if (_connectorA == null) 
			_connectorA=connectTo;
		else
			_connectorB=connectTo;
	}

	// Called when a message enters the link
	
	public void recv(SimEnt src, Event ev)
	{
		if (ev instanceof Message || ev instanceof SwitchEvent || ev instanceof BindingUpdate)
		{
			if (ev instanceof SwitchEvent) {
				System.out.println("Link recv SwitchEvent, passes it through");
			}
			if (ev instanceof BindingUpdate) {
				System.out.println("Link recv BindingUpdate, passes it through");
			}
			if (ev instanceof Message) {
				System.out.println("Link recv msg, passes it through");
			}
			if (src == _connectorA)
			{
				send(_connectorB, ev, _now);
			}
			else
			{
				send(_connectorA, ev, _now);
			}
		}
	}	
}