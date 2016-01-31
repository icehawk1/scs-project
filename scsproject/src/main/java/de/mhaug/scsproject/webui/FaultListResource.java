package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.Main;
import de.mhaug.scsproject.model.FaultTree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;

@Path("/FaultList")
public class FaultListResource extends JerseyResource {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtml() throws SQLException, CycleFoundException {
		Connection con = Main.getInjector().getInstance(Connection.class);
		PreparedStatement stmt = con.prepareStatement("SELECT name,joiner,children FROM FaultList WHERE treeid = ?");
		stmt.setInt(1, 42);
		ResultSet rs = stmt.executeQuery();

		Context context = Main.getInjector().getInstance(VelocityContext.class);
		context.put("faultlist", FaultTree.createFaultTreeFromTable(rs));

		return mergeVelocityTemplate("FaultList.html", context);
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void receivePostForm(@FormParam("name") String name, @FormParam("joiner") String joiner,
			@FormParam("children") String children, @FormParam("id") String id, @FormParam("comment") String comment,
			@FormParam("webix_operation") String op) throws SQLException {
		System.out.println("FaultList Received: " + name + " " + joiner + " " + children + " " + id + " " + op);

		Connection con = Main.getInjector().getInstance(Connection.class);
		PreparedStatement stmt = con
				.prepareStatement("insert or replace into FaultList(rowid, treeid, name, joiner, children, comment) "
						+ "values (?, ?, ?, ?, ?, ?)");
		stmt.setLong(1, Long.parseLong(id));
		stmt.setLong(2, 42);
		stmt.setString(3, name);
		stmt.setString(4, joiner);
		stmt.setString(5, children);
		stmt.setString(6, comment);
		stmt.execute();
	}
}
