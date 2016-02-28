package de.mhaug.scsproject.view;

import de.mhaug.scsproject.model.FmecaItem;
import de.mhaug.scsproject.model.ItemStorage;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Executes create and delete operations on the {@link ItemStorage}. This class
 * is needed because we need to have this on a different path because
 * /ItemList/XYZ is already full. All Resources are singletons because only one
 * instance will be registered with JAX-RS.
 * 
 * @author Martin Haug
 */
@Path("/ItemHelper")
@Singleton
public class ItemHelperResource {
	private ItemStorage storage;

	@Inject
	public ItemHelperResource(ItemStorage storage) {
		this.storage = storage;
	}

	/**
	 * Creates a new item and redirects the user back to the item list
	 */
	@GET
	@Path("new")
	@Produces(MediaType.TEXT_HTML)
	public Response acceptCreateRequest() throws URISyntaxException {
		storage.insertItem(new FmecaItem(storage.getAvailableKey()));
		return Response.seeOther(new URI("/ItemList/")).build();
	}

	/**
	 * Removes an item and redirects the user back to the item list
	 */
	@GET
	@Path("remove")
	@Produces(MediaType.TEXT_HTML)
	public Response acceptDeleteRequest(@QueryParam("id") int id) throws URISyntaxException {
		storage.removeItem(id);
		return Response.seeOther(new URI("/ItemList/")).build();
	}
}
