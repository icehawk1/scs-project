package de.mhaug.scsproject.view;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.context.Context;

import com.google.inject.Inject;

@Path("BlockDiagramDefinition")
public class BlockDiagrammDefinitionResource extends VelocityResource {
	private final Context context;

	@Inject
	public BlockDiagrammDefinitionResource(Context context) {
		this.context = context;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHtml() {
		context.put("bdDefinitionLoadUrl", getUrlOfResource(ItemListResource.class, "loadJsonData"));
		context.put("addItemUrl", getUrlOfResource(ItemListResource.class, "addItem"));

		String result = mergeVelocityTemplate("BlockDiagramDefiniton.html", context);
		return result;
	}
}