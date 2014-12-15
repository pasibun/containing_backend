package org.nhl.containing_backend.communication.messages;

import org.nhl.containing_backend.cranes.Crane;
import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.vehicles.Agv;
import org.nhl.containing_backend.vehicles.Transporter;

/**
 *
 */
public class CraneMessage extends Message {

    private Crane crane;
    private Transporter transporter;
    private Agv agv;
    private Container container;    

    public CraneMessage(Crane crane, Transporter transporter, Agv agv, Container container) {
        super(Message.CRANE);
        this.crane = crane;
        this.transporter = transporter;
        this.agv = agv;
        this.container = container;
    }

    @Override
    public String generateXml() {
        String message = "";
        message += "<id>" + getId() + "</id>";
        message += "<Create>";
        message += "<Crane type=\"" + crane.getType() + "\" identifier=\"" + crane.getId() +"\"></Crane>";
        message += "<Transporter type=\"" + transporter.getType() + "\" identifier=\"" + transporter.getId() +"\"></Transporter>";
        message += "<AgvId>" + agv.getId() + "</AgvId>";
        message += "<Container>" + container.toXml() + "</Container>";
        message += "</Create>";

        return message;
    }

    public Transporter getTransporter() {
        return transporter;
    }

    public Container getContainer() {
        return container;
    }

    public Agv getAgv() {
        return agv;
    }

    public Crane getCrane() {
        return crane;
    }
    
    
}
