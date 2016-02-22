package de.mhaug.scsproject.view;

import static org.junit.Assert.assertTrue;

import de.mhaug.scsproject.AbstractTest;

import org.junit.Before;
import org.junit.Test;

public class BlockDiagrammDefinitionResourceTest extends AbstractTest {

	private BlockDiagrammDefinitionResource instance;

	@Before
	public void setUp() throws Exception {
		this.instance = getInjector().getInstance(BlockDiagrammDefinitionResource.class);
	}

	@Test
	public void testGetHtml() {
		String actualHtml = instance.getHtml();
		assertTrue(actualHtml.contains("<html>"));
	}

}
