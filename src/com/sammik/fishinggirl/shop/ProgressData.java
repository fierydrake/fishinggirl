package com.sammik.fishinggirl.shop;

public class ProgressData {
	private int numOfBombLures;
	private RodLevel rodLevel;
	private LureLevel lureLevel;
	public enum RodLevel {
		BRONZE, SILVER, GOLD, LEGENDARY
	}
	
	public enum LureLevel {
		SMALL, MEDIUM, LARGE
	}
	
	public ProgressData() {
		numOfBombLures = 0;
		
		rodLevel = RodLevel.BRONZE;
		lureLevel = LureLevel.SMALL;
	}
	
	public RodLevel getRodLevel() {
		return this.rodLevel;
	}
	
	public void setRodLevel(RodLevel level) {
		this.rodLevel = level;
	}
	
	public LureLevel getLureLevel() {
		return this.lureLevel;
	}
	
	public void setLureLevel(LureLevel level) {
		this.lureLevel = level;
	}
}
