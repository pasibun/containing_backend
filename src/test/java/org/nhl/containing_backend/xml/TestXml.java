package org.nhl.containing_backend.xml;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.nhl.containing_backend.models.Container;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests XML parsing
 */
public class TestXml {
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
        // Code executed before each test
    }

    @Test
    public void testParse() {
        List<Container> containers;
        try {
            containers = Xml.parse(TestXml.class.getResourceAsStream("/test.xml"));
        }
        catch (Exception e){
            containers = new ArrayList<Container>();
            e.printStackTrace();
        }
        float delta = 0.0001f;

        Container container = containers.get(0);
        assertEquals(13, container.getArrivalDay());
        assertEquals(12, container.getArrivalMonth());
        assertEquals(4, container.getArrivalYear());
        assertEquals("0.00", container.getArrivalSpanStart());
        assertEquals("0.10", container.getArrivalSpanEnd());
        assertEquals("vrachtauto", container.getArrivalTransportType());
        assertEquals("DijckLogisticsBV", container.getArrivalCompany());
        assertEquals(1, container.getSpawnX());
        assertEquals(0, container.getSpawnY());
        assertEquals(0, container.getSpawnZ());
        assertEquals("FlowersNL", container.getOwner());
        assertEquals(19965, container.getNumber());
        assertEquals(22, container.getDepartureDay());
        assertEquals(12, container.getDepartureMonth());
        assertEquals(4, container.getDepartureYear());
        assertEquals("0.00", container.getDepartureSpanStart());
        assertEquals("12.00", container.getDepartureSpanEnd());
        assertEquals("zeeschip", container.getDepartureTransportType());
        assertEquals("ChinaShippingAgency", container.getDepartureCompany());
        assertEquals(12.192f, container.getLength(), delta);
        assertEquals(2.4384f, container.getWidth(), delta);
        assertEquals(2.5908f, container.getHeight(), delta);
        assertEquals(81, container.getWeight(), delta);
        assertEquals("nitrotolueen", container.getContentsName());
        assertEquals("gas", container.getContentsType());
        assertEquals("brandbaar", container.getContentsDanger());
        assertEquals("1496-1", container.getIso());
    }

    @After
    public void tearDown() throws Exception {
        // Code executed after each test
    }
}
