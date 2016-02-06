package de.mhaug.scsproject.webui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.VelocityContext;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.inject.Inject;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class TreeListResource extends AbstractResource {
	private VelocityContext context;
	private Connection con;
	private Gson gson;

	@Inject
	public TreeListResource(VelocityContext context, Connection con, Gson gson) {
		this.context = context;
		this.con = con;
		this.gson = gson;
	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtml(@QueryParam("treeid") String treeid) {
		context.put("treeid", treeid);
		String result = mergeVelocityTemplate("TreeList.html", context);
		return result;
	}

	// @Path("/Treelist/json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sendGetJson() throws SQLException, CycleFoundException {
		System.out.println("TreeList/json");
		PreparedStatement stmt = con.prepareStatement("Select rowid,name from FaultTree");
		ResultSet rs = stmt.executeQuery();

		Liste treelist = new Liste();
		while (rs.next()) {
			treelist.liste.add(new ListEntry(rs.getInt("rowid"), rs.getString("name")));
		}

		String result = gson.toJson(treelist.liste);
		System.out.println(result);
		return result;

	}

	private static class Liste {
		@SerializedName("liste")
		private ArrayList<ListEntry> liste;

		public Liste() {
			liste = new ArrayList<>();
		}
	}

	private static class ListEntry {
		@SerializedName("rowid")
		private int treeid;
		@SerializedName("name")
		private String name;
		@SerializedName("link")
		private String link;

		public ListEntry(int treeid, String name) {
			this.treeid = treeid;
			this.name = name;
			this.link = "<a href=\"/TreeEditor?treeid=42\" target=\"_blank\">Edit</a>";
		}
	}
}
