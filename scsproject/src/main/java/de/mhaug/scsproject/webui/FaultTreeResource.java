package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.Main;
import de.mhaug.scsproject.model.FaultTree;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import com.google.gson.Gson;

@Path("/FaultTree")
public class FaultTreeResource extends JerseyResource {
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
	public String sendGetJson() {
		System.out.println("Fault tree json");
		Gson gson = new Gson();
		return gson.toJson(FaultTree.getFaultTreeForID(54321));
	}
}
