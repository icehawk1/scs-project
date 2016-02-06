package de.mhaug.scsproject.webui;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import com.google.inject.Inject;

public abstract class VelocityResource {
	@Inject
	protected Context context;
	@Inject
	protected VelocityEngine engine;

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
