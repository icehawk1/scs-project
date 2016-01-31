package de.mhaug.scsproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class GuiceModule extends AbstractModule {
	@Override
	protected void configure() {
	}

	@Provides
	@Singleton
	public Connection provideDBconnection() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:internal.db");
	}
}
