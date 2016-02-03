package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.model.JoinerEdge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class DepthFirstSearch {
	private Set<TreeEntry> discovered = new HashSet<>();
	private Stack<TreeEntry> stack = new Stack<>();

	public String dfs(DirectedAcyclicGraph<String, JoinerEdge> graph) {
		TreeEntry root = new TreeEntry(graph.iterator().next());
		dfsInternal(graph, root);
		String result = new Gson().toJson(root);
		return result;
	}

	private void dfsInternal(DirectedAcyclicGraph<String, JoinerEdge> graph, TreeEntry currentRoot) {
		discovered.add(currentRoot);
		for (JoinerEdge edge : graph.outgoingEdgesOf(currentRoot.vertex)) {
			TreeEntry neighbor = new TreeEntry(edge.getTarget());
			if (!discovered.contains(neighbor)) {
				currentRoot.children.add(neighbor);
				dfsInternal(graph, neighbor);
			}
		}
	}

}

class TreeEntry {
	@SerializedName("id")
	public final int id;
	@SerializedName("value")
	public final String vertex;
	@SerializedName("data")
	public final List<TreeEntry> children;

	public TreeEntry(String vertex) {
		this.id = new Random().nextInt();
		this.vertex = vertex;
		this.children = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "TreeEntry [id=" + id + ", vertex=" + vertex + ", #children=" + children.size() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((vertex == null) ? 0 : vertex.hashCode());
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
		TreeEntry other = (TreeEntry) obj;
		if (id != other.id)
			return false;
		if (vertex == null) {
			if (other.vertex != null)
				return false;
		} else if (!vertex.equals(other.vertex))
			return false;
		return true;
	}

}
