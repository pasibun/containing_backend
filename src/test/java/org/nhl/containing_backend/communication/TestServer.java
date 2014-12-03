package org.nhl.containing_backend.communication;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

class Client implements Runnable {
    private final int portNumber = 6666;
    private final String serverName = "localhost";
    private Socket socket;
    private ListenRunnable listenRunnable;
    private SendRunnable sendRunnable;

    private boolean running;

    public Client() {

    }

    @Override
    public void run() {
        try {
            // Open up the socket.
            socket = new Socket(serverName, portNumber);

            listenRunnable = new ListenRunnable(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            sendRunnable = new SendRunnable(new PrintWriter(socket.getOutputStream(), true));

            Thread listenThread = new Thread(listenRunnable);
            listenThread.setName("ListenThread");
            Thread sendThread = new Thread(sendRunnable);
            sendThread.setName("SendThread");

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
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if (!listenRunnable.isRunning()) {
                this.stop();
            }
        }

    }

    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

public class TestServer {
    private Server server;
    private Client client;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Code executed before the first test method
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // Code executed after the last test method
    }

    @Before
    public void setUp() throws Exception {
        server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();

        sleep(5000);

        client = new Client();
        Thread clientThread = new Thread(client);
        clientThread.start();
        sleep(5000);
    }

    @Test
    public void testWriteMessageFromClientToServer() {
        client.writeMessage("Hello, world!");
        sleep(5000);
        assertEquals("Hello, world!", server.getMessage());
    }

    @Test
    public void testWriteMessageFromServerToClient() {
        server.writeMessage("Hello, world!");
        sleep(5000);
        assertEquals("<Controller>Hello, world!</Controller>", client.getMessage());
    }

    @Test
    public void testServerShutsDownQuitMessage() {
        client.writeMessage("quit");
        sleep(5000);
        assertFalse(server.isRunning());
    }

    @Test
    public void testServerShutsDownConnectionLost() {
        client.stop();
        sleep(5000);
        assertFalse(server.isRunning());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
        client.stop();
        sleep(5000);
    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
