package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FishingRod {
	private int x, y;
	private Sprite fishingRodSprite;
	private Texture fishingRodTexture;
	
	private boolean pulling;
	

	public FishingRod() {
		x = 0;
		y = 0;
		
		Texture.setEnforcePotImages(false);
		fishingRodTexture = new Texture(Gdx.files.local("fishingGirl/fishingRod1.png"));
		fishingRodTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
		TextureRegion region = new TextureRegion(fishingRodTexture, 0, 0, 197, 15);
		
		fishingRodSprite = new Sprite(region);
		fishingRodSprite.setOrigin(fishingRodSprite.getWidth()/2, fishingRodSprite.getHeight()/2);
		fishingRodSprite.setScale(0.8f);
		fishingRodSprite.setPosition(x, y);
	}
	
	public void update() {
		fishingRodSprite.setPosition(x, y);
	}
	
	public void draw(SpriteBatch batch) {
		fishingRodSprite.draw(batch);
	}
	
	public boolean isPulling() {
		return pulling;
	}
	
//	public void pullBack() {
//		fishingRodSprite.
//	}
}
