package org.nhl.containing_backend.communication.messages;

/**
 *
 */
public class SpeedMessage extends Message {

    private float speed;
    private String dateString;

    public SpeedMessage(float speed, String dateString) {
        super(Message.SPEED);
        this.speed = speed;
        this.dateString = dateString;
    }

    @Override
    public String generateXml() {
        String message = "";
        message += "<id>" + getId() + "</id>";
        message += "<SpeedMessage>";
        message += "<Speed>";
        message += speed;
        message += "</Speed>";
        message += "<DateString>";
        message += dateString;
        message += "</DateString>";
        message += "</SpeedMessage>";

        return message;
    }

    public float getSpeed() {
        return speed;
    }
}
