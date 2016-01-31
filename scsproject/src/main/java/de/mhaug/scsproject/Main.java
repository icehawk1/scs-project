package de.mhaug.scsproject;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main class.
 *
 */
public class Main {
	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "http://localhost:8080/";
	private static Injector injector;
	private HttpServer server;

	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		injector = Guice.createInjector(new GuiceModule());
		Main main = injector.getInstance(Main.class);

		main.initVelocity();

		main.startServer();
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));
		System.in.read();
		main.shutdownServer();
	}

	private void initVelocity() {
		Properties props = new Properties();
		props.setProperty("file.resource.loader.path", "src/resources/templates");
		props.setProperty("file.resource.loader.modificationCheckInterval", "10");
		Velocity.init(props);
	}

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 * 
	 * @return Grizzly HTTP server.
	 */
	public void startServer() {
		// create a resource config that scans for JAX-RS resources and
		// providers
		// in de.mhaug.scsproject package
		final ResourceConfig rc = new ResourceConfig().packages("de.mhaug.scsproject.webui");

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

		// Add handler which serves static files
		server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("src/resources/staticfiles"),
				"/staticfiles");

	}

	private void shutdownServer() {
		server.shutdownNow();
	}

	public static Injector getInjector() {
		return injector;
	}
}
