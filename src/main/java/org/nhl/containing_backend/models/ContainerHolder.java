package org.nhl.containing_backend.models;

import org.nhl.containing_backend.Container;
import org.nhl.containing_backend.exceptions.FullStackException;

import java.awt.*;
import java.util.Deque;
import java.util.EmptyStackException;

/**
 * Abstract class that can hold containers.
 */
public abstract class ContainerHolder {
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
    protected Deque<Container>[][] containers;
    protected int containerAmountLimit = 1;

    /**
     * Put a container in the stack positioned at the provided coordinate location.
     *
     * @param point 2D integer coordinate where a container must be put on the stack.
     * @param container Provided container.
     */
    protected void putContainer(Point point, Container container) {
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
    protected Container takeContainer(Point point) {
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
    protected int heightAt(Point point) {
        Deque<Container> deque = containers[point.x][point.y];
        return deque.size();
    }
}