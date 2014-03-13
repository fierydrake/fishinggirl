package com.sammik.fishinggirl.shop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sammik.fishinggirl.FishingGirlGame;
import com.sammik.fishinggirl.GameObject;

public class Shop extends GameObject{
	private boolean showing = false;
	public Shop(FishingGirlGame game, Texture texture, float x, float y) {
		super(game, texture, x, y);
	}
	
	public void draw(SpriteBatch batch) {
		if(showing) {
			super.draw(batch);
		}
	}
}
