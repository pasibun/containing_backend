package org.nhl.containing_backend;

import org.nhl.containing_backend.communication.Server;
import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.xml.Xml;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Main controller class.
 * 
*/
public class Controller {

    private Server server;
    private ArrayList<Container> containers;
    private Date currentDate;
    private long startTime;
//Our project is SECOND_MULTIPLIER faster than real life
    private static final int SECOND_MULTIPLIER = 200;
    private Calendar cal;

    public Controller() {
        server = new Server();
        containers = new ArrayList();
    }

    /**
     * Starts the controller and all the neccesary functions to connect with the
     * simulation
     */
    public void start() {
        readXml();
        startServer();
        prepareSimulation();
        initDate();
        while (true) {
            checkDate();
            createContainer();
//Backend loop!
        }
    }

    /**
     * Inits the date of the project
     */
    public void initDate() {
        startTime = System.currentTimeMillis();
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2004);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date dateRepresentation = cal.getTime();
        currentDate = dateRepresentation;
    }

    /**
     * Checks wether a second has passed in real life and sets the simulation
     * time according to the SECOND_MULTIPLIER constant value
     *     
* 1 hour in our project takes about 24 seconds Therefore 1 day in our
     * project = 9.6 Minutes
     *     
*/
    public void checkDate() {
        long curTime = System.currentTimeMillis();
        if (curTime - startTime > 1000 / SECOND_MULTIPLIER) {
            startTime = System.currentTimeMillis();
            cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + 1);
            Date dateRepresentation = cal.getTime();
            currentDate = dateRepresentation;
        }
    }

    /**
     * Prepares the simulation with some starting data
     */
    public void prepareSimulation() {
        System.out.println("Press ENTER to start the simulation when the server is connected with the client...");
        Scanner keyboard = new Scanner(System.in);
        keyboard.nextLine();
    }

    /**
     * Reads the xml files and puts the containers in an arraylist
     */
    public void readXml() {
        try {
            containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml1.xml")));
            containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml2.xml")));
            containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml3.xml")));
            containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml4.xml")));
            containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml5.xml")));
            containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml6.xml")));
            //containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml7.xml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Boots up the server and starts sending the first Container data when the
     * ENTER key has been pressed. NOTE: Only press enter when you're sure the
     * server has succesfully connected with the client!
     */
    public void startServer() {
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    /**
     * Sends a move message to the simulation
     *     
* @param objectName The object we are going to move
     * @param destination The destination where this object will be going to
     * @param speed the speed of the movement
     */
    public void moveObject(String objectName, String destination, float speed) {
        String moveMessage = "<Move><objectName>" + objectName + "</objectName><destinationName>" + destination + "</destinationName><speed>" + speed + "</speed></Move>";
        server.writeMessage(moveMessage);
    }

    /**
     * Sends a delete message to the simulation which will delete the object
     *     
* @param objectName The object we are going to delete
     */
    public void disposeObject(String objectName) {
        String disposeMessage = "<Dispose><objectName>" + objectName + "</objectName></Dispose>";
        server.writeMessage(disposeMessage);
    }

    /**
     * Sends an create container message to the client when there are containers
     * for the current date
     */
    public void createContainer() {
        String createMessage = "";
        int numberOfContainers = 0;
        Iterator<Container> i = containers.iterator();
        while (i.hasNext()) {
            Container c = i.next();
            Date d = c.getArrivalDate();
            if (d.equals(currentDate)) {
                createMessage = createMessage + "<Create><iso>" + c.getIso() + "</iso><owner>" + c.getOwner() + "</owner><arrivalTransportType>" + c.getArrivalTransportType() + "</arrivalTransportType><xLoc>" + c.getSpawnX() + "</xLoc><yLoc>" + c.getSpawnY() + "</yLoc><zLoc>" + c.getSpawnZ() + "</zLoc></Create>";
                numberOfContainers++;
                i.remove();
            }
        }
        if (numberOfContainers > 0) {
            server.writeMessage(createMessage);
        }
        numberOfContainers = 0;
    }

    /**
     * Provides an user input which will be sent to the Simulation client
     *     
* @throws IOException
     */
    public void prepareMessage() throws IOException {
        System.out.println("Please input a string to send to the simulator :");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        server.writeMessage(inFromUser.readLine());
    }
}