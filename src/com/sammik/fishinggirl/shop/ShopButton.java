package com.sammik.fishinggirl.shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sammik.fishinggirl.FishingGirlGame;
import com.sammik.fishinggirl.GameObject;

public class ShopButton extends GameObject {
	boolean isShopActive = false;
	Shop shop;
	public ShopButton(FishingGirlGame game, Texture texture, float x, float y, Shop shop) {
		super(game, texture, x, y);
		this.shop = shop;
	}
	
	public void click() {
		isShopActive = !isShopActive;
		if (isShopActive) {
			shop.open((getX() + getWidth() / 2 - shop.getWidth() / 2 - 10), (getY() + 50));
		} else {
			shop.close();
		}
	}
}
