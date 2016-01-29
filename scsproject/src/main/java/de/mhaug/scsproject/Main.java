package de.mhaug.scsproject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 *
 */
public class Main {
	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "http://localhost:8080/";

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 * 
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer() {
		// create a resource config that scans for JAX-RS resources and
		// providers
		// in de.mhaug.scsproject package
		final ResourceConfig rc = new ResourceConfig().packages("de.mhaug.scsproject.webui");

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		HttpServer result = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

		// Add handler which serves static files
		result.getServerConfiguration().addHttpHandler(new StaticHttpHandler("src/resources/staticfiles"), "/static");

		return result;
	}

	private static String getTemplatePath() throws URISyntaxException {
		return Main.class.getClassLoader().getResource("resources/staticfiles").getPath();
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		VelocityConfigurator.configureVelocity();

		final HttpServer server = startServer();
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));
		System.in.read();
		server.shutdownNow();
	}
}
