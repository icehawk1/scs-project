package de.mhaug.scsproject.webui;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

abstract class JerseyResource {
	public String mergeVelocityTemplate(String templatename, Context context) {
		StringWriter writer = new StringWriter();
		Velocity.mergeTemplate(templatename, "UTF-8", context, writer);
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
