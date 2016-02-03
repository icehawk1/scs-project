package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.Main;
import de.mhaug.scsproject.model.FaultTree;
import de.mhaug.scsproject.model.JoinerEdge;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;

import com.google.inject.Inject;

@Path("/FaultTree")
public class FaultTreeResource extends JerseyResource {
	private FaultTree faulttree;

	@Inject
	public FaultTreeResource(FaultTree faulttree) {
		this.faulttree = faulttree;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtml() {
		System.out.println("Fault tree html");

		Context context = Main.getInjector().getInstance(VelocityContext.class);
		return mergeVelocityTemplate("FaultTree.html", context);
	}

	@Path("json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sendGetJson(@QueryParam("treeid") String treeid) throws SQLException, CycleFoundException {
		System.out.println("Fault tree json");
		DirectedAcyclicGraph<String, JoinerEdge> graph = faulttree.getFaultTreeForID(Integer.parseInt(treeid));

		return "";
	}
}
