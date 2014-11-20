package org.nhl.containing_backend.models;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.nhl.containing_backend.Container;
import org.nhl.containing_backend.exceptions.FullStackException;

import java.awt.*;
import java.util.EmptyStackException;

import static org.junit.Assert.*;

/**
 * Basic empty test template for unit tests.
 */
public class TestStorage {
    Storage storage;

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
       storage = new Storage(2, 3);
    }

    @Test
    public void testPutContainer() {
        Container container = new Container();
        storage.putContainer(new Point(0, 0), container);
        // Find a way to ascertain that there is indeed a container at (0, 0).
    }

    @Test
    public void testPutContainerOverflow() {
        Container container = new Container();
        storage.putContainer(new Point(0, 0), container);
        thrown.expect(FullStackException.class);
        storage.putContainer(new Point(0, 0), container);
    }

    @Test
    public void testTakeContainer() {
        Container container = new Container();
        storage.putContainer(new Point(0, 0), container);
        Container result = storage.takeContainer(new Point(0, 0));
        assertEquals(container, result);
    }

    @Test
    public void testTakeContainerUnderflow() {
        thrown.expect(EmptyStackException.class);
        storage.takeContainer(new Point(0, 0));
    }

    @After
    public void tearDown() throws Exception {
        // Code executed after each test
    }
}
