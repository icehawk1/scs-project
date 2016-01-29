package de.mhaug.scsproject;

import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

public class VelocityConfigurator {
	private static Context context;

	public VelocityConfigurator() {
		context = new VelocityContext();
	}

	public static void configureVelocity() {
		Properties props = new Properties();
		props.setProperty("file.resource.loader.path", "src/resources/templates");
		props.setProperty("file.resource.loader.modificationCheckInterval", "10");
		Velocity.init(props);
	}

	public static Context getVelocityContext() {
		return new VelocityContext(context);
	}
}
