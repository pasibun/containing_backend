package org.nhl.containing_backend.models;

import java.util.Date;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.nhl.containing_backend.models.Container;

import static org.junit.Assert.*;

public class TestContainer {

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
    public void testCalculateLength() {
        float delta = 0.0001f;
        assertEquals(0.0254f, Container.calculateLength("0'1\""), delta);
        assertEquals(0.3048f, Container.calculateLength("1'"), delta);
        assertEquals(1.7526f, Container.calculateLength("5'9\""), delta);
    }

    @Test
    public void testGetArrivalDate() {
        Container con = new Container();
        con.setArrivalDay(1);
        con.setArrivalMonth(9);
        con.setArrivalYear(4);
        Date expected = new Date(2004, 9, 1);
        assertEquals(expected, con.getArrivalDate());
    }

    @Test
    public void testGetDepartureDate() {
        Container con = new Container();
        con.setDepartureDay(1);
        con.setDepartureMonth(9);
        con.setDepartureYear(4);
        Date expected = new Date(2004, 9, 1);
        assertEquals(expected, con.getDepartureDate());
    }

    @After
    public void tearDown() throws Exception {
        // Code executed after each test
    }
}
