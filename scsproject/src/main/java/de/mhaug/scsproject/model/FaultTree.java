package de.mhaug.scsproject.model;

import de.mhaug.scsproject.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;
import org.jgrapht.graph.DefaultEdge;

/**
 * Represents a fault tree as displayed in the Web-UI. A fault tree has an
 * undesired event as its root and this event then has its causes as children.
 * The causes are joined by either OR (at least one of them are needed) or AND
 * (all of them are needed.). Causes are events that need to happen for the root
 * to happen.
 * 
 * @author Martin Haug
 */
public class FaultTree {
	/**
	 * Gets a specific fault tree. Throws an exception when the contents of the
	 * database are invalid.
	 * 
	 * @param treeid
	 *            The unique id of the fault tree
	 */
	public static DirectedAcyclicGraph<String, JoinerEdge> getFaultTreeForID(int treeid)
			throws SQLException, CycleFoundException {
		Connection con = Main.getInjector().getInstance(Connection.class);
		PreparedStatement stmt = con.prepareStatement("SELECT name,joiner,children FROM FaultList WHERE treeid = ?");
		stmt.setInt(1, treeid);
		ResultSet rs = stmt.executeQuery();

		return createFaultTreeFromTable(rs);
	}

	/**
	 * Creates a FaultTree from the table rows given
	 */
	static DirectedAcyclicGraph<String, JoinerEdge> createFaultTreeFromTable(ResultSet rs)
			throws SQLException, CycleFoundException {
		DirectedAcyclicGraph<String, JoinerEdge> graph = new DirectedAcyclicGraph<>(JoinerEdge.class);

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
				graph.addDagEdge(name, child, new JoinerEdge(name, child, FaultTreeJoiner.valueOf(joiner)));
			}
		}

		return graph;
	}
}

/**
 * An Edge in the FaultTree. Can hold a FaultTreeJoiner.
 * 
 * @author Martin Haug
 */
class JoinerEdge extends DefaultEdge {
	private static final long serialVersionUID = 4941354549324940119L;
	public final FaultTreeJoiner joiner;
	private final String from;
	private String to;

	public JoinerEdge(String source, String target, FaultTreeJoiner joiner) {
		this.from = source;
		this.to = target;
		this.joiner = joiner;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	protected Object getSource() {
		return from;
	}

	@Override
	protected Object getTarget() {
		return to;
	}

	public FaultTreeJoiner getJoiner() {
		return joiner;
	}
}

/**
 * The boolean logic gates, that combine entries in the FaultTree.
 * 
 * @author Martin Haug
 */
enum FaultTreeJoiner {
	NONE, AND, OR;
}
