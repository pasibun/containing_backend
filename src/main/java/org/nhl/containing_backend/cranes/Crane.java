package org.nhl.containing_backend.cranes;

import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.models.ContainerHolder;

import java.awt.*;

/**
 * Picks up containers and drops them.
 */
public abstract class Crane extends ContainerHolder {

    private Point point = new Point(0, 0);
    String type;
    int id;

    public Crane(String type) {
        super();
        this.type = type;
    }

    /**
     * Attach a container to the Crane.
     *
     * @param container Container to be attached.
     */
    public void attachContainer(Container container) {
        super.putContainer(point, container);
    }

    /**
     * Detach and return the container held by the Crane.
     *
     * @return Container previously held by Crane.
     */
    public Container detachContainer() {
        return super.takeContainer(point);
    }

    /**
     * Return the container held by the Crane. ONLY USED AS ACCESSOR.
     *
     * @return Container held by Crane.
     */
    public Container getContainer() {
        return containers[0][0].peek();
    }

    public String getType() {
        return type;
    }    
}
