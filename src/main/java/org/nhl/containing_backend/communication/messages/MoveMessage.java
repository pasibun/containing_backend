package org.nhl.containing_backend.communication.messages;

import es.usc.citius.hipster.util.graph.HipsterDirectedGraph;
import org.nhl.containing_backend.Dijkstra;
import org.nhl.containing_backend.cranes.Crane;
import org.nhl.containing_backend.vehicles.Agv;
import org.nhl.containing_backend.vehicles.Transporter;

/**
 *
 */
public class MoveMessage extends Message {

    private Agv agv;
    private String dijkstra;
    private Crane crane;

    public MoveMessage(Agv agv, String dijkstra, Crane crane) {
        super(Message.MOVE);
        this.agv = agv;
        this.dijkstra = dijkstra;
        this.crane = crane;
    }

    public String generateXml() {
        String message = "";
        message += "<id>" + getId() + "</id>";
        message += "<Move>";
        message += "<AgvId>" + agv.getId() + "</AgvId>";
        message += "<CurrentX>" + agv.getX() + "</CurrentX>";
        message += "<CurrentY>" + agv.getY() + "</CurrentY>";
        message += "<Dijkstra>" + dijkstra + "</Dijkstra>";
        message += "<EndLocationType>" + crane.getType() + "</EndLocationType>";
        message += "<EndLocationId>" + crane.getId() + "</EndLocationId>";
        message += "</Move>";

        return message;
    }

    public Agv getAgv() {
        return agv;
    }
}
