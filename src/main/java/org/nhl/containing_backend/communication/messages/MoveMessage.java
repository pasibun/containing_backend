package org.nhl.containing_backend.communication.messages;

import org.nhl.containing_backend.vehicles.Transporter;

/**
 *
 */
public class MoveMessage extends Message {

    private Transporter transporter;
    private int depotIndex;

    public MoveMessage(Transporter transporter, int depotIndex) {
        super(Message.MOVE);
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
