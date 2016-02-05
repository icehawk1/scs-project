package de.mhaug.scsproject.webui;

import static org.junit.Assert.assertEquals;

import de.mhaug.scsproject.AbstractTest;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.junit.Before;
import org.junit.Test;

public class JerseyResourceTest extends AbstractTest {
	private JerseyResource instance;

	@Before
	public void setUp() throws Exception {
		instance = new RootResource();
	}

	@Test
	public void testTemplateProcessing() {
		String expected = "|\"/FaultTree/json?treeid=42\"|";
		Context context = getInjector().getInstance(VelocityContext.class);
		context.put("treeid", 42);
		String actual = instance.mergeVelocityTemplate("Test.html", context);

		assertEquals(expected, actual.trim());
	}
}
