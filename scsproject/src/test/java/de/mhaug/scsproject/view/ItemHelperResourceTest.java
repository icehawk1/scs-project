package de.mhaug.scsproject.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.mhaug.scsproject.AbstractTest;
import de.mhaug.scsproject.model.FmecaItem;
import de.mhaug.scsproject.model.ItemStorage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class ItemHelperResourceTest extends AbstractTest {
	private ItemHelperResource instance;
	private ItemStorage storage;

	@Before
	public void setUp() throws Exception {
		instance = getInjector().getInstance(ItemHelperResource.class);
		storage = getInjector().getInstance(ItemStorage.class);
	}

	@Test
	public void testGetItems() {
		Collection<FmecaItem> items = storage.getItems();
		assertEquals(3, items.size());
		assertTrue(items.contains(new FmecaItem(3)));
	}

	@Test
	public void testSaveLoad() throws URISyntaxException, IOException {
		Collection<FmecaItem> itemsBefore = new HashSet<>();
		itemsBefore.addAll(storage.getItems());
		assertTrue(itemsBefore.size() > 0);

		instance.acceptSaveRequest("testfile.json");
		storage.clear();
		instance.acceptLoadRequest("testfile.json");

		assertEquals(itemsBefore.size(), storage.size());
		for (FmecaItem item : itemsBefore)
			assertTrue(storage.contains(item.getId()));
	}
}
