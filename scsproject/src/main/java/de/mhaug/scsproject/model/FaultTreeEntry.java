package de.mhaug.scsproject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.annotations.SerializedName;

public class FaultTreeEntry {
	@SerializedName("id")
	public final String id;
	@SerializedName("value")
	public final String vertex;
	@SerializedName("data")
	public final List<FaultTreeEntry> children;

	public FaultTreeEntry(String vertex) {
		this.id = "" + Math.abs(new Random().nextInt());
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		FaultTreeEntry other = (FaultTreeEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (vertex == null) {
			if (other.vertex != null)
				return false;
		} else if (!vertex.equals(other.vertex))
			return false;
		return true;
	}

}
