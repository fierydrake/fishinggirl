package com.sammik.fishinggirl.shop;

import java.util.HashMap;

public class ShopConfig {
	public static final int SILVER_ROD = 1, GOLD_ROD = 2, LEGENDARY_ROD = 3, MEDIUM_LURE = 4, LARGE_LURE = 5, BOMB_LURE = 6;
	
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
	
	public static int getPrice(int item) {
		switch(item) {
		case SILVER_ROD:
			return 2000;
		case GOLD_ROD:
			return 4000;
		case LEGENDARY_ROD:
			return 6000;
		case MEDIUM_LURE:
			return 1500;
		case LARGE_LURE:
			return 4500;
		case BOMB_LURE:
			return 500;
		default:
			return 0;
		}
	}
	
	public static String getName(int item) {
		switch(item) {
		case SILVER_ROD:
			return "Silver Rod";
		case GOLD_ROD:
			return "Gold Rod";
		case LEGENDARY_ROD:
			return "Legendary Rod";
		case MEDIUM_LURE:
			return "Medium Lure";
		case LARGE_LURE:
			return "Large Lure";
		case BOMB_LURE:
			return "Bomb Lure";
		default:
			return "None";
		}
	}
}
