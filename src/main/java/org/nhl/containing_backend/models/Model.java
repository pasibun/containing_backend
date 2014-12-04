package org.nhl.containing_backend.models;

import java.util.ArrayList;
import org.nhl.containing_backend.cranes.MoveableCrane;
import org.nhl.containing_backend.cranes.RailCrane;
import org.nhl.containing_backend.vehicles.Agv;
import org.nhl.containing_backend.vehicles.Transporter;

import java.util.List;

/**
 * Representation of the state of the program.
 */
public class Model {

    private List<Container> containerPool;
    private List<Agv> agvs;
    private List<Transporter> transporters;
    private Storage storage;
    private List<RailCrane> railCranes;
    private List<MoveableCrane> moveableCranes;

    public Model() {
        containerPool = new ArrayList<Container>();
        agvs = new ArrayList<Agv>();
        transporters = new ArrayList<Transporter>();
        railCranes = new ArrayList<RailCrane>();
        moveableCranes = new ArrayList<MoveableCrane>();
    }

    /*public void createAgv() {
        for (int i = 0; i < 100; i++) {
            agv = new Agv();
            agvs.add(agv);
        }
    }*/

    public List<Container> getContainerPool() {
        return containerPool;
    }

    public List<Agv> getAgvs() {
        return agvs;
    }

    public List<MoveableCrane> getMoveableCranes() {
        return moveableCranes;
    }

    public List<RailCrane> getRailCranes() {
        return railCranes;
    }

    public List<Transporter> getTransporters() {
        return transporters;
    }
}
