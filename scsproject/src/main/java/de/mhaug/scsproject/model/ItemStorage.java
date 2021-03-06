package de.mhaug.scsproject.model;

import de.mhaug.scsproject.Main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Provides access to all the data the user has entered for all components. The
 * implementation with a map was chosen for ease of use. It is a Singleton
 * because we only need one list of components as this application only supports
 * a single user. If we want to support more than one user, we will need one
 * storage per user.
 * 
 * @author Martin Haug
 */
@Singleton
public class ItemStorage {
	/** Use a TreeMap so its always sorted */
	private SortedMap<Integer, FmecaItem> internalStorage = new TreeMap<>();
	private int nextKey = 1;
	private Gson gson;
	private static final File savedir = new File(System.getProperty("user.home"), ".scsstorage");

	@Inject
	public ItemStorage(Gson gson) {
		this.gson = gson;
		if (!savedir.exists())
			savedir.mkdirs();

		if (Main.debug_mode) {
			this.insertItem(new FmecaItem(1, "name 1", "description 1"));
			this.insertItem(new FmecaItem(2, "name 2", "description 2"));
			this.insertItem(new FmecaItem(3));
		}
	}

	/**
	 * Gets the next key that is not already assigned
	 */
	public int getAvailableKey() {
		assert !contains(nextKey);
		return nextKey;
	}

	/**
	 * Inserts a new item in the collection
	 */
	public void insertItem(FmecaItem item) {
		internalStorage.put(item.getId(), item);
		// Make sure nextKey is always higher than the higher key in the map
		nextKey = Math.max(nextKey + 1, item.getId() + 1);
	}

	/**
	 * Gets the item with the given id from the collection
	 * 
	 * @return the item or null if there is no item with this id
	 */
	public FmecaItem getItem(int id) {
		if (!contains(id)) {
			return null;
		}

		FmecaItem result = internalStorage.get(id);
		assert id == result.getId() : "The id in the Map and in the java bean should always be the same. "
				+ "It is just in two places so the templates can access it.";
		return result;
	}

	public boolean contains(int id) {
		return internalStorage.containsKey(id);
	}

	/**
	 * Removes the item with the given id only if it is present (optional
	 * operation)
	 */
	public void removeItem(int id) {
		if (contains(id)) {
			internalStorage.remove(id);
		}
	}

	/**
	 * Gets all items in the collection. The return value is read only so that
	 * the user can't accidently destroy the integrity of this object.
	 */
	public Collection<FmecaItem> getItems() {
		Collection<FmecaItem> result = Collections.unmodifiableCollection(internalStorage.values());
		return result;
	}

	public int size() {
		return internalStorage.size();
	}

	public void clear() {
		internalStorage.clear();
		nextKey = 1;
	}

	public void save(String filename) throws IOException {
		File savefile = new File(savedir, filename);
		if (!savefile.exists())
			savefile.createNewFile();

		FileWriter filewriter = new FileWriter(savefile);
		gson.toJson(internalStorage, filewriter);

		filewriter.flush();
		filewriter.close();
	}

	public void load(String filename) throws IOException {
		clear();

		FileReader filereader = new FileReader(new File(savedir, filename));
		assert internalStorage != null;

		internalStorage = gson.fromJson(filereader, new TypeToken<TreeMap<Integer, FmecaItem>>() {
		}.getType());

		assert internalStorage != null;

		filereader.close();
	}

	@Override
	public String toString() {
		return "storage: " + internalStorage.toString();
	}
}
