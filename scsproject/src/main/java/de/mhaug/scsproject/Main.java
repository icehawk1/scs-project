package de.mhaug.scsproject;

import de.mhaug.scsproject.view.FmecaItemEditorResource;
import de.mhaug.scsproject.view.ItemHelperResource;
import de.mhaug.scsproject.view.ItemListResource;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import javax.ws.rs.ProcessingException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main class, starts the HTTP server, runs it and shuts it down.
 */
public class Main {
	/** Base URI the Grizzly HTTP server will listen on */
	public static final String BASE_URI = "http://localhost:8080/";
	/**
	 * The injector which is used instead of the new operator because we are
	 * doing Dependency Injection
	 */
	private static Injector injector;
	private HttpServer server;

	/**
	 * Defines if this application should be executed in debug mode.
	 */
	public static final boolean debug_mode = false;

	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, SQLException {
		injector = Guice.createInjector(new ProductionModule());
		Main main = injector.getInstance(Main.class);

		main.startServer();
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));
		System.in.read();
		main.shutdownServer();
	}

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 * 
	 * @return Grizzly HTTP server.
	 */
	public void startServer() {
		try {
		// create a resource config that scans for JAX-RS resources and
		// providers in de.mhaug.scsproject package
		final ResourceConfig rc = new ResourceConfig().packages("de.mhaug.scsproject.webui");
		registerResources(rc);

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

		// Add handler which serves static files
		server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("src/resources/staticfiles"),
				"/staticfiles");
		} catch(ProcessingException ex){System.err.println("Could not start server: "+ex.getMessage());}
	}

	/**
	 * We need to manually register our Resources with JAX-RS because if JAX-RS
	 * does it itself, it will not use Dependency Injection and then the
	 * Resources do not get the necessary parameters.
	 */
	private void registerResources(final ResourceConfig rc) {
		rc.register(injector.getInstance(ItemListResource.class));
		rc.register(injector.getInstance(FmecaItemEditorResource.class));
		rc.register(injector.getInstance(ItemHelperResource.class));
	}

	public void shutdownServer() {
		server.shutdownNow();
	}
}
