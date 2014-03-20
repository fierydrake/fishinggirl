package com.sammik.fishinggirl.shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sammik.fishinggirl.FishingGirlGame;
import com.sammik.fishinggirl.GameObject;

public class Shop extends GameObject{
	private boolean showing = false;
	private List<ShopItem> items = new ArrayList<ShopItem>();
	private PurchaseButton = new PurchaseButton();
	public Shop(FishingGirlGame game, Texture texture, float x, float y) {
		super(game, texture, x, y);
	}
	
	

}
