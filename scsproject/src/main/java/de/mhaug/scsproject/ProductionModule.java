package de.mhaug.scsproject;

import de.mhaug.scsproject.model.FaultTreeJoiner;
import de.mhaug.scsproject.webui.TreeListResource;
import de.mhaug.scsproject.webui.VelocityResource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class ProductionModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(VelocityResource.class).to(TreeListResource.class);
	}

	@Provides
	private Context provideVelocityContext(Gson gson) {
		VelocityContext result = new VelocityContext();
		result.put("webix-jsfile", "/staticfiles/webix/webix_debug.js");
		result.put("webix-cssfile", "/staticfiles/webix/webix.css");
		result.put("charset", "UTF-8");
		result.put("possibleJoiners", gson.toJson(FaultTreeJoiner.values()));

		return result;
	}

	@Provides
	private VelocityEngine provideVelocityEngine() {
		VelocityEngine result = new VelocityEngine();
		Properties props = new Properties();
		props.setProperty("file.resource.loader.path", "src/resources/templates");
		props.setProperty("file.resource.loader.modificationCheckInterval", "10");
		props.setProperty("runtime.references.strict", "true");
		result.init(props);
		return result;
	}

	@Provides
	@Singleton
	private Connection provideDBconnection() throws SQLException {
		Connection result = DriverManager.getConnection("jdbc:sqlite:internal.db");

		Statement stmt = result.createStatement();
		stmt.execute("CREATE TABLE IF NOT EXISTS FaultTree(name STRING NOT NULL )");
		stmt.execute("CREATE TABLE IF NOT EXISTS FaultList(treeid int NOT NULL"
				+ ", name STRING NOT NULL, joiner STRING, children STRING, comment STRING,"
				+ "FOREIGN KEY(treeid) REFERENCES FaultTree(rowid) )");
		stmt.execute("CREATE INDEX IF NOT EXISTS flname ON FaultList(name)");

		return result;
	}
}
