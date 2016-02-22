package de.mhaug.scsproject.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Singleton;

@Singleton
public class ItemStorage {
	private final Map<Integer, FmecaItem> internalStorage = new HashMap<>();

	public ItemStorage() {
		internalStorage.put(1, new FmecaItem(1, "description 1", new ArrayList<String>()));
	}

	public void insertItem(FmecaItem item) {
		internalStorage.put(item.getID(), item);
	}

	public FmecaItem getItem(String descr) {
		return internalStorage.get(descr);
	}

	public boolean contains(String description) {
		return internalStorage.containsKey(description);
	}

	public void removeItem(String description) {
		internalStorage.remove(description);
	}

	public Collection<FmecaItem> getItems() {
		return Collections.unmodifiableCollection(internalStorage.values());
	}

	public int size() {
		return internalStorage.size();
	}

	public void clear() {
		internalStorage.clear();
	}
}
