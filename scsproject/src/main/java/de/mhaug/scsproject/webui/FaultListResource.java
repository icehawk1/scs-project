package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.VelocityConfigurator;
import de.mhaug.scsproject.model.FaultTree;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.context.Context;

@Path("/FaultList")
public class FaultListResource extends JerseyResource {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtml() {
		Context context = VelocityConfigurator.getVelocityContext();
		context.put("tree", FaultTree.getFaultTreeForID(54321));
		return mergeVelocityTemplate("FaultList.html", context);
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void receivePostForm(@FormParam("vorname") String vorname, @FormParam("zuname") String zuname) {
		System.out.println("Received: " + vorname + " " + zuname);
	}
}
