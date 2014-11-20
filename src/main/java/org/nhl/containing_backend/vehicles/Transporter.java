package org.nhl.containing_backend.vehicles;

import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.exceptions.FullStackException;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;

/**
 * Mass-transport vehicle that transports containers.
 */
public class Transporter extends Vehicle {
    // 2D array of Container Stacks.
    // Top-down visualisation:
    //
    //   0 1 2 3 4 5
    // 0
    // 1
    // 2
    // 3     X
    // 4
    // 5
    //
    // There's a Stack of Containers at X, which can be accessed via containers[3][2].
    // That is: Row 3, Column 2.
    //
    // Deque is used for the sole purpose of being objectively better than Stacks (the Java documentation has deprecated
    // Stacks and recommends Deques instead).
    private Deque<Container>[][] containers;
    private int containerAmountLimit;

    public Transporter(int rowsCount, int columnsCount, int containerAmountLimit) {
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
     * @param point 2D integer coordinate where a container must be put on the stack.
     * @param container Provided container.
     */
    public void putContainer(Point point, Container container) {
        Deque<Container> deque = containers[point.x][point.y];
        if (deque.size() == containerAmountLimit) {
            throw new FullStackException();
        }
        deque.addLast(container);
    }

    /**
     * Take a container from the stack at the provided coordinate location.
     *
     * @param point 2D integer coordinate from whence a container must be taken.
     * @return The taken container.
     */
    public Container takeContainer(Point point) {
        Deque<Container> deque = containers[point.x][point.y];
        if (deque.size() == 0) {
            throw new EmptyStackException();
        }
        return deque.removeLast();
    }

    /**
     * Small utility function that returns the size of a stack at a given coordinate
     *
     * @param point 2D integer coordinate of a container stack on the transporter.
     * @return The amount of containers on the stack.
     */
    public int heightAt(Point point) {
        Deque<Container> deque = containers[point.x][point.y];
        return deque.size();
    }
}
