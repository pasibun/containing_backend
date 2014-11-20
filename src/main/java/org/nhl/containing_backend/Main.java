package org.nhl.containing_backend;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        controller.startServer();
        controller.prepareMessage();
    }
}
