package org.nhl.containing_backend.cranes;

import org.nhl.containing_backend.models.Container;

import java.util.ArrayDeque;

/**
 * Crane that moves around on the rails. Choo choo!
 */
public class StorageCrane extends Crane {

    public StorageCrane(String type) {
        super(type);
        containers = new ArrayDeque[1][1];
        containers[0][0] = new ArrayDeque<Container>();
    }
}
