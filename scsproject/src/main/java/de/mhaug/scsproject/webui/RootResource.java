package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.Main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class RootResource extends JerseyResource {
	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtml() {
		Context context = Main.getInjector().getInstance(VelocityContext.class);
		String result = mergeVelocityTemplate("Homepage.html", context);
		return result;
	}
}
