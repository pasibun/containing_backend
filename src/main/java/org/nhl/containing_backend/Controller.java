package org.nhl.containing_backend;

import org.nhl.containing_backend.communication.Server;
import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.models.Model;
import org.nhl.containing_backend.xml.Xml;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Main controller class.
 * 
*/
public class Controller {

    private Server server;
    private Date currentDate;
    private long startTime;
    private long lastTime;
    //Our project is SECOND_MULTIPLIER faster than real life
    private static final int TIME_MULTIPLIER = 200;
    private Calendar cal;
    private Database database;
    private Model model;
    boolean running;

    public Controller() {
        database = new Database();
        server = new Server();
        model = new Model();
        running = false;
    }

    /**
     * Starts the controller and all the neccesary functions to connect with the
     * simulation
     */
    public void start() {
        model.getContainerPool().addAll(createContainersFromXmlResource());

        startServer();
        waitForServerConnection();
        initDate();
        running = true;
        while (running) {
            if (!server.isRunning()) {
                return;
            }
            updateDate();
            spawnContainersForCurrentDate();

            try {
                Thread.sleep(50);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        server.stop();
        running = false;
    }

    /**
     * Inits the date of the project
     */
    private void initDate() {
        startTime = System.currentTimeMillis();
        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2004);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        currentDate = cal.getTime();
        lastTime = System.currentTimeMillis();
        //database.setup();
    }

    /**
     * Checks whether a second has passed in real life and sets the simulation
     * time according to the SECOND_MULTIPLIER constant value
     *     
     * 1 hour in our project takes about 24 seconds Therefore 1 day in our
     * project = 9.6 Minutes
     *     
     */
    private void updateDate() {
        long curTime = System.currentTimeMillis();

        int deltaTime = (int)(curTime - lastTime);
        cal.add(Calendar.MILLISECOND, deltaTime * TIME_MULTIPLIER);
        currentDate = cal.getTime();

        lastTime = curTime;
    }

    /**
     * Prepares the simulation with some starting data
     */
    private void waitForServerConnection() {
        while (true) {
            if (server.isRunning()) {
                return;
            }
            try {
                Thread.sleep(500);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads the xml files and puts the containers in an arraylist
     */
    private List<Container> createContainersFromXmlResource() {
        List<Container> containers = new ArrayList<Container>();
        try {
            //containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml1.xml")));
            //containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml2.xml")));
            //containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml3.xml")));
            //containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml4.xml")));
            //containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml5.xml")));
            //containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml6.xml")));
            //containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml7.xml")));
            containers.addAll(Xml.parseContainerXml(Controller.class.getResourceAsStream("/xml8.xml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return containers;
    }

    /**
     * Boots up the server and starts sending the first Container data when the
     * ENTER key has been pressed. NOTE: Only press enter when you're sure the
     * server has succesfully connected with the client!
     */
    private void startServer() {
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
    private void moveObject(String objectName, String destination, float speed) {
        String moveMessage = "<Move><objectName>" + objectName + "</objectName><destinationName>" + destination + "</destinationName><speed>" + speed + "</speed></Move>";
        server.writeMessage(moveMessage);
    }

    /**
     * Sends a delete message to the simulation which will delete the object
     *     
     * @param objectName The object we are going to delete
     */
    private void disposeObject(String objectName) {
        String disposeMessage = "<Dispose><objectName>" + objectName + "</objectName></Dispose>";
        server.writeMessage(disposeMessage);
    }

    /**
     * Sends an create container message to the client when there are containers
     * for the current date
     */
    private void spawnContainersForCurrentDate() {
        String createMessage = "";
        int numberOfContainers = 0;
        Iterator<Container> i = model.getContainerPool().iterator();
        while (i.hasNext()) {
            Container container = i.next();
            Date arrivalDate = container.getArrivalDate();
            if (arrivalDate.before(currentDate)) {
                createMessage += createContainerXml(container);
                numberOfContainers++;
                i.remove();
            }
        }
        if (numberOfContainers > 0) {
            server.writeMessage(createMessage);
        }
    }

    private String createContainerXml(Container container) {
        return "<Create><iso>" + container.getIso() + "</iso><owner>" + container.getOwner() +
                "</owner><arrivalTransportType>" + container.getArrivalTransportType() +
                "</arrivalTransportType><xLoc>" + container.getSpawnX() + "</xLoc><yLoc>" + container.getSpawnY() +
                "</yLoc><zLoc>" + container.getSpawnZ() + "</zLoc></Create>";
    }

    /**
     * Provides an user input which will be sent to the Simulation client
     *
     * @throws IOException
     */
    private void prepareMessage() throws IOException {
        System.out.println("Please input a string to send to the simulator :");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        server.writeMessage(inFromUser.readLine());
    }
}