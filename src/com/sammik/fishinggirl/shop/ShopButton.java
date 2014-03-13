package com.sammik.fishinggirl.shop;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sammik.fishinggirl.FishingGirlGame;
import com.sammik.fishinggirl.GameObject;

public class ShopButton extends GameObject {
	boolean isShopActive = false;
	Shop shop;
	public ShopButton(FishingGirlGame game, Texture texture, float x, float y, Shop shop) {
		super(game, texture, x, y);
		this.shop = shop;
//		shop.setPosition(x + this.getWidth() / 2 - shop.getWidth() / 2, y + this.getHeight() + 50);
	}
	
	public void setShopActive(boolean b) {
		isShopActive = b;
		this.shop.setX(getX() + getWidth() / 2 - shop.getWidth() / 2);
		this.shop.setY(getY() - shop.getHeight() / 2 - 50);
	}
	
	public boolean getShopActive() {
		return this.isShopActive;
	}

	public void update(List<GameObject> foregroundLayer) {
		if(isShopActive) {
//			foregroundLayer.add(this.shop);
//			System.out.println(shop.getX() + ", " + shop.getY());
		}

	}
	
}
