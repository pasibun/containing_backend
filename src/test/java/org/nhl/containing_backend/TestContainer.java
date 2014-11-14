package org.nhl.containing_backend;

import org.junit.*;
import org.junit.rules.ExpectedException;

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
        float delta = 0.01f;
        assertEquals(0.0254f, Container.calculateLength("0'1\""), delta);
        assertEquals(0.3048f, Container.calculateLength("1'"), delta);
        assertEquals(1.7526f, Container.calculateLength("5'9\""), delta);
    }

    @After
    public void tearDown() throws Exception {
        // Code executed after each test
    }
}
