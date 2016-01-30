package de.mhaug.scsproject.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * A generic n-ary tree data structure
 * 
 * @param <T>
 *            The type of data that the tree should hold
 * @author Martin Haug
 */
public class Tree<T> {
	private List<Tree<T>> children = new LinkedList<Tree<T>>();
	private T data = null;

	public Tree(T data) {
		this.data = data;
	}

	public Tree(T data, Tree<T> parent) {
		this.data = data;
	}

	public List<Tree<T>> getChildren() {
		return children;
	}

	public void addChild(T data) {
		Tree<T> child = new Tree<T>(data);
		this.children.add(child);
	}

	public void addChild(Tree<T> child) {
		this.children.add(child);
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isLeaf() {
		if (this.children.isEmpty())
			return true;
		else
			return false;
	}

	public List<T> traverse() {
		return traverse((child) -> {
			return true;
		});
	}

	private List<T> traverse(Function<Tree, Boolean> lambda) {
		List<T> result = new LinkedList<>();
		for (Tree<T> found : traverse(new LinkedList<Tree<T>>(), lambda)) {
			result.add(found.getData());
		}
		return result;
	}

	private List<Tree<T>> traverse(List<Tree<T>> result, Function<Tree, Boolean> lambda) {
		if (lambda.apply(this)) {
			result.add(this);
		}

		for (Tree<T> child : getChildren()) {
			child.traverse(result, lambda);
		}

		return result;
	}

	public List<T> getLeafes() {
		return traverse((child) -> {
			return child.isLeaf();
		});
	}

	public List<T> search(T value) {
		return traverse((child) -> {
			return child.getData().equals(value);
		});
	}

	@Override
	public String toString() {
		return "Tree [ data=" + data + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		Tree other = (Tree) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

}