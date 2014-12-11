package org.nhl.containing_backend.communication.messages;

import org.nhl.containing_backend.communication.ProcessesMessage;
import org.nhl.containing_backend.vehicles.Transporter;

/**
 *
 */
public class ArriveMessage extends Message {

    private Transporter transporter;
    private int depotIndex;

    public ArriveMessage(Transporter transporter, int depotIndex) {
        super();
        this.transporter = transporter;
        this.depotIndex = depotIndex;
    }

    public String generateXml() {
        String message = "";
        message += "<id>" + getId() + "</id>";
        message += "<Arrive>";

        message += "<transporterId>" + transporter.getId() + "</transporterId>";
        message += "<depotIndex>" + depotIndex + "</depotIndex>";

        message += "</Arrive>";

        return message;
    }

    @Override
    public ProcessesMessage getProcessor() {
        return transporter;
    }
}
