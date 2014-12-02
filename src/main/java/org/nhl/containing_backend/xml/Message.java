package org.nhl.containing_backend.xml;

enum Command {

    OK
}


public class Message {
    private String objectName;
    private int  objectId;
    private int objectSize;
    private Command command;

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setObjectSize(int objectSize) {
        this.objectSize = objectSize;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public int getObjectId() {
        return objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public int getObjectSize() {
        return objectSize;
    }
    
}
