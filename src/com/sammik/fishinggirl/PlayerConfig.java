package com.sammik.fishinggirl;


public class PlayerConfig {
	private int numOfBombLures;
	private RodLevel rodLevel;
	private LureLevel lureLevel;
	
	public PlayerConfig() {
		numOfBombLures = 0;
		
		rodLevel = RodLevel.BRONZE;
		lureLevel = LureLevel.SMALL;
	}
	
	public enum RodLevel {
		BRONZE, SILVER, GOLD, LEGENDARY
	}
	
	public enum LureLevel {
		SMALL, MEDIUM, LARGE
	}
	
	public RodLevel getRodLevel() {
		return this.rodLevel;
	}
	
	public void setRodLevel(RodLevel level) {
		rodLevel = level;
	}
	
	public LureLevel getLureLevel() {
		return this.lureLevel;
	}
	
	public void setLureLevel(LureLevel level) {
		this.lureLevel = level;
	}
}	
