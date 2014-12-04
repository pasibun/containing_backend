package org.nhl.containing_backend.vehicles;

import org.nhl.containing_backend.models.Container;

import java.awt.*;
import java.util.ArrayDeque;

/**
 * Mass-transport vehicle that transports containers.
 */
public class Transporter extends Vehicle {

    String type;

    public Transporter(String type, int rowsCount, int columnsCount, int containerAmountLimit) {
        this.type = type;
        // Ignore the assignment error; it's stupid.
        this.containers = new ArrayDeque[rowsCount][columnsCount];

        // Instantiate all stacks in the 2D array.
        for (int row = 0; row < rowsCount; row++) {
            for (int column = 0; column < columnsCount; column++) {
                this.containers[row][column] = new ArrayDeque<Container>();
            }
        }

        this.containerAmountLimit = containerAmountLimit;
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

    /**
     * Small utility function that returns the size of a stack at a given coordinate
     *
     * @param point 2D integer coordinate of a container stack on the transporter.
     * @return The amount of containers on the stack.
     */
    @Override
    public int heightAt(Point point) {
        return super.heightAt(point);
    }

    public String getType() {
        return type;
    }
}
