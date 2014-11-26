package org.nhl.containing_backend.cranes;

import org.nhl.containing_backend.models.Container;

import java.util.ArrayDeque;

/**
 * Crane that moves around, somehow. I don't know; don't ask me.
 */
public class MoveableCrane extends Crane {
    public MoveableCrane() {
        containers = new ArrayDeque[1][1];
        containers[0][0] = new ArrayDeque<Container>();
    }
}
