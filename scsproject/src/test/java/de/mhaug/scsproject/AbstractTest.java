package de.mhaug.scsproject;

import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class AbstractTest {
	private Injector injector;

	public AbstractTest() {
		injector = Guice.createInjector(new TestModule());
	}

	public Injector getInjector() {
		return injector;
	}
}
