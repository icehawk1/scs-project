package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.VelocityConfigurator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/faultTree")
public class TreeDisplayResource extends JerseyResource {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtml() {
		return mergeVelocityTemplate("FaultTree.html", VelocityConfigurator.getVelocityContext());
	}
}
