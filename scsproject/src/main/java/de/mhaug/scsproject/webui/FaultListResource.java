package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.Main;
import de.mhaug.scsproject.model.FaultTree;
import de.mhaug.scsproject.model.FaultTreeJoiner;
import de.mhaug.scsproject.model.JoinerEdge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;

import com.google.inject.Inject;

/**
 * Displays the table where the user edits the fault tree and processes his
 * edits.
 * 
 * @author Martin Haug
 */
@Path("/FaultList")
public class FaultListResource extends JerseyResource {
	private FaultTree faulttree;
	private Connection con;

	@Inject
	public FaultListResource(FaultTree faulttree, Connection con) {
		this.faulttree = faulttree;
		this.con = con;
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void receivePostForm(@QueryParam("treeid") String treeid, @FormParam("name") String name,
			@FormParam("joiner") String joiner, @FormParam("children") String children,
			@FormParam("rowid") String rowid, @FormParam("comment") String comment,
			@FormParam("webix_operation") String op) throws SQLException {

		System.out.println("FaultList save: " + name + ", " + op + ", " + rowid);

		Connection con = Main.getInjector().getInstance(Connection.class);
		if (op.equals("update")) {
			PreparedStatement stmt = con.prepareStatement(
					"UPDATE FaultList SET name=?, joiner=?, children=?, comment=? WHERE rowid=? AND treeid=?");
			stmt.setString(1, name);
			stmt.setString(2, joiner);
			stmt.setString(3, children);
			stmt.setString(4, comment);
			stmt.setLong(5, Long.parseLong(rowid));
			stmt.setLong(6, Long.parseLong(treeid));
			stmt.execute();
		}
	}

	@Path("AddRow")
	@POST
	public String acceptPostAddRow(@QueryParam("treeid") String treeid) throws SQLException {
		System.out.println("add row: " + treeid);
		try {
			PreparedStatement stmt = con.prepareStatement("INSERT INTO FaultList (treeid,name) VALUES (?,?)");
			stmt.setString(1, treeid);
			stmt.setString(2, "");
			stmt.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "row added";
	}

	@Path("json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sendGetJson(@QueryParam("treeid") String treeid) throws SQLException, CycleFoundException {
		DirectedAcyclicGraph<String, JoinerEdge> graph = faulttree.getFaultTreeForID(Integer.parseInt(treeid));

		String result = "[";
		Iterator<String> it = graph.iterator();
		System.out.println(it.hasNext());
		while (it.hasNext()) {
			try {
				String vertex = it.next();
				int rowid = getRowidForName(vertex);

				String children = "";
				for (JoinerEdge edge : graph.outgoingEdgesOf(vertex)) {
					children += edge.getTarget() + ", ";
				}
				children = StringUtils.removeEnd(children.trim(), ",");

				result += "{\"rowid\":\"" + rowid + "\", \"name\":\"" + vertex + "\", ";
				Iterator<JoinerEdge> edges = graph.edgesOf(vertex).iterator();
				if (edges.hasNext()) {
					FaultTreeJoiner joiner = edges.next().getJoiner();
					result += "\"joiner\":\"" + joiner + "\", ";
				}
				result += "\"children\":\"" + children + "\"},";
				result += "\n";
			} catch (Exception ex) {
				System.err.println(ex.getMessage());
			}
		}

		result = StringUtils.removeEnd(result.trim(), ",") + "]";
		return result;
	}

	private int getRowidForName(String name) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("SELECT rowid FROM FaultList WHERE name=?");
		System.out.println("name: " + name);
		stmt.setString(1, name);

		ResultSet rs = stmt.executeQuery();
		if (rs.isClosed())
			System.err
					.println("An event named '" + name + "' does not exist. Maybe you misstyped when adding children?");
		int result = rs.getInt("rowid");
		return result;
	}
}
