package de.mhaug.scsproject.webui;

import de.mhaug.scsproject.model.JoinerEdge;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;

public class DepthFirstSearch {
	private Set<FaultTreeEntry> discovered = new HashSet<>();

	public FaultTreeEntry dfs(DirectedAcyclicGraph<String, JoinerEdge> graph) {
		FaultTreeEntry root = new FaultTreeEntry(graph.iterator().next());
		dfsInternal(graph, root);
		return root;
	}

	private void dfsInternal(DirectedAcyclicGraph<String, JoinerEdge> graph, FaultTreeEntry currentRoot) {
		discovered.add(currentRoot);
		for (JoinerEdge edge : graph.outgoingEdgesOf(currentRoot.vertex)) {
			FaultTreeEntry neighbor = new FaultTreeEntry(edge.getTarget());
			if (!discovered.contains(neighbor)) {
				currentRoot.children.add(neighbor);
				dfsInternal(graph, neighbor);
			}
		}
	}
}
