package de.mhaug.scsproject.view;

import static org.junit.Assert.assertTrue;

import de.mhaug.scsproject.AbstractTest;
import de.mhaug.scsproject.view.TreeListResource;

import org.junit.Before;
import org.junit.Test;

public class TreeListResourceTest extends AbstractTest {

	private TreeListResource instance;

	@Before
	public void setUp() throws Exception {
		instance = getInjector().getInstance(TreeListResource.class);
	}

	@Test
	public void testSendGetHtml() {
		String actual = instance.sendGetHtml();
		assertTrue(actual.contains("http-equiv=\"Content-Type\""));
	}
}
