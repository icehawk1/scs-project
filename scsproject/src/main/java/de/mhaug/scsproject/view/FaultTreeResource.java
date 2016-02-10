package de.mhaug.scsproject.view;

import de.mhaug.scsproject.model.DepthFirstSearch;
import de.mhaug.scsproject.model.FaultTree;
import de.mhaug.scsproject.model.FaultTreeEntry;
import de.mhaug.scsproject.model.JoinerEdge;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;

import com.google.gson.Gson;
import com.google.inject.Inject;

@Path("/FaultTree")
public class FaultTreeResource {
	private final FaultTree faulttree;
	private final DepthFirstSearch dfs;

	@Inject
	public FaultTreeResource(FaultTree faulttree, DepthFirstSearch dfs) {
		this.faulttree = faulttree;
		this.dfs = dfs;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sendGetJson(@QueryParam("treeid") String treeid) throws SQLException, CycleFoundException {
		DirectedAcyclicGraph<String, JoinerEdge> graph = faulttree.getFaultTreeForID(Integer.parseInt(treeid));
		FaultTreeEntry root = dfs.dfs(graph);

		List<FaultTreeEntry> temp = new ArrayList<>();
		temp.add(root);
		String result = new Gson().toJson(temp);
		return result;
	}
}
