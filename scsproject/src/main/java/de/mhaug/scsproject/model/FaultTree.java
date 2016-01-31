package de.mhaug.scsproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jgrapht.EdgeFactory;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;

public class FaultTree {
	/**
	 * Gets a specific fault tree
	 * 
	 * TODO: Derzeit nur ein stub
	 * 
	 * @param id
	 *            The unique id of the fault tree
	 */
	public static FaultTreeEvent getFaultTreeForID(int id) {
		return null;
	}

	public static DirectedAcyclicGraph<String, JoinerEdge> createFaultTreeFromTable(ResultSet rs)
			throws SQLException, CycleFoundException {
		DirectedAcyclicGraph<String, JoinerEdge> graph = new DirectedAcyclicGraph<>(new JoinerEdgeFactory());

		// SELECT name,joiner,children FROM FaultList WHERE treeid = ?
		while (rs.next()) {
			String name = rs.getString("name").trim();
			String[] children = rs.getString("children").split(",");
			String joiner = rs.getString("joiner").trim().toUpperCase();

			graph.addVertex(name);
			for (String child : children) {
				child = child.trim();
				if (child.isEmpty()) {
					continue;
				}

				if (!graph.containsVertex(child)) {
					graph.addVertex(child);
				}
				graph.addDagEdge(name, child);
			}
		}

		return graph;
	}
}

class JoinerEdgeFactory implements EdgeFactory<String, JoinerEdge> {

	@Override
	public JoinerEdge createEdge(String sourceVertex, String targetVertex) {
		return new JoinerEdge(FaultTreeJoiner.AND);
	}
}

class JoinerEdge {
	FaultTreeJoiner joiner;

	public JoinerEdge(FaultTreeJoiner joiner) {
		this.joiner = joiner;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}
}

class FaultTreeEvent {

	private final String comment;
	private final int rowid;
	private final String name;

	public FaultTreeEvent(int rowid, String name, String comment) {
		this.rowid = rowid;
		this.name = name;
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public int getRowid() {
		return rowid;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "FaultTreeEvent [comment=" + comment + ", rowid=" + rowid + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FaultTreeEvent other = (FaultTreeEvent) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}

enum FaultTreeJoiner {
	NONE, AND, OR;
}
