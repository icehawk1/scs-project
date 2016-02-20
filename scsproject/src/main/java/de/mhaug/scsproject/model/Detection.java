package de.mhaug.scsproject.model;

public enum Detection implements FmecaEnum {
	// @formatter:off
	NoDetectionPossible("Failure will not be detected"), 
	VeryRemote("Very remote chance that failure will be detected beforehand"), 
	Remote("Remote chance that failure will be detected beforehand"), 
	VeryLow("Very low chance that failure will be detected beforehand"), 
	Low("Low chance that failure will be detected beforehand"), 
	Moderate("Moderate chance that failure will be detected beforehand"), 
	ModeratelyHigh("It is somewhat likely that a failure will be detected beforehand"), 
	High("High chance that failure will be detected beforehand"), 
	VeryHigh("Very high chance that failure will be detected beforehand"), 
	Certain("A failure will definitely be detected");
	// @formatter:on

	private String description;

	private Detection(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.mhaug.scsproject.model.FmecaEnum#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.mhaug.scsproject.model.FmecaEnum#getNumber()
	 */
	@SuppressWarnings("static-access")
	@Override
	public int getNumber() {
		return this.values().length - this.ordinal();
	}
}
