package de.mhaug.scsproject;

import de.mhaug.scsproject.view.FaultListResource;
import de.mhaug.scsproject.view.FaultTreeResource;
import de.mhaug.scsproject.view.ProbabilityCalculatorResource;
import de.mhaug.scsproject.view.TreeEditorResource;
import de.mhaug.scsproject.view.TreeListResource;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main class.
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
		// create a resource config that scans for JAX-RS resources and
		// providers
		// in de.mhaug.scsproject package
		final ResourceConfig rc = new ResourceConfig().packages("de.mhaug.scsproject.webui");
		registerResources(rc);

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

		// Add handler which serves static files
		server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("src/resources/staticfiles"),
				"/staticfiles");
	}

	private void registerResources(final ResourceConfig rc) {
		rc.register(injector.getInstance(FaultTreeResource.class));
		rc.register(injector.getInstance(FaultListResource.class));
		rc.register(injector.getInstance(TreeEditorResource.class));
		rc.register(injector.getInstance(TreeListResource.class));
		rc.register(injector.getInstance(ProbabilityCalculatorResource.class));
	}

	private void shutdownServer() {
		server.shutdownNow();
		try {
			Connection con = injector.getInstance(Connection.class);
			if (con.isClosed())
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
