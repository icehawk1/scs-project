package de.mhaug.scsproject.view;

import static org.junit.Assert.assertEquals;

import de.mhaug.scsproject.AbstractTest;

import org.junit.Before;
import org.junit.Test;

public class VelocityResourceTest extends AbstractTest {

	private VelocityResource instance;

	@Before
	public void setUp() throws Exception {
		this.instance = getInjector().getInstance(VelocityResource.class);
	}

	@Test
	public void testGetUrlOfResource() {
		String expected = "/BlockDiagramDefinition";
		String actual = instance.getUrlOfResource(BlockDiagrammDefinitionResource.class, "dataLoadUrl");
		assertEquals(expected, actual);
	}
}
