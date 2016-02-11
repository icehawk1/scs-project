package de.mhaug.scsproject.view;

import static org.junit.Assert.assertEquals;

import de.mhaug.scsproject.AbstractTest;

import org.junit.Before;
import org.junit.Test;

public class VelocityResourceTest extends AbstractTest {

	private VelocityResource instance;

	@Before
	public void setUp() throws Exception {
		instance = getInjector().getInstance(VelocityResource.class);
	}

	@Test
	public void testGetUrlOfResource_ProbabilityCalculator() {
		String expected = "/ProbabilityCalculator";
		String actual = instance.getUrlOfResource(ProbabilityCalculatorResource.class);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetUrlOfResource_TreeList() {
		String expected = "/";
		String actual = instance.getUrlOfResource(TreeListResource.class);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetUrlOfResource_addEventUrl() {
		String expected = "/FaultList/AddEvent";
		String actual = instance.getUrlOfResource(FaultListResource.class, "acceptPostAddEvent");
		assertEquals(expected, actual);
	}
}
