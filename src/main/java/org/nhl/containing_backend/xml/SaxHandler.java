package org.nhl.containing_backend.xml;

import org.nhl.containing_backend.models.Container;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Handler class for use in Xml.
 */
class SaxHandler extends DefaultHandler {
    public List<Container> containers = new ArrayList<Container>();

    private Stack<String> elementStack = new Stack<String>();
    private Stack<Container> containerStack  = new Stack<Container>();

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {
        elementStack.push(qName);

        // Start of container.
        if (qName.equals("record")) {
            Container container = new Container();
            containerStack.push(container);
            currentElement();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        String element = elementStack.pop();

        if (qName.equals("record")) {
            Container container = containerStack.pop();
            containers.add(container);
        }
    }

    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        String value = new String(ch, start, length).trim();
        if (value.length() == 0) {
            return; // ignore white space
        }

        Container container = null;

        try {
            container = containerStack.peek();
        } catch (EmptyStackException e) {
        }

        if (currentElement().equals("d") && currentElementGrandParent().equals("aankomst")) {
            container.setArrivalDay(Integer.parseInt(value));
        } else if (currentElement().equals("m") && currentElementGrandParent().equals("aankomst")) {
            container.setArrivalMonth(Integer.parseInt(value));
        } else if (currentElement().equals("j") && currentElementGrandParent().equals("aankomst")) {
            container.setArrivalYear(Integer.parseInt(value));
        } else if (currentElement().equals("van") && currentElementGrandParent().equals("aankomst")) {
            container.setArrivalSpanStart(value);
        } else if (currentElement().equals("tot") && currentElementGrandParent().equals("aankomst")) {
            container.setArrivalSpanEnd(value);
        } else if (currentElement().equals("soort_vervoer") && currentElementParent().equals("aankomst")) {
            container.setArrivalTransportType(value);
        } else if (currentElement().equals("bedrijf") && currentElementParent().equals("aankomst")) {
            container.setArrivalCompany(value);
        } else if (currentElement().equals("x")) {
            container.setSpawnX(Integer.parseInt(value));
        } else if (currentElement().equals("y")) {
            container.setSpawnY(Integer.parseInt(value));
        } else if (currentElement().equals("z")) {
            container.setSpawnZ(Integer.parseInt(value));
        } else if (currentElement().equals("naam") && currentElementParent().equals("eigenaar")) {
            container.setOwner(value);
        } else if (currentElement().equals("containernr")) {
            container.setNumber(Integer.parseInt(value));
        } else if (currentElement().equals("d") && currentElementGrandParent().equals("vertrek")) {
            container.setDepartureDay(Integer.parseInt(value));
        } else if (currentElement().equals("m") && currentElementGrandParent().equals("vertrek")) {
            container.setDepartureMonth(Integer.parseInt(value));
        } else if (currentElement().equals("j") && currentElementGrandParent().equals("vertrek")) {
            container.setDepartureYear(Integer.parseInt(value));
        } else if (currentElement().equals("van") && currentElementGrandParent().equals("vertrek")) {
            container.setDepartureSpanStart(value);
        } else if (currentElement().equals("tot") && currentElementGrandParent().equals("vertrek")) {
            container.setDepartureSpanEnd(value);
        } else if (currentElement().equals("soort_vervoer") && currentElementParent().equals("vertrek")) {
            container.setDepartureTransportType(value);
        } else if (currentElement().equals("bedrijf") && currentElementParent().equals("vertrek")) {
            container.setDepartureCompany(value);
        } else if (currentElement().equals("l")) {
            container.setLength(Container.calculateLength(value));
        } else if (currentElement().equals("b")) {
            container.setWidth(Container.calculateLength(value));
        } else if (currentElement().equals("h")) {
            container.setHeight(Container.calculateLength(value));
        } else if (currentElement().equals("leeg")) {
            container.setEmptyWeight((float)Integer.parseInt(value));
        } else if (currentElement().equals("inhoud") && currentElementParent().equals("gewicht")) {
            container.setContentsWeight((float)Integer.parseInt(value));
        } else if (currentElement().equals("naam") && currentElementParent().equals("inhoud")) {
            container.setContentsName(value);
        } else if (currentElement().equals("soort") && currentElementParent().equals("inhoud")) {
            container.setContentsType(value);
        } else if (currentElement().equals("gevaar") && currentElementParent().equals("inhoud")) {
            container.setContentsDanger(value);
        } else if (currentElement().equals("ISO")) {
            container.setIso(value);
        } else {
            throw new SAXNotRecognizedException(currentElement() + "not recognised.");
        }

    }

    @Override
    public void ignorableWhitespace(char ch[], int start, int length)
            throws SAXException {
    }

    private String currentElement() {
        return elementStack.peek();
    }

    private String currentElementParent() {
        if (elementStack.size() < 2) {
            return null;
        }
        return elementStack.get(elementStack.size()-2);
    }

    private String currentElementGrandParent() {
        if (elementStack.size() < 3) {
            return null;
        }
        return elementStack.get(elementStack.size()-3);
    }
}
