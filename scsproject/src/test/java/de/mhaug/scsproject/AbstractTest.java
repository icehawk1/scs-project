package de.mhaug.scsproject;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.junit.BeforeClass;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

public abstract class AbstractTest {
	private Injector injector;

	public AbstractTest() {
		ProductionModule functionalModule = new ProductionModule();
		TestModule testModule = new TestModule();
		injector = Guice.createInjector(Modules.override(functionalModule).with(testModule));
	}

	@BeforeClass
	public static void setUpClass() {
		Properties props = new Properties();
		props.setProperty("file.resource.loader.path", "src/test/resources");
		props.setProperty("file.resource.loader.modificationCheckInterval", "10");
		Velocity.init(props);
	}

	public Injector getInjector() {
		return injector;
	}
}
