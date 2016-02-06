package de.mhaug.scsproject.webui;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.VelocityContext;

import com.google.inject.Inject;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/TreeEditor")
public class TreeEditorResource extends AbstractResource {
	private VelocityContext context;

	@Inject
	public TreeEditorResource(VelocityContext context) {
		this.context = context;
	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtml(@QueryParam("treeid") String treeid) {
		context.put("treeid", treeid);
		String result = mergeVelocityTemplate("TreeEditor.html", context);
		return result;
	}
}
