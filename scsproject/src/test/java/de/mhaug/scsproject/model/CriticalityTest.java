package de.mhaug.scsproject.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CriticalityTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOrdering() {
		assertTrue(Criticality.Low.compareTo(Criticality.VeryLow) < 0);
	}

	@Test
	public void testGetNumber() {
		int expected = 8;
		assertEquals(expected, Criticality.VeryHigh.getNumber());
	}
}
