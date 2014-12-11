package org.nhl.containing_backend.communication;

import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.vehicles.Transporter;

/**
 *
 */
public class SpeedMessage extends Message {

    private float speed;

    public SpeedMessage(float speed) {
        super();
        this.speed = speed;
    }

    @Override
    public String generateXml() {
        String message = "";
        message += "<id>" + getId() + "</id>";
        message += "<Speed>";
        message += speed;
        message += "</Speed>";

        return message;
    }

    @Override
    public ProcessesMessage getProcessor() {
        return null;
    }
}
