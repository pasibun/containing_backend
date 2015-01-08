package org.nhl.containing_backend.models;

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
                containers[row][column] = new ArrayDeque<>();
            }
        }
    }

    /**
     * Put a container in the stack positioned at the provided coordinate location.
     *
     * @param point     2D integer coordinate where a container must be put on the stack.
     * @param container Provided container.
     */
    @Override
    public void putContainer(Point point, Container container) {
        super.putContainer(point, container);
    }

    /**
     * Take a container from the stack at the provided coordinate location.
     *
     * @param point 2D integer coordinate from whence a container must be taken.
     * @return The taken container.
     */
    @Override
    public Container takeContainer(Point point) {
        return super.takeContainer(point);
    }
}
