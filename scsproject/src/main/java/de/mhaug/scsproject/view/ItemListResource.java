package de.mhaug.scsproject.view;

import de.mhaug.scsproject.model.ItemStorage;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;

/**
 * Displays the list currently known components/items. All Resources are
 * singletons because only one instance will be registered with JAX-RS.
 * 
 * @author Martin Haug
 */
@Singleton
@Path("/ItemList")
public class ItemListResource extends VelocityResource {
	private ItemStorage storage;

	@Inject
	public ItemListResource(ItemStorage storage) {
		this.storage = storage;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtml() {
		try {

			context.put("storage", storage);
			String result = mergeVelocityTemplate("ItemList.html", context);
			return result;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
