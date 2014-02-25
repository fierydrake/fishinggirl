package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Lure {
	private float x, y;
	private float velX, velY;
	private Sprite lureSprite;
	private Texture lureTexture;
	private LureSize lureSize;
	private boolean onScreen;
	
	enum LureSize {
		SMALL, MEDIUM, LARGE
	}
	
	private FishingRod fishingRod;
	
	
	public Lure(FishingRod fishingRod, int force) {
		this.onScreen = true;
		lureSize = LureSize.SMALL;
		this.fishingRod = fishingRod;
		this.velX = force / 10;
		this.velY = -1 * force;
		
		this.x = fishingRod.getX();
		this.y = fishingRod.getY(); 
		
		if(lureSize == LureSize.SMALL) {
			lureTexture = new Texture(Gdx.files.local("fishingGirl/lureSmall.png"));
			lureTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			TextureRegion region = new TextureRegion(lureTexture, 0, 0, 31, 31);
			lureSprite = new Sprite(region);
		}
	}
	
	public void update() {
		ApplyGravity();
		if(x > Gdx.graphics.getWidth() || y > Gdx.graphics.getHeight()) {
			System.out.println("OFFSCREEN");
			onScreen = false;
		}
		
		System.out.println(x + ", " + y);
		
		this.x += velX;
		this.y += velY;
		
		lureSprite.setPosition(x, y);
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public boolean isOnScreen() {
		return this.onScreen;
	}
	
	public void draw(SpriteBatch batch) {
		lureSprite.draw(batch);
	}
	
	public void ApplyGravity() {
		this.velY = velY - 1 * Gdx.graphics.getDeltaTime();
	}
	
}
