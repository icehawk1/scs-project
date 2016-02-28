package de.mhaug.scsproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A java bean that contains the data that the user has entered for a particular
 * component and its dependencies. It also contains a method for calculation the
 * risk priority number for this component as defined by FMECA.
 * 
 * @author Martin Haug
 */
public class FmecaItem {
	private String description = "";
	private List<String> requiredBy = new ArrayList<>();
	private String failureMode = "";
	private String consequences = "";
	private Criticality criticality = Criticality.None;
	private Probability probability = Probability.Remote;
	private Detection detection = Detection.NoDetectionPossible;
	private String mitigations = "";
	private String mitigations2;

	public FmecaItem(String description, List<String> requiredBy) {
		this.requiredBy = requiredBy;
		assert requiredBy != null;
		this.description = description;
		assert description != null;
	}

	public FmecaItem(String description, List<String> requiredBy, String failureMode, String consequences,
			Criticality criticality, Probability probability, Detection detection, String mitigations) {
		this.consequences = consequences;
		assert consequences != null;
		this.description = description;
		assert description != null;
		this.requiredBy = requiredBy;
		assert requiredBy != null;
		this.failureMode = failureMode;
		assert failureMode != null;
		this.criticality = criticality;
		assert criticality != null;
		this.probability = probability;
		assert probability != null;
		this.detection = detection;
		assert detection != null;
		this.mitigations = mitigations;
		assert mitigations != null;
	}

	public int getRiskPriorityNumber() {
		int result = criticality.getNumber() * probability.getNumber() * detection.getNumber();
		return result;
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

	public String getConsequences() {
		return consequences;
	}

	public void setConsequences(String consequences) {
		this.consequences = consequences;
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

	public String getMitigations() {
		return mitigations;
	}

	public void setMitigations(String mitigations) {
		this.mitigations = mitigations;
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
		result = prime * result + ((consequences == null) ? 0 : consequences.hashCode());
		result = prime * result + ((criticality == null) ? 0 : criticality.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((detection == null) ? 0 : detection.hashCode());
		result = prime * result + ((failureMode == null) ? 0 : failureMode.hashCode());
		result = prime * result + ((mitigations == null) ? 0 : mitigations.hashCode());
		result = prime * result + ((probability == null) ? 0 : probability.hashCode());
		result = prime * result + ((requiredBy == null) ? 0 : requiredBy.hashCode());
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
		if (consequences == null) {
			if (other.consequences != null)
				return false;
		} else if (!consequences.equals(other.consequences))
			return false;
		if (criticality != other.criticality)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (detection != other.detection)
			return false;
		if (failureMode == null) {
			if (other.failureMode != null)
				return false;
		} else if (!failureMode.equals(other.failureMode))
			return false;
		if (mitigations == null) {
			if (other.mitigations != null)
				return false;
		} else if (!mitigations.equals(other.mitigations))
			return false;
		if (probability != other.probability)
			return false;
		if (requiredBy == null) {
			if (other.requiredBy != null)
				return false;
		} else if (!requiredBy.equals(other.requiredBy))
			return false;
		return true;
	}

}
