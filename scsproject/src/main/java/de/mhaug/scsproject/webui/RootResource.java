package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.VelocityConfigurator;

import java.io.IOException;
import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.app.Velocity;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class RootResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getIt() {
		StringWriter writer = new StringWriter();
		Velocity.mergeTemplate("RootResource.html", "UTF-8", VelocityConfigurator.getVelocityContext(), writer);
		writer.flush();
		String result = writer.toString();
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
