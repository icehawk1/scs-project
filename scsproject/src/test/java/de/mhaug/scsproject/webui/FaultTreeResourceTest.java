package de.mhaug.scsproject.webui;

import static org.junit.Assert.assertEquals;

import de.mhaug.scsproject.AbstractTest;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class FaultTreeResourceTest extends AbstractTest {
	private FaultTreeResource instance;

	@Before
	public void setUp() throws Exception {
		instance = getInjector().getInstance(FaultTreeResource.class);
	}

	@Test
	public void test() throws SQLException, CycleFoundException {
		String jsonActual = instance.sendGetJson("42");
		jsonActual = StringUtils.removeStart(jsonActual, "[");
		jsonActual = StringUtils.removeEnd(jsonActual, "]");

		FaultTreeEntry actual = new Gson().fromJson(jsonActual, FaultTreeEntry.class);
		assertEquals(3, actual.children.size());
		assertEquals("Event 2.1", actual.children.get(1).children.get(0).vertex);
	}
}
