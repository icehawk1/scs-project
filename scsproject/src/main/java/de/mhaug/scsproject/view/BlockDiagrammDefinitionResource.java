package de.mhaug.scsproject.view;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.velocity.context.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.inject.Inject;

@Path("BlockDiagramDefinition")
public class BlockDiagrammDefinitionResource extends VelocityResource {
	private final Context context;
	private final Gson gson;

	@Inject
	public BlockDiagrammDefinitionResource(Context context, Gson gson) {
		this.context = context;
		this.gson = gson;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHtml() {
		String loadUrl = getUrlOfResource(BlockDiagrammDefinitionResource.class, "dataLoadUrl");
		System.out.println("loadurl: " + loadUrl);
		context.put("bdDefinitionLoadUrl", loadUrl);
		String result = mergeVelocityTemplate("BlockDiagramDefiniton.html", context);
		return result;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String dataLoadUrl() {
		List<FmecaItem> data = new ArrayList<>();
		data.add(new FmecaItem(1, "first item", "first requirement"));
		data.add(new FmecaItem(2, "second item", "second requirement"));

		String result = gson.toJson(data);
		return result;
	}
}

class FmecaItem {
	@SerializedName("rowid")
	private final int rowid;
	@SerializedName("item")
	private final String item;
	@SerializedName("requiredBy")
	private final String requiredBy;
	@SerializedName("failureMode")
	private String failureMode = "abc";

	public FmecaItem(int rowid, String item, String requiredBy) {
		this.rowid = rowid;
		this.item = item;
		this.requiredBy = requiredBy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((requiredBy == null) ? 0 : requiredBy.hashCode());
		result = prime * result + rowid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FmecaItem other = (FmecaItem) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (requiredBy == null) {
			if (other.requiredBy != null)
				return false;
		} else if (!requiredBy.equals(other.requiredBy))
			return false;
		if (rowid != other.rowid)
			return false;
		return true;
	}

}