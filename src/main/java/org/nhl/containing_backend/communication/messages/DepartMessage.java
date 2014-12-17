package org.nhl.containing_backend.communication.messages;

import org.nhl.containing_backend.vehicles.Transporter;

/**
 *
 */
public class DepartMessage extends Message {

    private Transporter transporter;

    public DepartMessage(Transporter transporter) {
        super(Message.DEPART);
        this.transporter = transporter;
    }

    public String generateXml() {
        String message = "";
        message += "<id>" + getId() + "</id>";
        message += "<Depart>";
        message += "<transporterId>" + transporter.getId() + "</transporterId>";
        message += "</Depart>";

        return message;
    }

    public Transporter getTransporter() {
        return transporter;
    }
}
