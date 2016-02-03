package de.mhaug.scsproject.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.mhaug.scsproject.AbstractTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;
import org.junit.Before;
import org.junit.Test;

public class FaultTreeTest extends AbstractTest {
	private DirectedAcyclicGraph<String, JoinerEdge> graph;
	private Object faulttree;

	@Before
	public void setUp() throws SQLException, CycleFoundException {
		Connection con = getInjector().getInstance(Connection.class);
		PreparedStatement stmt = con
				.prepareStatement("SELECT name,joiner,children,comment FROM FaultList WHERE treeid = 42");
		ResultSet rs = stmt.executeQuery();

		graph = getInjector().getInstance(FaultTree.class).createFaultTreeFromTable(rs);
	}

	/**
	 * Tests if the Tree containes the correct Vertices and Edges.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testTreeContent() throws SQLException, CycleFoundException {
		assertEquals(5, graph.edgeSet().size());
		assertEquals(6, graph.vertexSet().size());
		assertTrue(graph.containsEdge("Event 2", "Event 2.1"));
		assertEquals(FaultTreeJoiner.AND, graph.getEdge("Bad stuff", "Event 3").getJoiner());
		assertEquals(FaultTreeJoiner.OR, graph.getEdge("Event 2", "Event 2.2").getJoiner());
	}

	/**
	 * Tests if the Vertices are returned in the correct order.
	 */
	@Test
	public void testVertexOrdering() {
		List<String> expected = new ArrayList<>();
		expected.add("Bad stuff");
		expected.add("Event 1");
		expected.add("Event 2");
		expected.add("Event 3");
		expected.add("Event 2.1");
		expected.add("Event 2.2");

		List<String> actual = new ArrayList<>(graph.vertexSet().size());
		Iterator<String> it = graph.iterator();
		while (it.hasNext()) {
			String vertex = it.next();
			actual.add(vertex);
		}

		assertEquals(expected, actual);
	}
}
