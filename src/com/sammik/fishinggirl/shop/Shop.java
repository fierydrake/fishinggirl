package com.sammik.fishinggirl.shop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sammik.fishinggirl.FishingGirlGame;
import com.sammik.fishinggirl.GameObject;

public class Shop extends GameObject{
	private boolean showing = false;
	private List<ShopItem> shopItems = new ArrayList<ShopItem>();
	
	public Shop(FishingGirlGame game, List<ShopItem> shopItems, Texture texture, float x, float y) {
		super(game, texture, x, y);
		this.shopItems = shopItems;
	}
	
	public void printShopItems() {
		for(ShopItem shopItem : shopItems) {
			System.out.println("Item: " + shopItem.getDescription() + ". Price: " + shopItem.getPrice());
		}
	}
}
