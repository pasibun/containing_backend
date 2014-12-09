/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nhl.containing_backend;

import java.sql.*;
import java.util.List;
import org.nhl.containing_backend.cranes.Crane;
import org.nhl.containing_backend.models.Model;
import org.nhl.containing_backend.models.Storage;
import org.nhl.containing_backend.vehicles.Agv;
import org.nhl.containing_backend.vehicles.Transporter;
import sun.rmi.transport.Transport;

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
    }

    public void setup() {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement statement = conn.createStatement();

            String delete = "DROP TABLE IF EXISTS agv, transporter, storage, "
                    + "dockingcrane, storeagecrane, traincrane, truckcrane";

            statement.executeUpdate(delete);

            String sqlAGV = "CREATE TABLE agv "
                    + " avg_counter INTEGER(50), "
                    + "(agv_id INTEGER(50)) ";

            String sqlDockingCrane = "CREATE TABLE dockingcrane "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " crane_container_processed INTEGER(50), "
                    + " PRIMARY KEY ( id ))";

            String sqlStorageCrane = "CREATE TABLE storeagecrane "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " crane_container_processed INTEGER(50), "
                    + " PRIMARY KEY ( id ))";

            String sqlTrainCrane = "CREATE TABLE traincrane "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " crane_container_processed INTEGER(50), "
                    + " PRIMARY KEY ( id ))";

            String sqlTruckCrane = "CREATE TABLE truckcrane "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " crane_container_processed INTEGER(50), "
                    + " PRIMARY KEY ( id ))";

            String sqlTransporter = "CREATE TABLE transporter "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " transporter_name VARCHAR(255), "
                    + " container_counter INTEGER(50), "
                    + " PRIMARY KEY ( id ))";

            String sqlStorage = "CREATE TABLE storage "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " container_total INT(50), "
                    + " PRIMARY KEY ( id ))";



            statement.executeUpdate(sqlAGV);
            statement.executeUpdate(sqlTransporter);
            statement.executeUpdate(sqlStorage);
            statement.executeUpdate(sqlDockingCrane);
            statement.executeUpdate(sqlStorageCrane);
            statement.executeUpdate(sqlTrainCrane);
            statement.executeUpdate(sqlTruckCrane);

            conn.close();
            startValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startValue() {
        startingValueAGV();
        stringValueDockingCrane();
        stringValueStorageCrane();
        stringValueTrainCrane();
        stringValueTruckCrane();
    }

    private void startingValueAGV() {
        try {
            for (Agv agv : model.getAgvs()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO agv(avg_counter, agv_id) "
                        + " VALUES('0')"
                        + " VALUES('" + agv.getId() + "')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stringValueDockingCrane() {
        try {
            for (Agv agv : model.getAgvs()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO agv(avg_counter, agv_id) "
                        + " VALUES('0')"
                        + " VALUES('" + agv.getId() + "')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stringValueStorageCrane() {
        try {
            for (Agv agv : model.getAgvs()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO agv(avg_counter, agv_id) "
                        + " VALUES('0')"
                        + " VALUES('" + agv.getId() + "')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stringValueTrainCrane() {
        try {
            for (Agv agv : model.getAgvs()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO agv(avg_counter, agv_id) "
                        + " VALUES('0')"
                        + " VALUES('" + agv.getId() + "')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stringValueTruckCrane() {
        try {
            for (Agv agv : model.getAgvs()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO agv(avg_counter, agv_id) "
                        + " VALUES('0')"
                        + " VALUES('" + agv.getId() + "')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
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
}
