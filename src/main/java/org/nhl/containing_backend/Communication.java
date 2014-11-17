package org.nhl.containing_backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * Used to communicate with the Simulation
 */
public class Communication {

    private ServerSocket serverSocket;

    private enum statusEnum {

        LISTEN, INITIALIZE, DISPOSE, SENDING
    };
    private statusEnum status;
    private Socket server;
    private DataInputStream input;
    private DataOutputStream output;
    private Thread operation;
    private final int PORT = 6666;

    public Communication() {
        update();
        status = status.INITIALIZE;
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
            e.printStackTrace();
        }
    }

    /**
     * Closes the server and stops this thread
     */
    public void closeServer() {
        try {
            server.close();
            status = status.DISPOSE;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the simulation
     *
     * @param message The message
     */
    public void sendMessage(String message) {
        status = status.SENDING;
        try {
            //server = serverSocket.accept();
            output = new DataOutputStream(server.getOutputStream());
            output.writeUTF(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        status = status.LISTEN;
    }

    /**
     * Listens to the simulation for output
     */
    public void listen() {
        try {
            //server = serverSocket.accept();
            input = new DataInputStream(server.getInputStream());
            String outputString = input.readUTF();
            if (outputString == "") {
                input.reset();
            } else {
                System.out.println("Recieved string " + outputString + " from the simulation system! ");
            }

            //input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update() {
        operation = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        switch (status) {
                            case INITIALIZE:
                                startServer();
                                status = status.LISTEN;
                                break;
                            case LISTEN:
                                listen();
                                status = status.LISTEN;
                                break;
                            case SENDING:
                                status = status.SENDING;
                                break;
                            case DISPOSE:
                                operation.stop();
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
}
