package org.nhl.containing_backend.models;

import java.util.Calendar;
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
    public void testFinaliseLorryCoordinates() {
        Container con = new Container();
        con.setSpawnX(1);
        con.setSpawnY(0);
        con.setSpawnZ(0);
        con.setArrivalTransportType("vrachtauto");

        con.finalise();

        assertEquals(0, con.getSpawnX());
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

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2004);
        cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date expected = cal.getTime();
        assertEquals(expected, con.getArrivalDate());
    }

    @Test
    public void testGetDepartureDate() {
        Container con = new Container();
        con.setDepartureDay(1);
        con.setDepartureMonth(9);
        con.setDepartureYear(4);
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2004);
        cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date expected = cal.getTime();
        assertEquals(expected, con.getDepartureDate());
    }

    @After
    public void tearDown() throws Exception {
        // Code executed after each test
    }
}
