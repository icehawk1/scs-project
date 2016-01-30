package de.mhaug.scsproject.model;

import de.mhaug.scsproject.tree.Tree;

public class FaultTree {
	/**
	 * Gets a specific fault tree
	 * 
	 * TODO: Derzeit nur ein stub
	 * 
	 * @param id
	 *            The unique id of the fault tree
	 */
	public static Tree<String> getFaultTreeForID(int id) {
		Tree<String> result = new Tree<>("root");
		result.addChild("child1");
		result.addChild("child2");
		result.addChild("child3");

		result.getChildren().get(1).addChild("subchild 2.1");
		result.getChildren().get(1).addChild("subchild 2.2");
		return result;
	}
}
