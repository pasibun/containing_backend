package org.nhl.containing_backend.vehicles;

import org.nhl.containing_backend.models.Container;

import java.awt.*;
import java.util.ArrayDeque;

/**
 * Little robot vehicle.
 */
public class Agv extends Vehicle {

    private Point point = new Point(0, 0);


    public enum locationTypeEnum {

        INLANDSHIP("binnenschip"), SEASHIP("zeeschip"), TRAIN("trein"), TRUCK("vrachtauto"), NONE("");
        public String value;

        private locationTypeEnum(String value) {
            this.value = value;
        }
    };
    private locationTypeEnum locationType = locationTypeEnum.NONE;

    public Agv() {
        super();
        containers = new ArrayDeque[1][1];
        containers[0][0] = new ArrayDeque<>();
    }

    /**
     * Attach a container to the Agv.
     *
     * @param container Container to be attached.
     */
    public void attachContainer(Container container) {
        super.putContainer(point, container);
    }

    /**
     * Detach and return the container held by the Agv.
     *
     * @return Container previously held by Agv.
     */
    public Container detachContainer() {
        return super.takeContainer(point);
    }

    /**
     * Return the container held by the Agv. ONLY USED AS ACCESSOR.
     *
     * @return Container held by Agv.
     */
    public Container getContainer() {
        return containers[0][0].peek();
    }
   

    public String getLocationType() {
        String returnValue;
        switch (locationType) {
            case INLANDSHIP:
                returnValue = "binnenschip";
                break;
            case SEASHIP:
                returnValue = "zeeschip";
                break;
            case TRAIN:
                returnValue = "trein";
                break;
            case TRUCK:
                returnValue = "vrachtauto";
                break;
            default:
                returnValue = "";
                break;
        }
        return returnValue;
    }

    public void setLocationType(String locationType) {
        switch (locationType) {
            case "binnenschip":
                this.locationType = locationTypeEnum.INLANDSHIP;
                break;
            case "zeeschip":
                this.locationType = locationTypeEnum.SEASHIP;
                break;
            case "trein":
                this.locationType = locationTypeEnum.TRAIN;
                break;
            case "vrachtauto":
                this.locationType = locationTypeEnum.TRUCK;
                break;
        }
    }
}
