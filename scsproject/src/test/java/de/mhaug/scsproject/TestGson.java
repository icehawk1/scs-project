package de.mhaug.scsproject;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

/**
 * Test if Gson library is properly setup
 * 
 * @author Martin Haug
 */
public class TestGson extends AbstractTest {

	private Gson gson;

	@Before
	public void setUp() throws Exception {
		this.gson = getInjector().getInstance(Gson.class);
	}

	/**
	 * Test if Gson names fields properly
	 */
	@Test
	public void testGson() {
		String expected = "{\"blub\":\"bla\"}";
		TakeThis original = new TakeThis();
		String actual = gson.toJson(original).replaceAll("\\s+", "");
		assertEquals(expected, actual);
		assertEquals(original, gson.fromJson(actual, TakeThis.class));
	}
}

class TakeThis {
	public String blub = "bla";

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blub == null) ? 0 : blub.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TakeThis other = (TakeThis) obj;
		if (blub == null) {
			if (other.blub != null)
				return false;
		} else if (!blub.equals(other.blub))
			return false;
		return true;
	}

}