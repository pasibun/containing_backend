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
    private HipsterDirectedGraph<String, Double> graph;
    private Crane crane;

    public MoveMessage(Agv agv, HipsterDirectedGraph<String, Double> graph, Crane crane) {
        super(Message.MOVE);
        this.agv = agv;
        this.graph = graph;
        this.crane = crane;
    }

    public String generateXml() {
        String message = "";
        message += "<id>" + getId() + "</id>";
        message += "<Move>";
        message += "<CurrentX>" + agv.getX() + "</CurrentX>";
        message += "<CurrentY>" + agv.getY() + "</CurrentY>";
        message += "<Dijkstra>";

        message += "</Dijkstra>";
        message += "<EndLocationType>" + crane.getType() + "</EndLocationType>";
        message += "<EndLocationId>" + crane.getId() + "</EndLocationId>";
        message += "</Move>";

        return message;
    }

    private String toXml(HipsterDirectedGraph<String, Double> graph) {

        return null;
    }

    public Agv getAgv() {
        return agv;
    }
}
