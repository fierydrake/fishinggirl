package com.sammik.fishinggirl.shop;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.sammik.fishinggirl.FishingGirlGame;
import com.sammik.fishinggirl.GameObject;

public class ShopItem extends GameObject {
	private int price, ID;
	private String name;
	private Texture texture;
	private String description;
	
	public ShopItem(FishingGirlGame game, Texture texture, int item, float x, float y) {
		super(game, texture, x, y);
		this.name = ShopConfig.getName(item);
		this.price = ShopConfig.getPrice(item);
		this.description = ShopConfig.getDescription(item);
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public String getName() {
		return this.name;
	}
}
