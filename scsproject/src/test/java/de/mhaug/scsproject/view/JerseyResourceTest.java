package de.mhaug.scsproject.view;

import static org.junit.Assert.assertEquals;

import de.mhaug.scsproject.AbstractTest;
import de.mhaug.scsproject.view.VelocityResource;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.junit.Before;
import org.junit.Test;

public class JerseyResourceTest extends AbstractTest {
	private VelocityResource instance;

	@Before
	public void setUp() throws Exception {
		instance = getInjector().getInstance(VelocityResource.class);
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
