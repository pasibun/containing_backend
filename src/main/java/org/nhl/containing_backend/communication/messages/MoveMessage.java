package org.nhl.containing_backend.communication.messages;

import org.nhl.containing_backend.cranes.Crane;
import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.vehicles.Agv;
import org.nhl.containing_backend.vehicles.Transporter;

/**
 *
 */
public class MoveMessage extends Message {

    private Agv agv;

    public MoveMessage(Agv agv) {
        super(Message.CRANE);
        this.agv = agv;
    }

    @Override
    public String generateXml() {
        String message = "";
        message += "<id>" + getId() + "</id>";
        message += "<Create>";
        //message += "<Agv identifier=\"" + agv.getId() + "\" currentlocation=\"" +agv.getCurrentLocation + "\" endLocation=\"" +  + "\">";

        message += "</Agv>";
        message += "</Create>";

        return message;
    }

    public Agv getAgv() {
        return agv;
    }
}
