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
    private String dbName = "containing";
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
            Connection conn = DriverManager.getConnection(url, userName, password);
            Statement statement = conn.createStatement();

            String create = "CREATE DATABASE " + dbName;
            try {
                statement.executeUpdate(create);
            } catch (Exception e) {
            }
            conn = DriverManager.getConnection(url + dbName, userName, password);
            statement = conn.createStatement();

            String delete = "DROP TABLE IF EXISTS agv, seacrane, inlandcrane, storagecrane, traincrane, truckcrane, transporter, storage";

            statement.executeUpdate(delete);

            String sqlAGV = "CREATE TABLE agv "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " agv_counter int, "
                    + " PRIMARY KEY ( id ))";

            String sqlSeaCrane = "CREATE TABLE seacrane "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " crane_container_processed int, "
                    + " PRIMARY KEY ( id ))";
            
            String sqlInlandCrane = "CREATE TABLE inlandcrane "
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
                    + " container_counter int DEFAULT 1, "
                    + " PRIMARY KEY ( id ))";

            String sqlStorage = "CREATE TABLE storage "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " container_total int, "
                    + " PRIMARY KEY ( id ))";

            statement.executeUpdate(sqlAGV);
            statement.executeUpdate(sqlTransporter);
            statement.executeUpdate(sqlStorage);
            statement.executeUpdate(sqlSeaCrane);
            statement.executeUpdate(sqlInlandCrane);
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

            String sqlupdate = "UPDATE transporter "
                    + "SET container_counter = (container_counter + " + transport.getContainers().size() + ") "
                    + "WHERE transporter_name = '" + transport.getType() + "'";
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

            String sqlupdate = "UPDATE transporter "
                    + "SET container_total = " + storage.getContainers().size() + "";

            st.executeUpdate(sqlupdate);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update Database for docking crane Inland
     *
     * @param dockingCrane
     */
    public void updateDockingCraneInland(Crane dockingCrane) {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();

            String sqlupdate = "UPDATE inlandcrane "
                    + "SET crane_container_processed = (crane_container_processed + 1)"
                    + "WHERE id='" + dockingCrane.getId() + "'";

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
    public void updateDockingCraneSea(Crane dockingCrane) {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();

            String sqlupdate = "UPDATE seacrane "
                    + "SET crane_container_processed = (crane_container_processed + 1)"
                    + "WHERE id='" + dockingCrane.getId() + "'";

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
    public void updateTrainCrane(Crane trainCrane) {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();

            String sqlupdate = "UPDATE traincrane "
                    + "SET crane_container_processed = (crane_container_processed + 1)"
                    + "WHERE id='" + trainCrane.getId() + "'";

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
    public void updateTruckCrane(Crane truckCrane) {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();

            String sqlupdate = "UPDATE truckcrane "
                    + "SET crane_container_processed = (crane_container_processed + 1)"
                    + "WHERE id='" + truckCrane.getId() + "'";

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
        initDockingCraneInland();
        initDockingCraneSea();
        initStorageCrane();
        initTrainCrane();
        initTruckCrane();
        initTransporter();
    }

    private void initTransporter() {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();

            String sqlupdate1 = "INSERT INTO transporter(transporter_name) "
                    + "VALUES ('trein') ";

            String sqlupdate2 = "INSERT INTO transporter(transporter_name) "
                    + "VALUES ('vrachtauto')";

            String sqlupdate3 = "INSERT INTO transporter(transporter_name) "
                    + "VALUES ('zeeschip')";

            String sqlupdate4 = "INSERT INTO transporter(transporter_name) "
                    + "VALUES ('binnenschip')";



            st.executeUpdate(sqlupdate1);
            st.executeUpdate(sqlupdate2);
            st.executeUpdate(sqlupdate3);
            st.executeUpdate(sqlupdate4);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private void initDockingCraneInland() {
        try {
            for (DockingCraneInlandShip dockingCraneInland : model.getDockingCranesInland()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO inlandcrane(crane_container_processed) "
                        + " VALUES('0')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDockingCraneSea() {
        try {
            for (DockingCraneSeaShip dockingCraneSea : model.getDockingCranesSea()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO seacrane(crane_container_processed) "
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
