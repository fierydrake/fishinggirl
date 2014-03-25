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
		this.shop.setX(getX() + getWidth() / 2 - shop.getWidth() / 2 - 10);
		this.shop.setY(getY() + 50);
	}
	
	public void setShopActive(boolean b) {
		isShopActive = b;
		game.addToForegroundLayer(this.shop);
		ShopItem currentItem = null;
		for(int i = 0; i < this.shop.getShopItems().size(); i++) {
			currentItem = this.shop.getShopItems().get(i);
			Vector2 vec = this.shop.calculatePosInShop(i, 2, 2);
			currentItem.setPosition(vec.x, vec.y);
			game.addToForegroundLayer(currentItem);
		}
	}
	
	public boolean getShopActive() {
		return this.isShopActive;
	}

	public void update() {
		if(isShopActive) {
//			game.spawn(this.shop);
		}
	}
}
