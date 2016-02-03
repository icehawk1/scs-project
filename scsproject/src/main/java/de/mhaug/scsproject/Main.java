package de.mhaug.scsproject;

import de.mhaug.scsproject.webui.FaultListResource;
import de.mhaug.scsproject.webui.FaultTreeResource;
import de.mhaug.scsproject.webui.RootResource;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException, SQLException {
		injector = Guice.createInjector(new GuiceModule());
		Main main = injector.getInstance(Main.class);

		main.initVelocity();
		main.initDatabase();

		main.startServer();
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));
		System.in.read();
		main.shutdownServer();
	}

	private void initDatabase() throws SQLException {
		Connection con = injector.getInstance(Connection.class);
		Statement stmt = con.createStatement();

		stmt.execute("CREATE TABLE IF NOT EXISTS FaultTree(rowid int NOT NULL PRIMARY KEY, name STRING NOT NULL )");
		stmt.execute("CREATE TABLE IF NOT EXISTS FaultList(rowid int NOT NULL PRIMARY KEY, treeid int NOT NULL"
				+ ", name STRING NOT NULL, joiner STRING, children STRING, comment STRING,"
				+ "FOREIGN KEY(treeid) REFERENCES FaultTree(rowid) )");
		stmt.execute("CREATE UNIQUE INDEX IF NOT EXISTS flname ON FaultList(name)");
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
		rc.register(getInjector().getInstance(FaultTreeResource.class));
		rc.register(getInjector().getInstance(FaultListResource.class));
		rc.register(getInjector().getInstance(RootResource.class));

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

		// Add handler which serves static files
		server.getServerConfiguration().addHttpHandler(new StaticHttpHandler("src/resources/staticfiles"),
				"/staticfiles");
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

	public static Injector getInjector() {
		return injector;
	}
}
