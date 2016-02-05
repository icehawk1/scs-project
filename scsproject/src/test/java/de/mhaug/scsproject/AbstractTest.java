package de.mhaug.scsproject;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.junit.BeforeClass;

import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class AbstractTest {
	private Injector injector;

	public AbstractTest() {
		injector = Guice.createInjector(new TestModule());
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
