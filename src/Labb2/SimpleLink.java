package Labb2;
import Sim.Event;
import Sim.Link;
import Sim.Message;
import Sim.SimEnt;

public class SimpleLink extends Link {

    SimpleLink() {super();};

    // Called when a message enters the link

    public void recv(SimEnt src, Event ev)
    {
        if (ev instanceof Message)
        {
            //System.out.println("Link recv msg, passes it through");
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
