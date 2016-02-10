package de.mhaug.scsproject.webui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.mhaug.scsproject.AbstractTest;
import de.mhaug.scsproject.model.FaultListEntry;
import de.mhaug.scsproject.view.FaultListResource;

import java.sql.SQLException;
import java.util.List;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FaultListResourceTest extends AbstractTest {
	private FaultListResource instance;
	private Gson gson;

	@Before
	public void setUp() throws Exception {
		instance = getInjector().getInstance(FaultListResource.class);
		gson = getInjector().getInstance(Gson.class);
	}

	@Test
	public void testSendGetJson() throws SQLException, CycleFoundException {
		String actualJson = instance.sendGetJson("42");
		List<FaultListEntry> actualList = gson.fromJson(actualJson, new TypeToken<List<FaultListEntry>>() {
		}.getType());

		assertEquals(6, actualList.size());
		assertTrue(actualList.contains(new FaultListEntry(2, "Event 1", null, "")));
	}
}
