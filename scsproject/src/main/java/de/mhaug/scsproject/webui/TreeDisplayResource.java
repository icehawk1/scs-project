package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.VelocityConfigurator;
import de.mhaug.scsproject.model.FaultTree;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.context.Context;

@Path("/faultTree")
public class TreeDisplayResource extends JerseyResource {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtml() {
		Context context = VelocityConfigurator.getVelocityContext();
		context.put("tree", FaultTree.getFaultTreeForID(54321));
		return mergeVelocityTemplate("FaultTree.html", context);
	}
}
