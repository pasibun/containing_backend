package org.nhl.containing_backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Used to communicate with the Simulation
 */
public class Communication {

    private static final int PORT = 6666;

    public enum Status {

        LISTEN, INITIALIZE, SENDING
    };
    private Status status;
    private Socket server;
    private DataInputStream input;
    private DataOutputStream output;
    private Thread operation;
    private ServerSocket serverSocket;
    private String command;
    private String okObject;
    private int okId;

    public Communication() {
        status = Status.INITIALIZE;
    }

    public String getCommand() {
        return command;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Boots up the server
     */
    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(0);
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
        } catch (Exception e) {
            System.out.println("CLIENT NOT FOUND! MAKE SURE THE CLIENT IS ONLINE! RECONNECTING...");
            status = Status.INITIALIZE;
        }
        sleep(100);
    }

    /**
     * Closes the server and stops this thread
     */
    public void closeServer() {
        operation = null;

        try {
            server.close();
            serverSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sends a message to the simulation
     *
     * @param message The message
     */
    public void sendMessage(String message) {
        status = Status.SENDING;
        try {
            System.out.println("Trying to send message " + message + " to the Simulation system!");
            output = new DataOutputStream(server.getOutputStream());
            output.writeUTF(message);
            System.out.println("Sent message " + message + " to the Simulation system!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        status = Status.LISTEN;
    }

    /**
     * Listens to the simulation for output
     */
    public void listen() {
        try {
            input = new DataInputStream(server.getInputStream());
            command = input.readUTF();
            if (command.length() == 0) {
                input.reset();
            } else {
                System.out.println("Received string " + command + " from the simulation system. Now trying to decode... ");
                decodeXMLMessage(command);
            }
        } catch (Exception e) {
            System.out.println("CLIENT NOT FOUND! MAKE SURE THE CLIENT IS ONLINE! RECONNECTING...");
            status = Status.INITIALIZE;
        }
        sleep(3000);
    }

    /**
     * Tries to decode the incoming XML message and splits it within attributes
     * of this class.
     *
     * @param xmlMessage The xml message you're willing to decode
     */
    private void decodeXMLMessage(String xmlMessage) {
        try {
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlMessage));

            Document doc = db.parse(is);

            NodeList nodes = doc.getElementsByTagName("OK");
            int nodesDepth = nodes.getLength();
            if (nodesDepth > 0) {
                for (int i = 0; i < nodesDepth; i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList nodeList = element.getElementsByTagName("OBJECT");
                    Element line = (Element) nodeList.item(0);
                    okObject = getCharacterDataFromElement(line);
                    System.out.println("OBJECT: " + okObject);

                    nodeList = element.getElementsByTagName("OBJECTID");
                    line = (Element) nodeList.item(0);
                    okId = Integer.parseInt(getCharacterDataFromElement(line));
                    System.out.println("OBJECTID: " + okId);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the characterdata from the specified element
     */
    private static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "?";
    }

    /**
     * This method will be called once and loops until the thread stops.
     */
    public void Start() {
        operation = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread thisThread = Thread.currentThread();
                while (operation == thisThread) {
                    try {
                        switch (status) {
                            case INITIALIZE:
                                startServer();
                                status = Status.LISTEN;
                                break;
                            case LISTEN:
                                listen();
                                status = Status.LISTEN;
                                break;
                            case SENDING:
                                status = Status.SENDING;
                                break;
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        operation.setName("Backend Communicator");
        operation.start();

    }

    /**
     * Sleep this thread we are working with for x milliseconds
     *
     * @param milliseconds How long are we waiting in milliseconds?
     */
    public void sleep(int milliseconds) {
        try {
            Thread.currentThread().sleep(milliseconds); //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
