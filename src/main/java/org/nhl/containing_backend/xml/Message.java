package org.nhl.containing_backend.xml;


public class Message {
    private static int counter = 0;
    private String objectName;
    private int  objectId;
    private int objectSize;
    private Command command;

    public Message() {
        counter += 1;
        objectId = counter;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getObjectSize() {
        return objectSize;
    }

    public void setObjectSize(int objectSize) {
        this.objectSize = objectSize;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public static enum Command {
        OK
    }
}
