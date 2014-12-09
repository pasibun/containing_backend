/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nhl.containing_backend;

import java.sql.*;
import org.nhl.containing_backend.cranes.*;
import org.nhl.containing_backend.models.Model;
import org.nhl.containing_backend.models.Storage;
import org.nhl.containing_backend.vehicles.Agv;
import org.nhl.containing_backend.vehicles.Transporter;

/**
 *
 * @author matthijs_laptop
 */
public class Database {

    private String url = "jdbc:mysql://localhost:3306/";
    private String dbName = "demo";
    private String driver = "com.mysql.jdbc.Driver";
    private String userName = "root";
    private String password = "";
    private Model model;

    public Database(Model model) {
        this.model = model;
        setup();
    }

    public void setup() {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement statement = conn.createStatement();

            String delete = "DROP TABLE IF EXISTS agv, dockingcrane, storeagecrane, traincrane, truckcrane, transporter, storage";

            statement.executeUpdate(delete);

            String sqlAGV = "CREATE TABLE agv "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " agv_counter int, "
                    + " PRIMARY KEY ( id ))";

            String sqlDockingCrane = "CREATE TABLE dockingcrane "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " crane_container_processed int, "
                    + " PRIMARY KEY ( id ))";

            String sqlStorageCrane = "CREATE TABLE storagecrane "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " crane_container_processed int, "
                    + " PRIMARY KEY ( id ))";

            String sqlTrainCrane = "CREATE TABLE traincrane "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " crane_container_processed int, "
                    + " PRIMARY KEY ( id ))";

            String sqlTruckCrane = "CREATE TABLE truckcrane "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " crane_container_processed int, "
                    + " PRIMARY KEY ( id ))";

            String sqlTransporter = "CREATE TABLE transporter "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " transporter_name VARCHAR(255), "
                    + " container_counter int, "
                    + " PRIMARY KEY ( id ))";

            String sqlStorage = "CREATE TABLE storage "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " container_total int, "
                    + " PRIMARY KEY ( id ))";



            statement.executeUpdate(sqlAGV);
            statement.executeUpdate(sqlTransporter);
            statement.executeUpdate(sqlStorage);
            statement.executeUpdate(sqlDockingCrane);
            statement.executeUpdate(sqlStorageCrane);
            statement.executeUpdate(sqlTrainCrane);
            statement.executeUpdate(sqlTruckCrane);

            conn.close();
            initValues();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update database for transporter
     *
     * @param transport
     */
    public void updateDatabaseTransporters(Transporter transport) {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();

            String sqlupdate = "INSERT INTO transporter(id, transporter_name, container_counter) "
                    + " VALUES('" + transport.getId() + "')"
                    + " VALUES('" + transport.getType() + "')"
                    + " VALUES('" + transport.getContainers().size() + "')";
            st.executeUpdate(sqlupdate);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update database for storage
     *
     * @param storage
     */
    public void updateDatabaseStorage(Storage storage) {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();

            String sqlupdate = "INSERT INTO transporter(container_total) "
                    + " VALUES('" + storage.getContainers().size() + "')";

            st.executeUpdate(sqlupdate);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update Database for docking crane
     *
     * @param dockingCrane
     */
    public void updateDockingCrane(DockingCrane dockingCrane) {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();

            String sqlupdate = "INSERT INTO dockingcrane(crane_container_processed) "
                    + " VALUES('" + dockingCrane.getContainers().size() + "')";

            st.executeUpdate(sqlupdate);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update Database for storage crane
     *
     * @param storageCrane
     */
    public void updateStorageCrane(StorageCrane storageCrane) {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();

            String sqlupdate = "INSERT INTO storagecrane(crane_container_processed) "
                    + " VALUES('" + storageCrane.getContainers().size() + "')";

            st.executeUpdate(sqlupdate);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update Database for train crane
     *
     * @param trainCrane
     */
    public void updateTrainCrane(TrainCrane trainCrane) {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();

            String sqlupdate = "INSERT INTO traincrane(crane_container_processed) "
                    + " VALUES('" + trainCrane.getContainers().size() + "')";

            st.executeUpdate(sqlupdate);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update Database for truck crane
     *
     * @param truckCrane
     */
    public void updateTruckCrane(TruckCrane truckCrane) {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();

            String sqlupdate = "INSERT INTO truckcrane(crane_container_processed) "
                    + " VALUES('" + truckCrane.getContainers().size() + "')";

            st.executeUpdate(sqlupdate);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create starting value of the database
     */
    private void initValues() {
        initAgv();
        initDockingCrane();
        initStorageCrane();
        initTrainCrane();
        initTruckCrane();
    }

    private void initAgv() {
        try {
            for (Agv agv : model.getAgvs()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO agv(agv_counter) "
                        + " VALUES('1')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDockingCrane() {
        try {
            for (DockingCrane dockingCrane : model.getDockingCrane()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO dockingcrane(crane_container_processed) "
                        + " VALUES('0')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initStorageCrane() {
        try {
            for (StorageCrane storageCrane : model.getStorageCrane()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO storagecrane(crane_container_processed) "
                        + " VALUES('0')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTrainCrane() {
        try {
            for (TrainCrane trainCrane : model.getTrainCranes()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO traincrane(crane_container_processed) "
                        + " VALUES('0')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTruckCrane() {
        try {
            for (TruckCrane truckCrane : model.getTruckCranes()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO truckcrane(crane_container_processed) "
                        + " VALUES('0')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
