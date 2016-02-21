package de.mhaug.scsproject;

import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class ProductionModule extends AbstractModule {
	@Override
	protected void configure() {
	}

	@Provides
	private Context provideVelocityContext(Gson gson) {
		VelocityContext result = new VelocityContext();
		result.put("webix-jsfile", "/staticfiles/webix/webix_debug.js");
		result.put("webix-cssfile", "/staticfiles/webix/webix.css");
		result.put("charset", "UTF-8");

		return result;
	}

	@Provides
	private VelocityEngine provideVelocityEngine() {
		VelocityEngine result = new VelocityEngine();
		Properties props = new Properties();
		props.setProperty("file.resource.loader.path", "src/resources/templates");
		props.setProperty("file.resource.loader.modificationCheckInterval", "5");
		props.setProperty("runtime.references.strict", "true");
		props.setProperty("runtime.references.strict.escape", "true");
		// props.setProperty("runtime.log.invalid.references", "true");
		result.init(props);
		return result;
	}
}
