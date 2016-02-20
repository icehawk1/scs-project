package de.mhaug.scsproject.model;

import java.util.List;

public class FmecaItem {
	private final String description;
	private List<String> requiredBy;
	private String failureMode;
	private Criticality criticality;
	private Probability probability;
	private Detection detection;

	public FmecaItem(String description, List<String> requiredBy, String failureMode, Criticality criticality,
			Probability probability, Detection detection) {
		this.description = description;
		this.requiredBy = requiredBy;
		this.failureMode = failureMode;
		this.criticality = criticality;
		this.probability = probability;
		this.detection = detection;
	}

	public List<String> getRequiredBy() {
		return requiredBy;
	}

	public void setRequiredBy(List<String> requiredBy) {
		this.requiredBy = requiredBy;
	}

	public String getFailureMode() {
		return failureMode;
	}

	public void setFailureMode(String failureMode) {
		this.failureMode = failureMode;
	}

	public Criticality getCriticality() {
		return criticality;
	}

	public void setCriticality(Criticality criticality) {
		this.criticality = criticality;
	}

	public Probability getProbability() {
		return probability;
	}

	public void setProbability(Probability probability) {
		this.probability = probability;
	}

	public Detection getDetection() {
		return detection;
	}

	public void setDetection(Detection detection) {
		this.detection = detection;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "FmecaItem [description=" + description + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FmecaItem other = (FmecaItem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}
}
