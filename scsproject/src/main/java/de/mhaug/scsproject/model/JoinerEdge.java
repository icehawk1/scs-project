package de.mhaug.scsproject.model;

import org.jgrapht.graph.DefaultEdge;

/**
 * An Edge in the FaultTree. Can hold a FaultTreeJoiner.
 * 
 * @author Martin Haug
 */
public class JoinerEdge extends DefaultEdge {
	private static final long serialVersionUID = 4941354549324940119L;
	public final FaultTreeJoiner joiner;
	private final String from;
	private String to;

	public JoinerEdge(String source, String target, FaultTreeJoiner joiner) {
		this.from = source;
		this.to = target;
		this.joiner = joiner;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	protected String getSource() {
		return from;
	}

	@Override
	public String getTarget() {
		return to;
	}

	public FaultTreeJoiner getJoiner() {
		return joiner;
	}

}