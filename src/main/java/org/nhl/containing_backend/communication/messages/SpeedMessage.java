package org.nhl.containing_backend.communication.messages;

/**
 *
 */
public class SpeedMessage extends Message {

    private float speed;

    public SpeedMessage(float speed) {
        super(Message.SPEED);
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

    public float getSpeed() {
        return speed;
    }
}
