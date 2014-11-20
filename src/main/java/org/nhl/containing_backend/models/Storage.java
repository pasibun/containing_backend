package org.nhl.containing_backend.models;

import org.nhl.containing_backend.models.Container;

import java.awt.*;
import java.util.ArrayDeque;

/**
 * Temporary storage for containers
 */
public class Storage extends ContainerHolder {

    public Storage(int rowsCount, int columnsCount) {
        containers = new ArrayDeque[rowsCount][columnsCount];

        // Instantiate all stacks in the 2D array.
        for (int row = 0; row < rowsCount; row++) {
            for (int column = 0; column < columnsCount; column++) {
                containers[row][column] = new ArrayDeque<Container>();
            }
        }
    }

    public void putContainer(Point point, Container container) {
        super.putContainer(point, container);
    }

    public Container takeContainer(Point point) {
        return super.takeContainer(point);
    }
}
