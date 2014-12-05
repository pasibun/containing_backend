package org.nhl.containing_backend;

import org.nhl.containing_backend.communication.*;
import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.models.Model;
import org.nhl.containing_backend.vehicles.Transporter;
import org.nhl.containing_backend.xml.Xml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.StringReader;
import java.util.*;
import java.util.List;

/**
 * Main controller class.
 */
public class Controller implements Runnable {

    //Our project is SECOND_MULTIPLIER faster than real life
    private static final int TIME_MULTIPLIER = 200;
    private boolean running;
    private Server server;
    private Date currentDate;
    private long startTime;
    private long lastTime;
    private Calendar cal;
    private Database database;
    private Model model;
    private List<Message> messagePool;

    public Controller() {
        database = new Database();
        server = new Server();
        model = new Model();
        messagePool = new ArrayList<Message>();
        running = false;
    }

    /**
     * Starts the controller and all the necessary initialisations.
     */
    public void start() {
        model.getContainerPool().addAll(createContainersFromXmlResource());
        startServer();
        waitForServerConnection();
        initDate(); // Keep this as CLOSE to `while (running)` as possible.
        int sumTime = Integer.MAX_VALUE;
        running = true;
        while (running) {
            if (!server.isRunning()) {
                return;
            }
            long curTime = System.currentTimeMillis();
            int deltaTime = (int) (curTime - lastTime);
            updateDate(deltaTime);
            sumTime += deltaTime;

            if (sumTime > 1000) {
                spawnTransporters();
                sumTime = 0;
            }
            assignTransportersToDepots();

            handleOkMessages();
            
            lastTime = curTime;

            try {
                Thread.sleep(50);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shuts down the server and sets running to false.
     */
    public void stop() {
        server.stop();
        running = false;
    }

    /**
     * Initialises the simulation date.
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
    }

    /**
     * Updates the simulation date.
     * <p/>
     * Compares the time since the last function call to the current time. This is the delta time.
     * The delta time is added to the simulation date, multiplied by the specified TIME_MULTIPLIER.
     */
    private void updateDate(int deltaTime) {
        cal.add(Calendar.MILLISECOND, deltaTime * TIME_MULTIPLIER);
        currentDate = cal.getTime();
    }

    /**
     * Starts up the server in a separate thread.
     */
    private void startServer() {
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    /**
     * Halts the program until the server has connected to a client.
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
     * Return a list of all containers described in the XML files.
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
     * Pops the containers from the pool that are set to be dispatched for the current date.
     */
    private List<Container> containersForCurrentDate() {
        List<Container> result = new ArrayList<Container>();

        Iterator<Container> i = model.getContainerPool().iterator();
        while (i.hasNext()) {
            Container container = i.next();
            Date arrivalDate = container.getArrivalDate();
            if (arrivalDate.before(currentDate)) {
                result.add(container);
                i.remove();
            }
        }

        return result;
    }

    /**
     * Spawns new transporters if the time is right.
     */
    private void spawnTransporters() {
        List<Container> containers = containersForCurrentDate();

        if (containers.size() == 0) {
            return;
        }

        List<Transporter> transporters = distributeContainers(containers);
        model.getTransporters().addAll(transporters);

        for (Transporter transporter : transporters) {
            CreateMessage message = new CreateMessage(transporter);
            messagePool.add(message);
            transporter.setProcessingMessageId(message.getId());
            server.writeMessage(message.generateXml());
        }
    }

    /**
     * Distributes the provided containers over a list of newly generated transporters.
     * </p>
     * WARNING: Method is butt-ugly.
     *
     * @param containers Containers that have to arrive in harbour.
     * @return Transporters loaded with containers that are ready to arrive.
     */
    private List<Transporter> distributeContainers(List<Container> containers) {
        List<Container> lorryContainers = new ArrayList<Container>();
        List<Container> trainContainers = new ArrayList<Container>();
        List<Container> inlandshipContainers = new ArrayList<Container>();
        List<Container> seashipContainers = new ArrayList<Container>();

        String[] types = new String[]{"vrachtauto", "trein", "binnenschip", "zeeschip"};

        // Create separate lists for all transporter types.
        for (Container container : containers) {
            if (container.getArrivalTransportType().equals(types[0])) {
                lorryContainers.add(container);
            } else if (container.getArrivalTransportType().equals(types[1])) {
                trainContainers.add(container);
            } else if (container.getArrivalTransportType().equals(types[2])) {
                inlandshipContainers.add(container);
            } else if (container.getArrivalTransportType().equals(types[3])) {
                seashipContainers.add(container);
            }
        }

        List<List<Container>> listOfLists = new ArrayList<List<Container>>();
        listOfLists.add(lorryContainers);
        listOfLists.add(trainContainers);
        listOfLists.add(inlandshipContainers);
        listOfLists.add(seashipContainers);

        List<Transporter> result = new ArrayList<Transporter>();

        int counter = 0;

        // Loop over all the lists.
        for (List<Container> containers_ : listOfLists) {
            String type = types[counter];

            if (containers_.size() == 0) {
                counter++;
                continue;
            }

            // Create a dictionary where a key is a (x, y, z) coordinate, and the value is a list of containers.
            Map<String, List<Container>> dict = new HashMap<String, List<Container>>();

            // Distribute the containers over the dictionary.
            for (Container container : containers_) {
                String point = "";
                point += container.getSpawnX() + ",";
                point += container.getSpawnY() + ",";
                point += container.getSpawnZ();

                if (!dict.containsKey(point)) {
                    List<Container> pointList = new ArrayList<Container>();
                    pointList.add(container);
                    dict.put(point, pointList);
                } else {
                    dict.get(point).add(container);
                }
            }

            // Figure out the biggest list in the dictionary. This is also the amount of transporters.
            int amountOfTransporters = 0;
            for (List<Container> containerList : dict.values()) {
                if (amountOfTransporters < containerList.size()) {
                    amountOfTransporters = containerList.size();
                }
            }

            // Figure out the boundaries of the transporters.
            int limitX = 1;
            int limitY = 1;
            int limitZ = 1;
            for (String point : dict.keySet()) {
                String[] coords = point.split(",");
                if (limitX < Integer.parseInt(coords[0]) + 1) {
                    limitX = Integer.parseInt(coords[0]) + 1;
                }
                if (limitY < Integer.parseInt(coords[1]) + 1) {
                    limitY = Integer.parseInt(coords[1]) + 1;
                }
                if (limitZ < Integer.parseInt(coords[2]) + 1) {
                    limitZ = Integer.parseInt(coords[2]) + 1;
                }
            }

            // For every transporter, fill it with containers and add it to the list.
            for (int i = 0; i < amountOfTransporters; i++) {
                Transporter transporter = new Transporter(type, limitX, limitY, limitZ);

                Iterator iterator = dict.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, List<Container>> pair = (Map.Entry<String, List<Container>>) iterator.next();
                    Point point = new Point(Integer.parseInt(pair.getKey().split(",")[0]),
                            Integer.parseInt(pair.getKey().split(",")[1]));
                    Container container = null;
                    try {
                        container = pair.getValue().remove(0);
                    } catch (IndexOutOfBoundsException e) {
                        iterator.remove();
                        continue;
                    }
                    transporter.putContainer(point, container);
                }
                if (transporter.getContainers().size() > 0) {
                    result.add(transporter);
                }
            }

            counter++;
        }

        return result;
    }

    /**
     * From the pool of transporters that are currently out of bounds, assign as much of them as possible to a depot
     * that isn't currently being used.
     */
    private void assignTransportersToDepots() {
        HashMap<String, List<Integer>> availableDepots = model.availableDepots();

        for (Transporter transporter : model.getTransporters()) {
            // If the transporter is doing nothing, has finished processing its message, and there is a free depot.
            if (!transporter.isOccupied() && transporter.getProcessingMessageId() == -1 &&
                    !availableDepots.get(transporter.getType()).isEmpty()) {
                // In the array of depots for the type of the transporter, set the first available depot to the current
                // transporter.
                int spot = availableDepots.get(transporter.getType()).remove(0);
                model.getDepots().get(transporter.getType())[spot] = transporter;
                transporter.setOccupied(true);

                ArriveMessage message = new ArriveMessage(transporter, spot);
                messagePool.add(message);
                transporter.setProcessingMessageId(message.getId());
                server.writeMessage(message.generateXml());
            }
        }
    }

    /**
     * Processes all received OK messages. Removes them from the pool and sets the message processors to -1.
     */
    private void handleOkMessages() {
        List<String> xmlMessages = new ArrayList<String>();
        while (true) {
            String xmlMessage = server.getMessage();
            if (xmlMessage == null) {
                break;
            }
            xmlMessages.add(xmlMessage);
        }

        for (String message : xmlMessages) {
            try {
                handleOkMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void handleOkMessage(String xmlMessage) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource source = new InputSource();
        source.setCharacterStream(new StringReader(xmlMessage));

        Document doc = db.parse(source);

        NodeList nodes = doc.getElementsByTagName("id");
        if (nodes.getLength() != 1) {
            throw new Exception(xmlMessage + " is not a valid message");
        }

        Element line = (Element) nodes.item(0);
        int id = Integer.parseInt(Xml.getCharacterDataFromElement(line));

        Message message = null;
        boolean nobreak = true;

        for (Message message_ : messagePool) {
            if (message_.getId() == id) {
                message = message_;
                nobreak = false;
                break;
            }
        }
        if (nobreak) {
            throw new Exception(id + " doesn't exist");
        }

        ProcessesMessage processor = message.getProcessor();
        processor.setProcessingMessageId(-1);
        // Maybe set occupied to false as well? Not sure yet.
        messagePool.remove(message);
    }

    @Override
    public void run() {
        start();
    }
}