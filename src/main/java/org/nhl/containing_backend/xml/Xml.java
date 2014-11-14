package org.nhl.containing_backend.xml;

import org.nhl.containing_backend.Container;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.List;

/**
 * Parses provided XML files and returns Containers.
 */
public class Xml {
    public static List<Container> parse(InputStream xmlFile) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler();
            saxParser.parse(xmlFile, handler);
            return handler.containers;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
