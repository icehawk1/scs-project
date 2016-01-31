package de.mhaug.scsproject.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class FaultTree {
	/**
	 * Gets a specific fault tree
	 * 
	 * TODO: Derzeit nur ein stub
	 * 
	 * @param id
	 *            The unique id of the fault tree
	 */
	public static FaultTreeEvent getFaultTreeForID(int id) {
		FaultTreeEvent result = new FaultTreeEvent(1, "root", "", new FaultTreeJoiner(2, JoinerType.AND));
		List<FaultTreeEvent> children = result.getJoiner().getChildren();
		children.add(new FaultTreeEvent(3, "Event 1", "", null));
		children.add(new FaultTreeEvent(5, "Event 2", "I can haz children", new FaultTreeJoiner(6, JoinerType.OR)));
		children.add(new FaultTreeEvent(7, "Event 3", "", null));

		children = children.get(1).getJoiner().getChildren();
		children.add(new FaultTreeEvent(9, "Event 2.1", "", null));
		children.add(new FaultTreeEvent(11, "Event 2.2", "", null));

		return result;
	}
}

class FaultTreeEvent {
	@SerializedName("id")
	private final int id;
	@SerializedName("value")
	private final String name;
	@SerializedName("comment")
	private final String comment;
	@SerializedName("data")
	private final FaultTreeJoiner joiner;

	public FaultTreeEvent(int id, String name, String comment, FaultTreeJoiner joiner) {
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.joiner = joiner;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public FaultTreeJoiner getJoiner() {
		return joiner;
	}

	@Override
	public String toString() {
		return "FaultTreeEvent [id=" + id + ", name=" + name + ", comment=" + comment + ", joiner=" + joiner + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + id;
		result = prime * result + ((joiner == null) ? 0 : joiner.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		FaultTreeEvent other = (FaultTreeEvent) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (id != other.id)
			return false;
		if (joiner == null) {
			if (other.joiner != null)
				return false;
		} else if (!joiner.equals(other.joiner))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}

class FaultTreeJoiner {
	@SerializedName("id")
	private final int id;
	@SerializedName("value")
	private final String name;
	@SerializedName("data")
	private final List<FaultTreeEvent> children;
	@SerializedName("type")
	private final JoinerType type;

	public FaultTreeJoiner(int id, JoinerType type) {
		this.id = id;
		this.name = type.toString();
		this.children = new ArrayList<>();
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<FaultTreeEvent> getChildren() {
		return children;
	}

	public JoinerType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "FaultTreeJoiner [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		FaultTreeJoiner other = (FaultTreeJoiner) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}

enum JoinerType {
	AND, OR
}
