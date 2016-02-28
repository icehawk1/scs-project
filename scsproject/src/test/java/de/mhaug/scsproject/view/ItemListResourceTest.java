package de.mhaug.scsproject.view;

import de.mhaug.scsproject.AbstractTest;
import de.mhaug.scsproject.model.FmecaItem;
import de.mhaug.scsproject.model.ItemStorage;

import org.junit.Before;

public class ItemListResourceTest extends AbstractTest {

	private static final String name = "test name";
	private ItemListResource instance;
	private ItemStorage storage;
	private static final String description = "test description";

	@Before
	public void setUp() throws Exception {
		this.instance = getInjector().getInstance(ItemListResource.class);
		this.storage = getInjector().getInstance(ItemStorage.class);
		storage.clear();
		storage.insertItem(new FmecaItem(1, name, description));
	}
}
