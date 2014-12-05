package org.nhl.containing_backend.communication;

/**
 * Interface for things that can process messages.
 */
public interface ProcessesMessage {
    public int getId();

    public void setOccupied(boolean occupied);

    public int getProcessingMessageId();
    public void setProcessingMessageId(int id);
}
