package org.nhl.containing_backend.models;

import org.nhl.containing_backend.Container;
import org.nhl.containing_backend.exceptions.PlaceEmptyException;
import org.nhl.containing_backend.exceptions.PlaceOccupiedException;

import java.awt.*;

/**
 * Temporary storage for containers
 */
public class Storage {
    Container[][] containers;

    public Storage(int rowsCount, int columnsCount) {
        containers = new Container[rowsCount][columnsCount];
    }

    public void putContainer(Point point, Container container) {
        if (getContainerAt(point) != null) {
            throw new PlaceOccupiedException();
        }
        setContainerAt(point, container);
    }

    public Container takeContainer(Point point) {
        Container result = getContainerAt(point);
        if (result == null) {
            throw new PlaceEmptyException();
        }
        setContainerAt(point, null);
        return result;
    }

    public Container getContainerAt(Point point) {
        return containers[point.x][point.y];
    }

    private void setContainerAt(Point point, Container container) {
        containers[point.x][point.y] = container;
    }
}
