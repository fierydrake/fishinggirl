package com.sammik.fishinggirl;

import samsstuff.ConfigData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Lure extends GameObject{
	private float velX, velY, forceX, forceY;
	private LureSize lureSize;
	private boolean onScreen;
	private boolean isAttached, isCasting, isSubmerged, isTouchingCliff;
	private float pullAmount;
	
	static boolean debug = false;
	
	float absX;
	float absY;
	float r;
	
	float aRad = 0;
	
	enum LureSize {
		SMALL, MEDIUM, LARGE
	}

	private final Texture[] lureTextures = new Texture[] {
		game.assets.texture("smallLure"),
		game.assets.texture("mediumLure"),
		game.assets.texture("largeLure"),
	};
	private FishingRod fishingRod;
	private float waterLevel = game.getWater().getTop();
	
	
	public Lure(final FishingGirlGame game, FishingRod fishingRod, LureSize size) {
		super(game, game.assets.texture("smallLure"), fishingRod.getEndX(), fishingRod.getEndY(), game.assets.texture("smallLure").getWidth() / 2f, game.assets.texture("smallLure").getHeight() / 2f);
		this.onScreen = true;
		this.isAttached = true;
		this.isCasting = false;
		lureSize = size;
		this.fishingRod = fishingRod;

		this.pullAmount = 0;
		
//		if(lureSize == LureSize.SMALL) {
//			lureTexture = new Texture(Gdx.files.local("fishingGirl/lureSmall.png"));
//		}
	}
	
	public void update() {
		double xDiff = 0;
		double yDiff = 0;
		float centreX = fishingRod.getEndX(), centreY = fishingRod.getEndY();
		
		if(getY() < waterLevel && isSubmerged == false && !isAttached) {
			xDiff = (getX() + getWidth() / 2) - fishingRod.getEndX();
			yDiff = (getY() + getHeight() / 2) - fishingRod.getEndY();
			aRad = (float) Math.atan2(yDiff, xDiff);
			
			absX = centreX - (getX() + getWidth() / 2);
			absY = centreY - (getY() + getHeight() / 2);
			r = (float) Math.sqrt((absX * absX) + (absY * absY));
			
			isSubmerged = true;
			isCasting = false;
			if(debug) System.out.println("submerged!!");
		} else if(getY() > waterLevel && isSubmerged == true) {
			isTouchingCliff = false;
			isSubmerged = false;
			isAttached = true;
			isCasting = false;
			onScreen = false;
		}
		
		if(isAttached()) {
			this.velX = 0;
			this.velY = 0;
			setPosition(fishingRod.getEndX(), fishingRod.getEndY());
			onScreen = true;
		} else if(isSubmerged && !isTouchingCliff) {
			isCasting = false;
			aRad -= 0.011;
			r -= pullAmount;
			
			this.velX = ((float) (centreX + Math.cos(aRad) * r) - getX());
			this.velY = ((float) (centreY + Math.sin(aRad) * r) - getY());
			
			onScreen = true;
		} 
		
		else if(isTouchingCliff) {
			r -= pullAmount;
			this.velX = ((float) (centreX + Math.cos(aRad) * r) - getX());
			this.velY = ((float) (centreY + Math.sin(aRad) * r) - getY());
		}
		
		
		if(!isSubmerged)	ApplyGravity();
		else if(getLeft() < game.getCliff().getRight() - 50 && !isTouchingCliff) {
			isTouchingCliff = true;
			velX = 0; velY = 0;
			
			absX = centreX - (getX() + getWidth() / 2);
			absY = centreY - (getY() + getHeight() / 2);
			r = (float) Math.sqrt((absX * absX) + (absY * absY));
			
//			xDiff = (getX() + getWidth() / 2) - fishingRod.getEndX();
//			yDiff = (getY() + getHeight() / 2) - fishingRod.getEndY();
//			aRad = (float) Math.atan2(yDiff, xDiff);
			aRad += 0.99;
			
			setPosition(game.getCliff().getRight() - 50, getY());
		}
		
		if(getX() > FishingGirlGame.WORLD_WIDTH || getY() > FishingGirlGame.WORLD_HEIGHT || getX() < 0 || getY() < 0) {
			velX = 0;
			velY = 0;
			isTouchingCliff = false;
			isSubmerged = false;
			isAttached = true;
			isCasting = false;
			onScreen = false;
		} 
		setPosition(getX() + velX * Gdx.graphics.getDeltaTime(), getY() + velY * Gdx.graphics.getDeltaTime());
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

	public boolean isOnScreen() {
		return this.onScreen;
	}
	
	public void ApplyGravity() {
		this.velY = velY - 5;
	}

	public void setAttached(boolean b) {
		this.isAttached = b;
	}
	
	public boolean isSubmerged() {
		return isSubmerged;
	}
	
	public void setPullAmount(float amount) {
		this.pullAmount = amount;
	}
}
