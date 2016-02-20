package de.mhaug.scsproject.model;

/**
 * The criticality ranking for FMECA
 * 
 * @author Martin Haug
 */
public enum Criticality implements FmecaEnum {
// @formatter:off
	HazardousWithoutWarning("Potential failure poses high danger without prior warning"), 
	HazardousWithWarning("Potential failure poses high danger with prior warning"), 
	VeryHigh("Product is inoperable with loss of primary Function"), 
	High("Product is operable, but at reduced level of performance"), 
	Moderate("Product is operable, but comfort or convenience item(s) are inoperable"), 
	Low("Product is operable, but comfort or convenience item(s) operate at a reduced level of performance"), 
	VeryLow("Most customers notice some quirks"), 
	Minor("Average customers notice some quirks"), 
	VeryMinor("Very observant customers notice some quirks"), 
	None("No effect");
// @formatter:on

	private String description;

	private Criticality(String description) {
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
	@Override
	public int getNumber() {
		return this.values().length - this.ordinal();
	}
}