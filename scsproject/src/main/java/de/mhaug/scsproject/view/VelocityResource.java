package de.mhaug.scsproject.view;

import java.io.IOException;
import java.io.StringWriter;

import javax.ws.rs.core.UriBuilder;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import com.google.inject.Inject;

/**
 * Baseclass for all Resources that use Velocity templates.
 * 
 * @author Martin Haug
 */
public abstract class VelocityResource {
	@Inject
	protected Context context;
	@Inject
	protected VelocityEngine engine;

	/**
	 * Initializes the Velocity context with some default variables. Gets called
	 * after injection is done.
	 * 
	 * Warning: This will not work if this class gets optional dependencies.
	 */
	@Inject
	public void init() {

	}

	String getUrlOfResource(Class resourceClass, String methodName) {
		try {
			UriBuilder builder = UriBuilder.fromResource(resourceClass);
			String result = builder.path(resourceClass, methodName).build().toString();
			return result;
		} catch (IllegalArgumentException ex) {
			// The method was not annotated with @Path ...
			// Yes, this is ugly.
			return getUrlOfResource(resourceClass);
		}
	}

	String getUrlOfResource(Class resourceClass) {
		UriBuilder builder = UriBuilder.fromResource(resourceClass);
		String result = builder.build().toString();
		return result;
	}

	/**
	 * Produces the result of applying the given variables to the given template
	 * using Velocity.
	 */
	public String mergeVelocityTemplate(String templatename, Context context) {
		StringWriter writer = new StringWriter();
		engine.mergeTemplate(templatename, "UTF-8", context, writer);
		writer.flush();
		String result = writer.toString();
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
