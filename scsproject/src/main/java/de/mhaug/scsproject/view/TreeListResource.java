package de.mhaug.scsproject.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.inject.Inject;

@Path("/")
public class TreeListResource extends VelocityResource {
	private final Connection con;
	private final Gson gson;

	@Inject
	public TreeListResource(Connection con, Gson gson) {
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
	public String sendGetHtml() {
		String result = mergeVelocityTemplate("TreeList.html", context);
		return result;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sendGetJson() throws SQLException, CycleFoundException {
		PreparedStatement stmt = con.prepareStatement("Select rowid,name from FaultTree");
		ResultSet rs = stmt.executeQuery();

		Liste treelist = new Liste();
		while (rs.next()) {
			treelist.liste.add(new ListEntry(rs.getInt("rowid"), rs.getString("name")));
		}

		String result = gson.toJson(treelist.liste);
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
