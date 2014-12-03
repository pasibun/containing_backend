/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nhl.containing_backend;

import java.sql.*;
import java.util.List;
import org.nhl.containing_backend.models.Model;
import org.nhl.containing_backend.vehicles.Agv;

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

    public Database() {
    }

    public void startDatabase() {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement statement = conn.createStatement();

            String delete = "DROP TABLE IF EXISTS agv, transporter, storage, railcrane, movablecrane";

            statement.executeUpdate(delete);

            String sqlAGV = "CREATE TABLE agv "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " agv_name VARCHAR(255), "
                    + " PRIMARY KEY ( id ))";
            String sqlTransporter = "CREATE TABLE transporter "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " transporter_name VARCHAR(255), "
                    + " PRIMARY KEY ( id ))";
            String sqlStorage = "CREATE TABLE storage "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " container_name VARCHAR(255), "
                    + " container_total INT(255), "
                    + " PRIMARY KEY ( id ))";
            String sqlRailCrane = "CREATE TABLE railCrane "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " railcrane_name VARCHAR(255), "
                    + " PRIMARY KEY ( id ))";
            String sqlMovableCrane = "CREATE TABLE movableCrane "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " movablecrane_name VARCHAR(255), "
                    + " PRIMARY KEY ( id ))";

            statement.executeUpdate(sqlAGV);
            statement.executeUpdate(sqlTransporter);
            statement.executeUpdate(sqlStorage);
            statement.executeUpdate(sqlRailCrane);
            statement.executeUpdate(sqlMovableCrane);

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDatabaseAGV() {
        try {
            model = new Model();
            model.createAgv();
            for (Agv agv : model.getAgvs()) {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);
                Statement st = conn.createStatement();

                String sqlupdate = "INSERT INTO agv(agv_name) "
                        + " VALUES('avg')";
                st.executeUpdate(sqlupdate);

                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
