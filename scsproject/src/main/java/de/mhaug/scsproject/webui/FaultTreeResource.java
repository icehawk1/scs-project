package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.model.FaultTree;
import de.mhaug.scsproject.model.JoinerEdge;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;

import com.google.inject.Inject;

@Path("/FaultTree")
public class FaultTreeResource extends JerseyResource {
	private FaultTree faulttree;
	private DepthFirstSearch dfs;

	@Inject
	public FaultTreeResource(FaultTree faulttree, DepthFirstSearch dfs) {
		this.faulttree = faulttree;
		this.dfs = dfs;
	}

	@Path("json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sendGetJson(@QueryParam("treeid") String treeid) throws SQLException, CycleFoundException {
		DirectedAcyclicGraph<String, JoinerEdge> graph = faulttree.getFaultTreeForID(Integer.parseInt(treeid));
		String result = dfs.dfs(graph);
		return result;
	}
}
