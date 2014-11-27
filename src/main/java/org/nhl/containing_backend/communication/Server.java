package org.nhl.containing_backend.communication;

import org.nhl.containing_backend.communication.ListenRunnable;
import org.nhl.containing_backend.communication.SendRunnable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server.
 */
public class Server implements Runnable {
    private ServerSocket serverSocket;
    private final int portNumber = 6666;

    private Socket clientSocket;

    private ListenRunnable listenRunnable;
    private SendRunnable sendRunnable;

    private boolean running;

    public Server() {

    }

    @Override
    public void run() {
        try {
            // Open up the socket.
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Waiting for client");

            // Wait for client.
            clientSocket = serverSocket.accept();
            System.out.println("Have client");

            listenRunnable = new ListenRunnable(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
            sendRunnable = new SendRunnable(new PrintWriter(clientSocket.getOutputStream(), true));

            Thread listenThread = new Thread(listenRunnable);
            Thread sendThread = new Thread(sendRunnable);

            listenThread.start();
            sendThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        running = true;

        while (running) {
            try {
                // Do nothing.
                Thread.sleep(1000);
                // In case the client shut down the listener, shut down everything.
                if (!listenRunnable.isRunning()) {
                    this.stop();
                }
                System.out.println("Still alive");
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            listenRunnable.stop();
        } catch (Throwable e) {
        }
        try {
            sendRunnable.stop();
        } catch (Throwable e) {
        }
        running = false;
    }

    public String getMessage() {
        return listenRunnable.getMessage();
    }

    public void writeMessage(String message) {
        sendRunnable.writeMessage(message);
    }
}
