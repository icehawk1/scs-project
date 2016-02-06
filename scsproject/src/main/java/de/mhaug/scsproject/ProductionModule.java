package de.mhaug.scsproject;

import de.mhaug.scsproject.webui.AbstractResource;
import de.mhaug.scsproject.webui.TreeListResource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class ProductionModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(AbstractResource.class).to(TreeListResource.class);
	}

	@Provides
	@Singleton
	public Connection provideDBconnection() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:internal.db");
	}
}
