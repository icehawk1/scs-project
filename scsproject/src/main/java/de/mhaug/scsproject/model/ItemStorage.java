package de.mhaug.scsproject.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Singleton;

@Singleton
public class ItemStorage {
	private final Map<String, FmecaItem> storage = new HashMap<>();

	public void insertItem(FmecaItem item) {
		storage.put(item.getDescription(), item);
	}

	public FmecaItem getItem(String descr) {
		return storage.get(descr);
	}

	public boolean contains(String description) {
		return storage.containsKey(description);
	}

	public void removeItem(String description) {
		storage.remove(description);
	}

	public Collection<FmecaItem> getItems() {
		return Collections.unmodifiableCollection(storage.values());
	}
}
