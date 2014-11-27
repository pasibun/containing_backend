package org.nhl.containing_backend;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 */
public class SendRunnable implements Runnable {
    BufferedWriter out;
    ConcurrentLinkedQueue<String> queue;

    private boolean running;

    public SendRunnable(BufferedWriter out) {
        this.out = out;
        this.queue = new ConcurrentLinkedQueue<String>();
    }

    @Override
    public void run() {
        String outputLine;
        running = true;

        while (running) {
            outputLine = queue.poll();
            if (outputLine == null) {
                try {
                    Thread.sleep(500);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            } else {
                // Send outputLine to client
                try {
                    out.write(outputLine);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Left SendRunnable");
    }

    public void stop() {
        running = false;
    }

    public void writeMessage(String message) {
        queue.add(message);
    }
}
