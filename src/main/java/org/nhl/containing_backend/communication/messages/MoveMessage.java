package org.nhl.containing_backend.communication.messages;

import org.nhl.containing_backend.Dijkstra;
import org.nhl.containing_backend.vehicles.Agv;
import org.nhl.containing_backend.vehicles.Transporter;

/**
 *
 */
public class MoveMessage extends Message {

    private Agv agv;
    private Dijkstra dijkstra;
    private int depotIndex;

    public MoveMessage(Agv agv, Dijkstra dijkstra, int depotIndex) {
        super(Message.MOVE);
        this.agv = agv;
        this.dijkstra = dijkstra;
        this.depotIndex = depotIndex;
    }

    public String generateXml() {
        String message = "";
        message += "<id>" + getId() + "</id>";
        message += "<Move>";
        message += "<currentLocationX>" + agv.getX() + "</currentLocationX>";
        message += "<currentLocationY>" + agv.getY() + "</currentLocationY>";
        message += "<Dijkstra>";

        message += "</Move>";

        return message;
    }

    public Agv getAgv() {
        return agv;
    }

    public Dijkstra getDijkstra() {
        return dijkstra;
    }

    public int getDepotIndex() {
        return depotIndex;
    }
}
