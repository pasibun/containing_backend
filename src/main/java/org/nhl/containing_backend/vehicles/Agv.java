package org.nhl.containing_backend.vehicles;

import org.nhl.containing_backend.models.Container;

import java.awt.*;
import java.util.ArrayDeque;

/**
 * Little robot vehicle.
 */
public class Agv extends Vehicle {
    private Point point = new Point(0, 0);

    public Agv() {
        containers = new ArrayDeque[1][1];
        containers[0][0] = new ArrayDeque<Container>();
    }

    /**
     * Attach a container to the Agv.
     *
     * @param container Container to be attached.
     */
    public void attachContainer(Container container) {
        super.putContainer(point, container);
    }

    /**
     * Detach and return the container held by the Agv.
     *
     * @return Container previously held by Agv.
     */
    public Container detachContainer() {
        return super.takeContainer(point);
    }

    /**
     * Return the container held by the Agv. ONLY USED AS ACCESSOR.
     *
     * @return Container held by Agv.
     */
    public Container getContainer() {
        return containers[0][0].peek();
    }
}
