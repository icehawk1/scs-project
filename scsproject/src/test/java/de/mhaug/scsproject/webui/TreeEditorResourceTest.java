package de.mhaug.scsproject.webui;

import static org.junit.Assert.assertTrue;

import de.mhaug.scsproject.AbstractTest;
import de.mhaug.scsproject.view.TreeEditorResource;

import org.junit.Before;
import org.junit.Test;

public class TreeEditorResourceTest extends AbstractTest {

	private TreeEditorResource instance;

	@Before
	public void setUp() throws Exception {
		instance = getInjector().getInstance(TreeEditorResource.class);
	}

	@Test
	public void testSendGetHtml() {
		String actual = instance.sendGetHtml("42");
		assertTrue(actual.contains("treeid=42"));
		assertTrue(actual.toUpperCase().contains("<!DOCTYPE HTML>"));
	}
}
