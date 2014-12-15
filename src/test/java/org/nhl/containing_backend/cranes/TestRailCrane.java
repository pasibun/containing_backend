package org.nhl.containing_backend.cranes;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.nhl.containing_backend.models.Container;
import org.nhl.containing_backend.exceptions.FullStackException;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

/**
 * Basic empty test template for unit tests.
 */
public class TestRailCrane {
    /**private StorageCrane railCrane;

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
        railCrane = new StorageCrane();
    }

    @Test
    public void testAttachContainer() {
        Container container = new Container();
        railCrane.attachContainer(container);
        assertEquals(container, railCrane.getContainer());
    }

    @Test
    public void testAttachContainerOverflow() {
        Container container = new Container();
        railCrane.attachContainer(container);
        thrown.expect(FullStackException.class);
        railCrane.attachContainer(container);
    }

    @Test
    public void testDetachContainer() {
        Container container = new Container();
        railCrane.attachContainer(container);
        Container result = railCrane.detachContainer();
        assertEquals(container, result);
    }

    @Test
    public void testDetachContainerUnderFlow() {
        thrown.expect(EmptyStackException.class);
        railCrane.detachContainer();
    }

    @After
    public void tearDown() throws Exception {
        // Code executed after each test
    }
    */
    
}
