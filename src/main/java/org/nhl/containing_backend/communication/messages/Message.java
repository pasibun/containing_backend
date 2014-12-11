package org.nhl.containing_backend.communication.messages;


import org.nhl.containing_backend.communication.ProcessesMessage;

public abstract class Message {
    private static int counter = 0;
    private int id;

    public Message() {
        counter += 1;
        id = counter;
    }

    public abstract String generateXml();

    public abstract ProcessesMessage getProcessor();

    public int getId() {
        return id;
    }
}
