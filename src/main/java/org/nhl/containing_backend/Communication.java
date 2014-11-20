package org.nhl.containing_backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/*
 * Commands:
 * 
 * CREATE + [OJECT] + {DATA} + /CEATE
 * MOVE + [OBJECT] + [DESTINATION X,DESTINATION Y} + [SPEED] + /MOVE
 * DISPOSE + [OBJECT] + [DESTINATION X, DESTINATION Y] + SPEED + /DISPOSE
 * 
 */

/**
 *
 * Used to communicate with the Simulation
 *
 */
public class Communication {

    private ServerSocket serverSocket;

    private enum Status {

        LISTEN, INITIALIZE, DISPOSE, SENDING
    };
    private Status status;
    private Socket server;
    private DataInputStream input;
    private DataOutputStream output;
    private Thread operation;
    private final int PORT = 6666;
    private String commando;

    public Communication() {
        status = Status.INITIALIZE;
    }

    public String getCommando() {
        return commando;
    }

    /**
     * Boots up the server
     */
    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setSoTimeout(0);
            System.out.println("Waiting for client on port "
                    + serverSocket.getLocalPort() + "...");
            server = serverSocket.accept();
            System.out.println("Just connected to "
                    + server.getRemoteSocketAddress());
        } catch (Exception e) {
        }

        sleep(100);
    }

    /**
     * Closes the server and stops this thread
     */
    public void closeServer() {
        try {
            server.close();
            status = Status.DISPOSE;
        } catch (Exception e) {
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
            //server = serverSocket.accept();
            System.out.println("Trying to send message " + message + " to the Simulation system!");
            output = new DataOutputStream(server.getOutputStream());
            output.writeUTF(message);
            System.out.println("Sent message " + message + " to the Simulation system!");
        } catch (Exception e) {
        }
        status = Status.LISTEN;
    }

    /**
     * Listens to the simulation for output
     */
    public void listen() {
        try {
            //server = serverSocket.accept();
            input = new DataInputStream(server.getInputStream());
            commando = input.readUTF();
            if (commando.equals("")) {
                input.reset();
            } else {
                System.out.println("Recieved string " + commando + " from the simulation system! ");
            }
        } catch (Exception e) {
        }
    }

    /**
     * This method will be called once and loops until the thread stops.
     */
    public void Start() {
        operation = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
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
                            case DISPOSE:
                                operation.stop();
                                break;
                        }
                    } catch (Throwable e) {
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
            operation.currentThread().sleep(milliseconds); //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            operation.currentThread().interrupt();
        }
    }
}
