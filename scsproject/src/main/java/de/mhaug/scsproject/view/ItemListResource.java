package de.mhaug.scsproject.view;

import de.mhaug.scsproject.model.Criticality;
import de.mhaug.scsproject.model.Detection;
import de.mhaug.scsproject.model.FmecaItem;
import de.mhaug.scsproject.model.ItemStorage;
import de.mhaug.scsproject.model.Probability;

import java.util.ArrayList;
import java.util.List;

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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String loadJsonData() {
		List<FmecaItem> data = new ArrayList<>();
		data.add(new FmecaItem("first item", new ArrayList<String>(), "first failure mode",
				Criticality.HazardousWithWarning, Probability.Low, Detection.High));
		data.add(new FmecaItem("second item", new ArrayList<String>(), "second failure mode", Criticality.VeryLow,
				Probability.VeryHigh, Detection.High));

		String result = gson.toJson(data);
		return result;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addItem(String itemJson) {
		FmecaItem item = gson.fromJson(itemJson, FmecaItem.class);
		storage.insertItem(item);
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void removeItem(@FormParam("description") String description) {
		if (storage.contains(description)) {
			storage.removeItem(description);
		}
	}
}
