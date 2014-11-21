package org.nhl.containing_backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.xml.Xml;

/**
 * Main controller class.
 */
public class Controller {

    private Communication server;
    private ArrayList<Container> containers;
    private Date currentDate;

    public Controller() {
        server = new Communication();
        containers = new ArrayList();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2004);
        cal.set(Calendar.MONTH, 12);
        cal.set(Calendar.DAY_OF_MONTH, 13);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date dateRepresentation = cal.getTime();
        currentDate = dateRepresentation;
    }

    /**
     * Reads the xml files and puts the containers in an arraylist
     */
    public void readXml() {
        try {
            containers.addAll(Xml.parse(Controller.class.getResourceAsStream("/xml1.xml")));
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
        server.Start();
        readXml();
        System.out.println("Press ENTER to proceed when the server is connected with the client...");
        Scanner keyboard = new Scanner(System.in);
        keyboard.nextLine();
        createContainer();
    }

    /**
     * Sends an create container message to the client when there are containers
     * for the current date
     */
    public void createContainer() {
        String createMessage;
        int numberOfContainers = 0;
        for (Container c : containers) {
            Date d = c.getArrivalDate();
            if (d.equals(currentDate)) {
                createMessage = "<Create><iso>" + c.getIso() + "</iso><owner>" + c.getOwner() + "</owner><arrivalTransportType>" + c.getArrivalTransportType() + "</arrivalTransportType></Create>";
                server.sendMessage(createMessage);
                numberOfContainers++;
            }
        }
        server.sendMessage("<LastMessage><numberOfContainers>" + numberOfContainers + "</numberOfContainers></LastMessage>");
    }

    /**
     * Provides an user input which will be sent to the Simulation client
     *
     * @throws IOException
     */
    public void prepareMessage() throws IOException {
        System.out.println("Please input a string to send to the simulator :");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        server.sendMessage(inFromUser.readLine());
    }
}
