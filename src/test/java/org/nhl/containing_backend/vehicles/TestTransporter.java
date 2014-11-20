package org.nhl.containing_backend.vehicles;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.exceptions.FullStackException;

import java.awt.*;
import java.util.EmptyStackException;

import static org.junit.Assert.*;

/**
 * Basic empty test template for unit tests.
 */
public class TestTransporter {
    Transporter transporter;

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
        // Two rows, three columns, two "height".
        // For future unit testing, perhaps try more variables?
        transporter = new Transporter(2, 3, 2);
    }

    @Test
    public void testPutContainer() {
        Container container = new Container();
        transporter.putContainer(new Point(0, 0), container);
        // Find a way to ascertain that there is indeed a container at (0, 0).
    }

    @Test
    public void testPutContainerOverflow() {
        Container container = new Container();
        transporter.putContainer(new Point(0, 0), container);
        transporter.putContainer(new Point(0, 0), container);
        thrown.expect(FullStackException.class);
        transporter.putContainer(new Point(0, 0), container);
    }

    @Test
    public void testTakeContainer() {
        Container container = new Container();
        // First add a bottom layer container, to make sure that the container is taken from the top.
        transporter.putContainer(new Point(0, 0), new Container());
        transporter.putContainer(new Point(0, 0), container);
        Container result = transporter.takeContainer(new Point(0, 0));
        assertEquals(container, result);
    }

    @Test
    public void testTakeContainerUnderflow() {
        thrown.expect(EmptyStackException.class);
        transporter.takeContainer(new Point(0, 0));
    }

    @Test
    public void testHeightAt() {
        assertEquals(0, transporter.heightAt(new Point(0, 0)));
        transporter.putContainer(new Point(0, 0), new Container());
        assertEquals(1, transporter.heightAt(new Point(0, 0)));
    }

    @After
    public void tearDown() throws Exception {
        // Code executed after each test
    }
}
