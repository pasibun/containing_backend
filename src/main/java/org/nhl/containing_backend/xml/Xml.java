package org.nhl.containing_backend.xml;

import org.nhl.containing_backend.communication.Message;
import org.nhl.containing_backend.models.Container;
import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse messages from and to the server.
 */
public class Xml {
    
    public static List<Container> parseContainerXml(InputStream xmlFile) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            XmlFileHandler handler = new XmlFileHandler();
            saxParser.parse(xmlFile, handler);
            return handler.containers;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Tries to decode the incoming XML message and splits it within attributes
     * of this class.
     *
     * @param xmlMessage The xml message you're willing to decode
     */
    /*public static ArrayList<Message> decodeXMLMessage(String xmlMessage) {
        ArrayList<Message> messageList = new ArrayList();
        try {
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlMessage));
            Document doc = db.parse(is);
            NodeList parent = doc.getElementsByTagName("Simulation");
            if (parent.getLength() > 0) {
                NodeList attributes = doc.getElementsByTagName("OK");
                int lengthAttributes = attributes.getLength();
                if (lengthAttributes > 0) {
                    for (int i = 0; i < lengthAttributes; i++) {
                        Message message = new Message();
                        Element element = (Element) attributes.item(i);
                        NodeList currentItem = element.getElementsByTagName("OBJECTNAME");
                        Element line = (Element) currentItem.item(0);
                        message.setObjectName(getCharacterDataFromElement(line));
                        
                        currentItem = element.getElementsByTagName("OBJECTID");
                        line = (Element) currentItem.item(0);
                        
                        currentItem = element.getElementsByTagName("OBJECTSIZE");
                        line = (Element) currentItem.item(0);
                        message.setObjectSize(Integer.parseInt(getCharacterDataFromElement(line)));
                        
                        message.setCommand(Message.Command.OK);
                        
                        messageList.add(message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messageList;
    }*/

    /**
     * Gets the characterdata from the specified element
     */
    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}
