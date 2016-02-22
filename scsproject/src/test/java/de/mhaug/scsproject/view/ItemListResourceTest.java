package de.mhaug.scsproject.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.mhaug.scsproject.AbstractTest;
import de.mhaug.scsproject.model.FmecaItem;
import de.mhaug.scsproject.model.ItemStorage;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ItemListResourceTest extends AbstractTest {

	private ItemListResource instance;
	private ItemStorage storage;
	private String key = "test description";

	@Before
	public void setUp() throws Exception {
		this.instance = getInjector().getInstance(ItemListResource.class);
		this.storage = getInjector().getInstance(ItemStorage.class);
		storage.clear();
		storage.insertItem(new FmecaItem(key, new ArrayList<String>()));
	}

	@Test
	public void testLoadJsonData() {
		String actual = instance.loadJsonData();
		assertTrue(actual.contains("\"Result\":\"OK\""));
		assertTrue(actual.contains(key));
	}

	@Test
	public void testAddItem() {
		String actual = instance.addItem("abcxyz", "peng");
		assertTrue(actual.contains("\"Result\":\"OK\""));
		assertTrue(actual.contains("abcxyz"));
		assertEquals(2, storage.size());
	}

	@Test
	public void testUpdateItem() {
		String actual = instance.updateItem(key);
		assertTrue(actual.contains("\"Result\":\"OK\""));
		assertEquals(1, storage.size());
	}

	@Test
	public void testRemoveItem() {
		String actual = instance.removeItem(key);
		assertTrue(actual.contains("\"Result\":\"OK\""));
		assertEquals(0, storage.size());
	}
}
