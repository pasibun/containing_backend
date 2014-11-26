package org.nhl.containing_backend;

import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.xml.Xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        //controller.startServer();
        int max_x = -1;
        int max_y = -1;
        int max_z = -1;

        List<Container> containers = Xml.parse(Main.class.getResourceAsStream("/xml1.xml"));
        containers.addAll(Xml.parse(Main.class.getResourceAsStream("/xml2.xml")));
        containers.addAll(Xml.parse(Main.class.getResourceAsStream("/xml3.xml")));
        containers.addAll(Xml.parse(Main.class.getResourceAsStream("/xml4.xml")));
        containers.addAll(Xml.parse(Main.class.getResourceAsStream("/xml5.xml")));
        containers.addAll(Xml.parse(Main.class.getResourceAsStream("/xml6.xml")));
        containers.addAll(Xml.parse(Main.class.getResourceAsStream("/xml7.xml")));

        printStats(containers, "trein");
        printStats(containers, "zeeschip");
        printStats(containers, "vrachtauto");
        printStats(containers, "binnenschip");
    }

    public static void printStats(List<Container> containers, String type) {
        List<Container> typeContainers = listContainerType(containers, type);
        List<Integer> typeX = new ArrayList<Integer>();
        List<Integer> typeY = new ArrayList<Integer>();
        List<Integer> typeZ = new ArrayList<Integer>();

        for (Container c : typeContainers) {
            int x = (int)c.getSpawnX();
            int y = (int)c.getSpawnY();
            int z = (int)c.getSpawnZ();

            typeX.add(x);
            typeY.add(y);
            typeZ.add(z);
        }
        System.out.println(type + " x: " + makeOccurrencesDict(typeX));
        System.out.println(type + " y: " + makeOccurrencesDict(typeY));
        System.out.println(type + " z: " + makeOccurrencesDict(typeZ));
    }

    public static HashMap<Integer, Integer> makeOccurrencesDict(List<Integer> values) {
        HashMap<Integer, Integer> dict = new HashMap<Integer, Integer>();

        for (int i : values) {
            if (!dict.containsKey(i)) {
                dict.put(i, 1);
            } else {
                dict.put(i, dict.get(i) + 1);
            }
        }

        return dict;
    }

    public static List<Container> listContainerType(List<Container> containers, String type) {
        List<Container> newContainers = new ArrayList<Container>();
        for (Container c : containers) {
            if (c.getArrivalTransportType().equals(type)) {
                newContainers.add(c);
            }
        }
        return newContainers;
    }
}
