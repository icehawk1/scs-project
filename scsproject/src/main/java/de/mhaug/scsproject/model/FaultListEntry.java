package de.mhaug.scsproject.model;

import com.google.gson.annotations.SerializedName;

public class FaultListEntry {

	@SerializedName("rowid")
	private final int rowid;
	@SerializedName("name")
	private final String name;
	@SerializedName("joiner")
	private final FaultTreeJoiner joiner;
	@SerializedName("children")
	private final String children;
	private transient String comment = "";

	public FaultListEntry(int rowid, String name, FaultTreeJoiner joiner, String children) {
		this(rowid, name, joiner, children, "");
	}

	public FaultListEntry(int rowid, String name, FaultTreeJoiner joiner, String children, String comment) {
		this.rowid = rowid;
		this.name = name;
		this.joiner = joiner;
		this.children = children.trim();
		this.comment = comment;
	}

	public int getRowid() {
		return rowid;
	}

	public String getName() {
		return name;
	}

	public FaultTreeJoiner getJoiner() {
		return joiner;
	}

	public String getChildren() {
		return children;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public String toString() {
		return "FaultListEntry [rowid=" + rowid + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + rowid;
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
		FaultListEntry other = (FaultListEntry) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rowid != other.rowid)
			return false;
		return true;
	}

}
