package org.nhl.containing_backend.cranes;

import org.nhl.containing_backend.models.Container;

import java.util.ArrayDeque;

/**
 * Crane that moves around on the rails. Choo choo!
 */
public class TruckCrane extends Crane {
    public TruckCrane() {
        super();
        containers = new ArrayDeque[1][1];
        containers[0][0] = new ArrayDeque<Container>();
    }
}
