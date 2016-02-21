package de.mhaug.scsproject.view;

import de.mhaug.scsproject.model.FmecaItem;
import de.mhaug.scsproject.model.ItemStorage;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.inject.Inject;

@Path("/ItemList")
public class ItemListResource {
	private Gson gson;
	private ItemStorage storage;

	@Inject
	public ItemListResource(ItemStorage storage, Gson gson) {
		this.storage = storage;
		this.gson = gson;
	}

	/**
	 * Loads the list of Items via POST. This is not RESTful, but JTable can't
	 * do it properly. Returns the same value as via GET.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String loadJsonDataPost() {
		return loadJsonData();
	}

	/**
	 * Loads the list of Items currently in the data store.
	 * 
	 * @return The data in JSON format
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String loadJsonData() {
		System.out.println("Item list GET");

		Collection<FmecaItem> data = storage.getItems();
		String result = gson.toJson(data);

		if (result != null) {
			return "{ \"Result\":\"OK\", \"Records\":" + result + " }";
		} else {
			return "{\"Result\":\"ERROR\", \"Message\":\"data is null\"}";
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String addItem(@FormParam("description") String description, @FormParam("failureMode") String failureMode) {
		System.out.println("data: " + description + " " + failureMode);

		FmecaItem toInsert = new FmecaItem(description, new ArrayList<String>());
		toInsert.setFailureMode(failureMode);
		storage.insertItem(toInsert);

		return "{ \"Result\":\"OK\", \"Record\":" + gson.toJson(toInsert) + " }";
	}

	// @POST
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// public void removeItem(@FormParam("description") String description) {
	// System.out.println("remove: " + description);
	//
	// if (storage.contains(description)) {
	// storage.removeItem(description);
	// }
	// }
}
