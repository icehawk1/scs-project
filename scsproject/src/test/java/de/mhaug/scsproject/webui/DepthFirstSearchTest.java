package de.mhaug.scsproject.webui;

import static org.junit.Assert.assertEquals;

import de.mhaug.scsproject.AbstractTest;
import de.mhaug.scsproject.model.FaultTree;

import java.sql.SQLException;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class DepthFirstSearchTest extends AbstractTest {

	private DepthFirstSearch instance;
	private FaultTree faulttree;

	@Before
	public void setUp() throws Exception {
		instance = getInjector().getInstance(DepthFirstSearch.class);
		faulttree = getInjector().getInstance(FaultTree.class);
	}

	@Test
	public void test() throws SQLException, CycleFoundException {
		TreeEntry actual = new Gson().fromJson(instance.dfs(faulttree.getFaultTreeForID(42)), TreeEntry.class);
		assertEquals(3, actual.children.size());
		assertEquals("Event 2.1", actual.children.get(1).children.get(0).vertex);
	}
}
