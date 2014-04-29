package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Lure extends GameObject{
	public enum LureSize { 
		SMALL, MEDIUM, LARGE;

		public LureSize next() {
			return values()[ordinal()+1];
		} 
	}
	
	private float velX, velY, forceX, forceY;
	private LureSize lureSize;
	private boolean onScreen;
	private boolean isAttached, isCasting, isSubmerged, isTouchingCliff;
	private float pullAmount;
	private final float size = 50f;
	private Collider collider = new Collider(this, -size/2f, -size/2f, size, size);
	
	static boolean debug = true;
	
	float absX;
	float absY;
	float r;
	
	float aRad = 0;
	

	private final Texture[] lureTextures = new Texture[] {
		game.assets.texture("smallLure"),
		game.assets.texture("mediumLure"),
		game.assets.texture("largeLure"),
	};
	private FishingRod fishingRod;
	private float waterLevel = game.getWater().getTop();
	
	
	public Lure(final FishingGirlGame game, FishingRod fishingRod, LureSize lureSize) {
		super(game, game.assets.texture("smallLure"), fishingRod.getEndX(), fishingRod.getEndY(), game.assets.texture("smallLure").getWidth() / 2f, game.assets.texture("smallLure").getHeight() / 2f);
		this.fishingRod = fishingRod;
		onScreen = true;
		isAttached = true;
		isCasting = false;
		this.lureSize = lureSize;
		pullAmount = 0;
	}
	
	public void update() {
		System.out.println(isAttached);
		for(int i = 0; i < game.getFishies().size(); i++) {
			if(Collider.isColliding(this, game.getFishies().get(i))) {
				if(debug)	System.out.println("COLLIDING WITH FISH " + i + "!");
			}
		}
		
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
	
	public void debugDraw(ShapeRenderer shapeRenderer) {
		final Rectangle colliderBounds = collider.getWorldCollisionRectangle();
		shapeRenderer.setProjectionMatrix(game.camera.combined);
		shapeRenderer.begin(ShapeType.Rectangle);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(colliderBounds.x, colliderBounds.y, colliderBounds.width, colliderBounds.height);
		shapeRenderer.end();
	}
}
