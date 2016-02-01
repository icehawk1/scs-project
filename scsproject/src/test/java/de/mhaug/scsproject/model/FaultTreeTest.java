package de.mhaug.scsproject.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;
import org.junit.Test;

public class FaultTreeTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws SQLException, CycleFoundException {
		Connection con = DriverManager.getConnection("jdbc:sqlite:src/test/resources/test.db");
		PreparedStatement stmt = con
				.prepareStatement("SELECT name,joiner,children,comment FROM FaultList WHERE treeid = 42");
		ResultSet rs = stmt.executeQuery();

		DirectedAcyclicGraph<String, JoinerEdge> actual = FaultTree.createFaultTreeFromTable(rs);

		assertEquals(5, actual.edgeSet().size());
		assertEquals(6, actual.vertexSet().size());
		assertTrue(actual.containsEdge("Event 2", "Event 2.1"));
		assertEquals(FaultTreeJoiner.AND, actual.getEdge("Bad stuff", "Event 3").getJoiner());
		assertEquals(FaultTreeJoiner.OR, actual.getEdge("Event 2", "Event 2.2").getJoiner());
	}

}
