package de.mhaug.scsproject.view;

import de.mhaug.scsproject.model.Criticality;
import de.mhaug.scsproject.model.Detection;
import de.mhaug.scsproject.model.FmecaItem;
import de.mhaug.scsproject.model.ItemStorage;
import de.mhaug.scsproject.model.Probability;

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

/**
 * Displays a series of web pages that allow the user to enter data for a
 * particular component which is identified by the path parameter given. The
 * data will be entered in the order specified by FMECA.
 * 
 * A series of GET and POST requests with redirects inbetween was chosen so that
 * the browser back button still works.
 * 
 * @author Martin Haug
 */
@Path("/ItemList/{item}")
public class FmecaItemEditorResource extends VelocityResource {
	private ItemStorage storage;

	@Inject
	public FmecaItemEditorResource(ItemStorage storage) {
		this.storage = storage;
	}

	/**
	 * Will be called directly after object was fully constructed. This is
	 * necessary, because Criticality.values() is null at the time this object
	 * is created. Beware: This method does not work together with optional
	 * dependencies.
	 */
	@Override
	void init() {
		super.init();

		List<Criticality> criticality_values = new ArrayList<>();
		for (Criticality current : Criticality.values())
			criticality_values.add(current);
		context.put("criticality_values", criticality_values);

		List<Probability> probability_values = new ArrayList<>();
		for (Probability current : Probability.values())
			probability_values.add(current);
		context.put("probability_values", probability_values);

		List<Detection> detection_values = new ArrayList<>();
		for (Detection current : Detection.values())
			detection_values.add(current);
		context.put("detection_values", detection_values);
	}

	/**
	 * Redirects the user to the starting point for entering data.
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response sendGetHtmlDefault(@PathParam("item") String item) throws URISyntaxException {
		return Response.seeOther(new URI("/ItemList/" + item + "/get-failuremode")).build();
	}

	/**
	 * Sends the html for a particular field, where field is either one of the
	 * steps/web pages were the user enters the data. The particular velocity
	 * template will be chosen based on the field parameter.
	 * 
	 * Field can be one of: failuremode, consequences, criticality, mitigations,
	 * adjust-criticality or summary.
	 */
	@GET
	@Path("get-{field}")
	@Produces(MediaType.TEXT_HTML)
	public String sendGetHtmlField(@PathParam("item") String item, @PathParam("field") String field) {
		try {
			int id = Integer.parseInt(item);
			if (!storage.contains(id)) {
				throw new NotFoundException();
			}

			FmecaItem fmecaitem = storage.getItem(id);
			context.put("item", fmecaitem);
			context.put("itemid", id);
			context.put("field", field);
			context.put("minlength", 20);
			context.put("maxlength", 500);

			String result = mergeVelocityTemplate("editor-" + field + ".html", context);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * Saves the possible ways the component could fail.
	 */
	@POST
	@Path("failuremode")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response retrieveFailureMode(@PathParam("item") String item, @FormParam("failuremode") String failuremode)
			throws Exception {
		try {

			// Store the stuff that the user has typed in the form
			// and then redirect him to the next step
			int id = Integer.parseInt(item);
			if (storage.contains(id)) {
				URI nextPage = new URI("/ItemList/" + item + "/get-consequences");
				System.out.println("set failure mode: " + failuremode);
				storage.getItem(id).setFailureMode(failuremode);
				return Response.seeOther(nextPage).build();
			} else {
				throw new NotFoundException();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * Saves the possible consequences a failure of this component could have
	 */
	@POST
	@Path("consequences")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response retrieveConsequences(@PathParam("item") String item, @FormParam("consequences") String consequences)
			throws Exception {
		try {

			// Store the stuff that the user has typed in the form
			// and then redirect him to the next step
			int id = Integer.parseInt(item);
			if (storage.contains(id)) {
				URI nextPage = new URI("/ItemList/" + item + "/get-criticality");
				System.out.println("set consequences: " + consequences);
				storage.getItem(id).setConsequences(consequences);
				return Response.seeOther(nextPage).build();
			} else {
				throw new NotFoundException();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * Saves the three numerical values from which the risk priority number is
	 * computed.
	 * 
	 * @param criticality
	 * @param probability
	 * @param detection
	 */
	@POST
	@Path("criticality")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response retrieveCriticality(@PathParam("item") String item, @FormParam("criticality") String criticality,
			@FormParam("probability") String probability, @FormParam("detection") String detection) throws Exception {
		try {

			// Store the stuff that the user has typed in the form
			// and then redirect him to the next step
			int id = Integer.parseInt(item);
			if (storage.contains(id)) {
				URI nextPage = new URI("/ItemList/" + item + "/get-mitigations");

				if (criticality != null && !criticality.isEmpty())
					storage.getItem(id).setCriticality(Criticality.valueOf(criticality));
				else
					System.err.println("Invalid criticality:" + criticality);

				if (probability != null && !probability.isEmpty())
					storage.getItem(id).setProbability(Probability.valueOf(probability));
				else
					System.err.println("Invalid probability: " + probability);

				if (detection != null && !detection.isEmpty())
					storage.getItem(id).setDetection(Detection.valueOf(detection));
				else
					System.out.println("Invalid detection: " + detection);

				return Response.seeOther(nextPage).build();
			} else {
				throw new NotFoundException();
			}

		} catch (

		Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * Saves the possible ways a failure could be mitigated.
	 */
	@POST
	@Path("mitigations")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response sendPostHtmlMitigations(@PathParam("item") String item,
			@FormParam("mitigations") String mitigations) throws Exception {
		try {

			// Store the stuff that the user has typed in the form
			// and then redirect him to the next step
			int id = Integer.parseInt(item);
			if (storage.contains(id)) {
				URI nextPage = new URI("/ItemList/" + item + "/get-summary");
				System.out.println("set mitigations: " + mitigations);
				storage.getItem(id).setMitigations(mitigations);
				return Response.seeOther(nextPage).build();
			} else {
				throw new NotFoundException();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * Saves nothing but redirects the user to the item list because he is done
	 * editing this component.
	 */
	@POST
	@Path("summary")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Response sendPostHtmlSummary(@PathParam("item") String item) throws Exception {
		try {
			URI nextPage = new URI("/ItemList/");
			return Response.seeOther(nextPage).build();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
