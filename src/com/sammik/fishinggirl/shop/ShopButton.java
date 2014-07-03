package com.sammik.fishinggirl.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.sammik.fishinggirl.FishingGirlGame;
import com.sammik.fishinggirl.GameObject;

public class ShopButton extends GameObject {
	boolean isShopActive = false;
	float t = 0f;
	float bobSpeed = 2f;
	float bobAmplitude = 10f;
	float initialY;
	Shop shop;
	public ShopButton(FishingGirlGame game, Texture texture, float x, float y, Shop shop) {
		super(game, texture, x, y, texture.getWidth() / 2f, texture.getHeight() / 2f);
		this.initialY = y;
		this.shop = shop;
	}
	
	@Override
	public void update() {
		t += bobSpeed * Gdx.graphics.getRawDeltaTime();
		if (t > 2f*Math.PI) { t -= 2f*Math.PI; }
		setYByOrigin(initialY + bobAmplitude * (float)Math.sin(t));
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
