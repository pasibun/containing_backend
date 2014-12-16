package org.nhl.containing_backend.communication.messages;

import org.nhl.containing_backend.vehicles.Transporter;

/**
 *
 */
public class DepartMessage extends Message {

    private Transporter transporter;
    private int depotIndex;

    public DepartMessage(Transporter transporter, int depotIndex) {
        super(Message.DEPART);
        this.transporter = transporter;
        this.depotIndex = depotIndex;
    }

    public String generateXml() {
        String message = "";
        message += "<id>" + getId() + "</id>";
        message += "<Move>";

        message += "</Move>";

        return message;
    }

    public Transporter getTransporter() {
        return transporter;
    }

    public int getDepotIndex() {
        return depotIndex;
    }
}
