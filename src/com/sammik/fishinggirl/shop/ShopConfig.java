package com.sammik.fishinggirl.shop;

import java.util.HashMap;

public class ShopConfig {
	private int numOfBombLures;
	private RodLevel rodLevel;
	private LureLevel lureLevel;
	
	public final int SILVER_ROD = 1, GOLD_ROD = 2, LEGENDARY_ROD = 3, MEDIUM_LURE = 4, LARGE_LURE = 5, BOMB_LURE = 6;
	
	public static String getDescription(int item) {
		switch(item) {
		case SILVER_ROD:
			return "Cast further";
		case GOLD_ROD:
			return "Cast even further";
		case LEGENDARY_ROD:
			return "Cast far and reel in heavy fish quickly.";
		case MEDIUM_LURE:
			return "Catch medium fish";
		case LARGE_LURE:
			return "Catch large fish";
		case BOMB_LURE:
			return "Explodes and kills the first fish that touches it. Even if it is a very large fish.";
		default:
			return "";
		}
	}
	
	public enum RodLevel {
		BRONZE, SILVER, GOLD, LEGENDARY
	}
	
	public enum LureLevel {
		SMALL, MEDIUM, LARGE
	}
	
	public ShopConfig() {
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
