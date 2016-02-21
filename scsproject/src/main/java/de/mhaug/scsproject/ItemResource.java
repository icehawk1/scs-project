package de.mhaug.scsproject;

import de.mhaug.scsproject.model.ItemStorage;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;
import com.google.inject.Inject;

@Path("/Item/{item}")
public class ItemResource {
	private Gson gson;
	private ItemStorage storage;

	@Inject
	public ItemResource(ItemStorage storage, Gson gson) {
		this.storage = storage;
		this.gson = gson;
	}

	@DELETE
	public void deleteItem(@PathParam("item") String item) {
		System.out.println("itemResource delete: " + item);
	}
}
