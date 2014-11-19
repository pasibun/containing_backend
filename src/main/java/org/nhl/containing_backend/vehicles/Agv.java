package org.nhl.containing_backend.vehicles;

import org.nhl.containing_backend.Container;
import org.nhl.containing_backend.exceptions.AgvEmptyException;
import org.nhl.containing_backend.exceptions.AgvFullException;

/**
 * Little robot vehicle.
 */
public class Agv extends Vehicle {
    private Container container = null;

    public Agv() {

    }

    /**
     * Attach a container to the Agv.
     *
     * @param newContainer Container to be attached.
     */
    public void attachContainer(Container newContainer) {
        if (container != null) {
            throw new AgvFullException();
        }
        container = newContainer;
    }

    /**
     * Detach and return the container held by the Agv.
     *
     * @return Container previously held by Agv.
     */
    public Container detachContainer() {
        if (container == null) {
            throw new AgvEmptyException();
        }
        Container result = container;
        container = null;
        return result;
    }

    /**
     * Return the container held by the Agv. ONLY USED AS ACCESSOR.
     *
     * @return Container held by Agv.
     */
    public Container getContainer() {
        return container;
    }
}
