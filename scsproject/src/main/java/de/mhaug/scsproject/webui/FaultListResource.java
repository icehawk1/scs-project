package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.Main;
import de.mhaug.scsproject.model.FaultTree;
import de.mhaug.scsproject.model.FaultTreeJoiner;
import de.mhaug.scsproject.model.JoinerEdge;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
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

	@Inject
	public FaultListResource(FaultTree faulttree) {
		this.faulttree = faulttree;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtml(@QueryParam("treeid") String treeid) throws SQLException, CycleFoundException {
		Context context = Main.getInjector().getInstance(VelocityContext.class);
		context.put("faultlist", faulttree.getFaultTreeForID(Integer.parseInt(treeid)));

		return mergeVelocityTemplate("FaultList.html", context);
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void receivePostForm(@FormParam("name") String name, @FormParam("joiner") String joiner,
			@FormParam("children") String children, @FormParam("id") String id, @FormParam("comment") String comment,
			@FormParam("webix_operation") String op) throws SQLException {

		System.out.println("FaultList save: " + op + ", " + id);

		Connection con = Main.getInjector().getInstance(Connection.class);
		if (op.equals("update")) {
			PreparedStatement stmt = con
					.prepareStatement("UPDATE FaultList SET name=?, joiner=?, children=?, comment=? WHERE rowid=?");
			stmt.setString(1, name);
			stmt.setString(2, joiner);
			stmt.setString(3, children);
			stmt.setString(4, comment);
			stmt.setLong(5, Long.parseLong(id));
			stmt.execute();
		}
	}

	@Path("json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sendGetJson(@QueryParam("treeid") String treeid) throws SQLException, CycleFoundException {
		System.out.println("Fault list json");
		DirectedAcyclicGraph<String, JoinerEdge> graph = faulttree.getFaultTreeForID(Integer.parseInt(treeid));

		Iterator<String> it = graph.iterator();
		String result = "[";
		while (it.hasNext()) {
			String vertex = it.next();

			String children = "";
			for (JoinerEdge edge : graph.outgoingEdgesOf(vertex)) {
				children += edge.getTarget() + ", ";
			}
			children = StringUtils.removeEnd(children.trim(), ",");

			FaultTreeJoiner joiner = graph.edgesOf(vertex).iterator().next().getJoiner();
			result += "{ \"name\":\"" + vertex + "\", \"joiner\":\"" + joiner + "\", \"children\":\"" + children
					+ "\"},";
			result += "\n";
		}

		result = StringUtils.removeEnd(result.trim(), ",") + "]";
		return result;
	}
}
