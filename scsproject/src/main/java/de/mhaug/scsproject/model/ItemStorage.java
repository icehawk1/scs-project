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
	private int nextKey = 1;

	public ItemStorage() {
		this.insertItem(new FmecaItem(nextKey, "description 1", new ArrayList<String>()));
	}

	public int getAvailableKey() {
		return nextKey;
	}

	public void insertItem(FmecaItem item) {
		internalStorage.put(getAvailableKey(), item);
		nextKey++;
	}

	public FmecaItem getItem(int id) {
		return internalStorage.get(id);
	}

	public boolean contains(int id) {
		return internalStorage.containsKey(id);
	}

	public void removeItem(int id) {
		internalStorage.remove(id);
	}

	public Collection<FmecaItem> getItems() {
		return Collections.unmodifiableCollection(internalStorage.values());
	}

	public int size() {
		return internalStorage.size();
	}

	public void clear() {
		internalStorage.clear();
		nextKey = 1;
	}
}
