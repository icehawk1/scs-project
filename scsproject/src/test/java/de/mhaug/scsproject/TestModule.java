package de.mhaug.scsproject;

import de.mhaug.scsproject.view.BlockDiagrammDefinitionResource;
import de.mhaug.scsproject.view.VelocityResource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class TestModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(VelocityResource.class).to(BlockDiagrammDefinitionResource.class);
		bind(WebDriver.class).to(FirefoxDriver.class);
	}

	@Provides
	@Singleton
	public Connection provideDBconnection() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:src/test/resources/test.db");
	}
}
