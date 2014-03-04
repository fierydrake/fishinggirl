package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Lure {
	private float x, y;
	private float velX, velY, forceX, forceY;
	private Sprite lureSprite;
	private Texture lureTexture;
	private LureSize lureSize;
	private boolean onScreen;
	private boolean isAttached, isCasting, isSubmerged;
	private float pullAmount;
	
	float absX;
	float absY;
	float r;
	
	float aRad = 0;
	
	enum LureSize {
		SMALL, MEDIUM, LARGE
	}
	
	private FishingRod fishingRod;
	private int waterLevel = Gdx.graphics.getHeight() - 500;
	
	
	public Lure(FishingRod fishingRod) {
		this.onScreen = true;
		this.isAttached = true;
		this.isCasting = false;
		lureSize = LureSize.SMALL;
		this.fishingRod = fishingRod;
		
		this.x = fishingRod.getX();
		this.y = fishingRod.getY(); 
		
		this.pullAmount = 0;
		
		if(lureSize == LureSize.SMALL) {
			lureTexture = new Texture(Gdx.files.local("fishingGirl/lureSmall.png"));
			lureTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			TextureRegion region = new TextureRegion(lureTexture, 0, 0, 31, 31);
			lureSprite = new Sprite(region);
		}
		lureSprite.setOrigin(lureSprite.getWidth()/2f, lureSprite.getHeight()/2f);
	}
	
	public void update() {
		
		double xDiff = 0;
		double yDiff = 0;
		float centreX = fishingRod.getEndX(), centreY = fishingRod.getEndY();
		
		if(getY() < waterLevel && isSubmerged == false && !isAttached) {
			xDiff = (x + getWidth() / 2) - fishingRod.getEndX();
			yDiff = (y + getHeight() / 2) - fishingRod.getEndY();
			aRad = (float) Math.atan2(yDiff, xDiff);
			
			absX = centreX - (x + getWidth() / 2);
			absY = centreY - (y + getHeight() / 2);
			r = (float) Math.sqrt((absX * absX) + (absY * absY));
			
			isSubmerged = true;
			isCasting = false;
			System.out.println("submerged!!");
		} else if(getY() > waterLevel && isSubmerged == true) {
			onScreen = false;
		}
		
		if(isAttached()) {
			this.velX = 0;
			this.velY = 0;
			this.x = fishingRod.getEndX();
			this.y = fishingRod.getEndY();
			onScreen = true;
		} else if(isSubmerged) {
			isCasting = false;
			
			
			aRad -= 0.011;
			
			r -= pullAmount;
			System.out.println("R: " + r);
			this.velX = ((float) (centreX + Math.cos(aRad) * r) - x);
			this.velY = ((float) (centreY + Math.sin(aRad) * r) - y);
			
			onScreen = true;
		}
		
		
		if(!isSubmerged)ApplyGravity();
		
		if(x > Gdx.graphics.getWidth() || y > Gdx.graphics.getHeight() || x < 0 || y < 0) {
			System.out.println("OFFSCREEN");
			velX = 0;
			velY = 0;
			isSubmerged = false;
			isAttached = true;
			isCasting = false;
			onScreen = false;
		} 
		
		
		this.x += velX * Gdx.graphics.getDeltaTime();
		this.y += velY * Gdx.graphics.getDeltaTime();
		
		lureSprite.setPosition(x, y);
	}
	
	
	
	public void Cast(int force) {
		this.velX = force * 5;
		this.velY = 0;
		isCasting = true;
		isAttached = false;
	}
	
	public boolean isAttached() {
		return this.isAttached;
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
		this.velY = velY - 5;
	}

	public void setAttached(boolean b) {
		this.isAttached = b;
	}

	public float getWidth() {
		return lureSprite.getWidth();
	}
	
	public float getHeight() {
		return lureSprite.getHeight();
	}
	
	public boolean isSubmerged() {
		return isSubmerged;
	}
	
	public void setPullAmount(float amount) {
		this.pullAmount = amount;
	}
}
