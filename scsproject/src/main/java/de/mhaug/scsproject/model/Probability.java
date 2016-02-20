package de.mhaug.scsproject.model;

public enum Probability implements FmecaEnum {
	// @formatter:off
	VeryHigh("Failure almost inevitable",10), 
	High("Repeated failures",8), 
	Moderate("Occasional failures",6), 
	Low("Relatively few failures",3), 
	Remote("Failure is unlikely",1);
	// @formatter:on

	private String description;
	private int number;

	private Probability(String description, int number) {
		this.description = description;
		this.number = number;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getNumber() {
		return number;
	}
}
