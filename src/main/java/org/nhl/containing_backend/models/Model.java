package org.nhl.containing_backend.models;

import java.util.ArrayList;
import org.nhl.containing_backend.cranes.DockingCraneInlandShip;
import org.nhl.containing_backend.cranes.StorageCrane;
import org.nhl.containing_backend.vehicles.Agv;
import org.nhl.containing_backend.vehicles.Transporter;

import java.util.HashMap;
import java.util.List;
import org.nhl.containing_backend.cranes.DockingCraneSeaShip;
import org.nhl.containing_backend.cranes.TrainCrane;
import org.nhl.containing_backend.cranes.TruckCrane;

/**
 * Representation of the state of the program.
 */
public class Model {

    private List<Container> containerPool;
    private List<Agv> agvs;
    private List<Transporter> transporters;
    private HashMap<String, Transporter[]> depots;
    private Storage storage;
    private List<StorageCrane> storageCranes;
    private List<DockingCraneInlandShip> dockingCranesInland;
    private List<DockingCraneSeaShip> dockingCranesSea;
    private List<TrainCrane> trainCranes;
    private List<TruckCrane> truckCranes;
    private final int MAXAGV = 144;
    private List<Float> agvParkingX;
    private List<Float> agvParkingY;

    public Model() {
        containerPool = new ArrayList<>();
        agvs = new ArrayList<>();
        transporters = new ArrayList<>();
        depots = new HashMap<>();
        depots.put("vrachtauto", new Transporter[20]);
        depots.put("trein", new Transporter[1]);
        depots.put("binnenschip", new Transporter[2]);
        depots.put("zeeschip", new Transporter[1]);
        storageCranes = new ArrayList<>();
        dockingCranesInland = new ArrayList<>();
        dockingCranesSea = new ArrayList<>();
        trainCranes = new ArrayList<>();
        truckCranes = new ArrayList<>();
        agvParkingX = new ArrayList<>();
        agvParkingY = new ArrayList<>();
        initStartModel();
    }

    /**
     * Figure out which sets of depots aren't currently occupied by a
     * transporter.
     *
     * @return A dictionary with the type as key, and a list of available depots
     * (indices) for that type as value.
     */
    public HashMap<String, List<Integer>> availableDepots() {
        HashMap<String, List<Integer>> result = new HashMap<>();
        for (String key : depots.keySet()) {
            result.put(key, availableDepotsForType(key));
        }
        return result;
    }

    private List<Integer> availableDepotsForType(String type) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < depots.get(type).length; i++) {
            if (depots.get(type)[i] == null) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * Create starting model
     */
    private void initStartModel() {
        initDockingCraneInland();
        initDockingCraneSea();
        initStorageCrane();
        initTrainCrane();
        initTruckCrane();
        initAgvParkingShip();
        initAgvParkingTrain();
        initAgvParkingLorry();
        placeAgv();
    }

    /**
     * Initializes the agv parking on the ship storage platform. The X and Y
     * locations are storred in an ArrayList. To trigger the 6th parking spot,
     * pull the 6th element from both array's agvParkingX.pull(5);
     * agvParkingY.pull(5);
     *
     * 144 parking places
     */
    private void initAgvParkingShip() {
        // Parking id 0 till 23
        int agvStartPoint = -167;
        for (int i = 1; i < 29; i++) {
            if (i % 7 != 0) {
                agvParkingX.add((agvStartPoint + (4.7f * i)));
                agvParkingY.add(-122f);
            } else {
                agvStartPoint += 17;
            }
        }

        // Parking on the opposite side
        // Parking id 24 till 47
        int agvOpositeStartPoint = -298;
        for (int i = 29; i < 57; i++) {
            if (i % 7 != 0) {
                agvParkingX.add((agvOpositeStartPoint + (4.7f * i)));
                agvParkingY.add(113f);
            } else {
                agvOpositeStartPoint += 17;
            }
        }
    }

    /**
     * Initializes the agv parking on the train storage platform.
     */
    private void initAgvParkingTrain() {
        // Parking id 48 till 71
        int agvStartPoint = -149;
        for (int i = 57; i < 85; i++) {
            if (i % 7 != 0) {
                agvParkingX.add((agvStartPoint + (4.7f * i)));
                agvParkingY.add(-122f);
            } else {
                agvStartPoint += 17;
            }
        }

        // Parking on the opposite side
        // Parking id 72 till 95
        int agvOpositeStartPoint = -281;
        for (int i = 85; i < 113; i++) {
            if (i % 7 != 0) {
                agvParkingX.add((agvOpositeStartPoint + (4.7f * i)));
                agvParkingY.add(113f);
            } else {
                agvOpositeStartPoint += 17;
            }
        }

    }

    /**
     * Initializes the agv parking on the lorry storage platform.
     */
    private void initAgvParkingLorry() {
        // Parking id 96 till 119
        int agvStartPoint = -163;
        for (int i = 113; i < 141; i++) {
            if (i % 7 != 0) {
                agvParkingX.add((agvStartPoint + (4.7f * i)));
                agvParkingY.add(-122f);
            } else {
                agvStartPoint += 17;
            }
        }

        // Parking on the opposite side
        // Parking id 120 till 143
        int agvOpositeStartPoint = -295;
        for (int i = 141; i < 169; i++) {
            if (i % 7 != 0) {
                agvParkingX.add((agvOpositeStartPoint + (4.7f * i)));
                agvParkingY.add(113f);
            } else {
                agvOpositeStartPoint += 17;
            }
        }
    }

    /**
     * Spawn agv on given parkingspace and add it to agv list
     *
     * @param id used to place agv on the given parkingspace
     */
    private void agvToParking(int id) {
        try {
            Agv agv = new Agv();
            float agvX = agvParkingX.get(id);
            float agvY = agvParkingY.get(id);
            agv.setX(agvX);
            agv.setY(agvY);
            agv.setId(id);

            agvs.add(agv);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Max parking id is 143, you used " + id);
        }
    }

    private void placeAgv() {
        for (int i = 0; i < MAXAGV; i++) {
            agvToParking(i);
        }
    }

    private void initDockingCraneInland() {
        for (int i = 0; i < 8; i++) {
            DockingCraneInlandShip dockingCraneInland = new DockingCraneInlandShip("DockingCraneInlandShip");
            dockingCraneInland.setId(i);
            dockingCranesInland.add(dockingCraneInland);
        }
    }

    private void initDockingCraneSea() {
        for (int i = 0; i < 10; i++) {
            DockingCraneSeaShip dockingCraneSea = new DockingCraneSeaShip("DockingCraneSeaShip");
            dockingCraneSea.setId(i);
            dockingCranesSea.add(dockingCraneSea);
        }
    }

    private void initStorageCrane() {
        for (int i = 0; i < 12; i++) {
            StorageCrane storageCrane = new StorageCrane("StorageCrane");
            storageCrane.setId(i);
            storageCranes.add(storageCrane);
        }
    }

    private void initTrainCrane() {
        for (int i = 0; i < 4; i++) {
            TrainCrane trainCrane = new TrainCrane("TrainCrane");
            trainCrane.setId(i);
            trainCranes.add(trainCrane);
        }
    }

    private void initTruckCrane() {
        for (int i = 0; i < 20; i++) {
            TruckCrane truckCrane = new TruckCrane("TruckCrane");
            truckCrane.setId(i);
            truckCranes.add(truckCrane);
        }
    }

    public List<Container> getContainerPool() {
        return containerPool;
    }

    public List<Agv> getAgvs() {
        return agvs;
    }

    public List<DockingCraneInlandShip> getDockingCranesInland() {
        return dockingCranesInland;
    }

    public List<DockingCraneSeaShip> getDockingCranesSea() {
        return dockingCranesSea;
    }

    public List<StorageCrane> getStorageCrane() {
        return storageCranes;
    }

    public List<TrainCrane> getTrainCranes() {
        return trainCranes;
    }

    public List<TruckCrane> getTruckCranes() {
        return truckCranes;
    }

    public List<Transporter> getTransporters() {
        return transporters;
    }

    public HashMap<String, Transporter[]> getDepots() {
        return depots;
    }

    public Storage getStorage() {
        return storage;
    }
}
