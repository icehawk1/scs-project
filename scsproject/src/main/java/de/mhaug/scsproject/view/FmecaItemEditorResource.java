package de.mhaug.scsproject.view;

import de.mhaug.scsproject.model.Criticality;
import de.mhaug.scsproject.model.FmecaItem;
import de.mhaug.scsproject.model.ItemStorage;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ItemList/{item}")
public class FmecaItemEditorResource extends VelocityResource {
	private ItemStorage storage;

	@Inject
	public FmecaItemEditorResource(ItemStorage storage) {
		this.storage = storage;
	}

	@Override
	void init() {
		super.init();

		List<Criticality> criticalities = new ArrayList<>();
		for (Criticality crit : Criticality.values())
			criticalities.add(crit);
		context.put("possible_criticalities", criticalities);
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response sendGetHtmlDefault(@PathParam("item") String item) throws URISyntaxException {
		return Response.seeOther(new URI("/ItemList/" + item + "/get-failuremode")).build();
	}

	@GET
	@Path("get-{field}")
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtmlField(@PathParam("item") String item, @PathParam("field") String field) {
		try {
			int id = Integer.parseInt(item);
			if (!storage.contains(id)) {
				throw new NotFoundException();
			}

			FmecaItem deleteme = storage.getItem(id);
			System.out.print(deleteme + " ");
			System.out.println(deleteme.getFailureMode());
			context.put("item", deleteme);
			context.put("minlength", 20);
			context.put("maxlength", 500);

			String result = mergeVelocityTemplate("editor-" + field + ".html", context);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@POST
	@Path("failuremode")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response sendPostHtmlFailureMode(@PathParam("item") String item, @FormParam("input") String input)
			throws Exception {
		try {

			// Store the stuff that the user has typed in the form
			// and then redirect him to the next step
			int id = Integer.parseInt(item);
			if (storage.contains(id)) {
				URI nextPage = new URI("/ItemList/" + item + "/get-criticality");
				System.out.println("set failure mode: " + input);
				storage.getItem(id).setFailureMode(input);
				return Response.seeOther(nextPage).build();
			} else {
				throw new NotFoundException();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@POST
	@Path("criticality")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response sendPostHtmlCriticality(@PathParam("item") String item, @FormParam("input") String input)
			throws Exception {
		try {

			// Store the stuff that the user has typed in the form
			// and then redirect him to the next step
			int id = Integer.parseInt(item);
			if (storage.contains(id)) {
				URI nextPage = new URI("/ItemList/" + item + "/get-summary");
				if (input != null && !input.isEmpty()) {
					storage.getItem(id).setCriticality(Criticality.valueOf(input));
				} else {
					System.err.println("Invalid criticality:" + input);
				}
				return Response.seeOther(nextPage).build();
			} else {
				throw new NotFoundException();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	@POST
	@Path("summary")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response sendPostHtmlSummary(@PathParam("item") String item, @FormParam("input") String input)
			throws Exception {
		try {
			URI nextPage = new URI("/ItemList/");
			return Response.seeOther(nextPage).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
