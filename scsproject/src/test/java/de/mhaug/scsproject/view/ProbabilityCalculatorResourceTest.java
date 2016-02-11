package de.mhaug.scsproject.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import de.mhaug.scsproject.AbstractTest;
import de.mhaug.scsproject.model.FaultListEntry;
import de.mhaug.scsproject.model.FaultTreeJoiner;
import de.mhaug.scsproject.view.ProbabilityCalculatorResource;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ProbabilityCalculatorResourceTest extends AbstractTest {

	private ProbabilityCalculatorResource instance;
	private Gson gson;

	@Before
	public void setUp() throws Exception {
		instance = getInjector().getInstance(ProbabilityCalculatorResource.class);
		gson = getInjector().getInstance(Gson.class);
	}

	@Test
	public void testSendGetHtml() {
		String actual = instance.sendGetHtml("42");
		assertTrue(actual.contains("treeid=42"));
		assertTrue(actual.trim().startsWith("<!DOCTYPE HTML>"));
	}

	@Test
	public void testSendGetJson() {
		List<FaultListEntry> actual = gson.fromJson(instance.sendGetJson("42"), new TypeToken<List<FaultListEntry>>() {
		}.getType());

		assertEquals(4, actual.size());

		assertTrue(actual.contains(new FaultListEntry(2, "Event 1", FaultTreeJoiner.NONE, "", "bbb")));
		assertTrue(actual.contains(new FaultListEntry(6, "Event 2.2", FaultTreeJoiner.NONE, "", "fff")));

		assertFalse(
				actual.contains(new FaultListEntry(1, "Event 2", FaultTreeJoiner.OR, "Event 2.1, Event 2.2", "ccc")));
	}
}
