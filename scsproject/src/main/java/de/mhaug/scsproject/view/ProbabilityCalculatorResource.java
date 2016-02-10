package de.mhaug.scsproject.view;

import de.mhaug.scsproject.model.FaultListEntry;
import de.mhaug.scsproject.model.FaultTreeJoiner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.inject.Inject;

@Path("/ProbabilityCalculator")
public class ProbabilityCalculatorResource extends VelocityResource {
	private Gson gson;
	private Connection con;

	@Inject
	public ProbabilityCalculatorResource(Gson gson, Connection con) {
		this.gson = gson;
		this.con = con;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void receivePostJson(@QueryParam("probabilities") String probs) {
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtml(@QueryParam("treeid") String treeid) {
		context.put("treeid", treeid);
		return mergeVelocityTemplate("ProbabilityCalculator.html", context);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sendGetJson(@QueryParam("treeid") String treeid) {
		List<FaultListEntry> leaves = getLeaves(treeid);
		return gson.toJson(leaves);
	}

	private List<FaultListEntry> getLeaves(String treeid) {
		try {
			PreparedStatement stmt = con.prepareStatement(
					"SELECT rowid,name,joiner,children FROM FaultList WHERE children='' AND treeid=?");
			stmt.setLong(1, Long.parseLong(treeid));
			ResultSet rs = stmt.executeQuery();

			List<FaultListEntry> result = new ArrayList<>();
			while (rs.next()) {
				int rowid = rs.getInt("rowid");
				String name = rs.getString("name");
				String joinerStr = rs.getString("joiner");
				FaultTreeJoiner joiner = joinerStr.trim().isEmpty() ? FaultTreeJoiner.NONE
						: FaultTreeJoiner.valueOf(joinerStr);
				String children = rs.getString("children");

				assert children.trim().isEmpty();
				result.add(new FaultListEntry(rowid, name, joiner, children));
			}
			return result;
		} catch (NumberFormatException | SQLException ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}
}
