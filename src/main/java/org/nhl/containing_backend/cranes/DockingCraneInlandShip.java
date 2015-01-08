package org.nhl.containing_backend.cranes;


import java.util.ArrayDeque;

/**
 * Crane that moves around, somehow. I don't know; don't ask me.
 */
public class DockingCraneInlandShip extends Crane {

    public DockingCraneInlandShip(String type) {
        super(type);
        containers = new ArrayDeque[1][1];
        containers[0][0] = new ArrayDeque<>();
    }
}
