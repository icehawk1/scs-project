package de.mhaug.scsproject.tree;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TreeTest {

	private Tree<String> instance;

	@Before
	public void setUp() throws Exception {
		instance = new Tree<String>("root");
		instance.addChild("child1");
		instance.addChild("child2");
		instance.addChild("child3");

		instance.getChildren().get(1).addChild("subchild 2.1");
		instance.getChildren().get(1).addChild("subchild 2.2");
	}

	@Test
	public void testTraverse() {
		List<String> expected = new ArrayList<>();
		expected.add("root");
		expected.add("child1");
		expected.add("child2");
		expected.add("subchild 2.1");
		expected.add("subchild 2.2");
		expected.add("child3");

		List<String> actual = instance.traverse();
		assertEquals(expected, actual);
	}

	@Test
	public void testSearch() {
		List<String> expected = new ArrayList<>();
		expected.add(instance.getChildren().get(1).getChildren().get(1).getData());

		List<String> actual = instance.search("subchild 2.2");
		assertEquals(expected, actual);
	}

	@Test
	public void testGetLeafes() {
		Collection<String> expected = new ArrayList<>();
		expected.add("child1");
		expected.add("subchild 2.1");
		expected.add("subchild 2.2");
		expected.add("child3");

		Collection<String> actual = instance.getLeafes();
		assertEquals(expected, actual);
	}

}
