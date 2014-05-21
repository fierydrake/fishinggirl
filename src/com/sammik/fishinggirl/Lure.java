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
	
	private float velX, velY;
	private LureSize lureSize;
	private boolean onScreen;
	private boolean isAttached, isCasting, isSubmerged, isTouchingCliff;
	private float pullAmount;
	private final float size = 50f;
	private Collider collider = new Collider(this, -size/2f, -size/2f, size, size);
	
	static boolean debug = true;
	
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
	
	private void updateAttached() {
		velX = 0;
		velY = 0;
		setPositionByOrigin(fishingRod.getEndX(), fishingRod.getEndY());
		onScreen = true;
	}
	
	private void updateCasting() {
		ApplyGravity();
		setPosition(getX() + velX * Gdx.graphics.getDeltaTime(), getY() + velY * Gdx.graphics.getDeltaTime());
	}
	
	private void updateSubmerged() {
		final float centreX = fishingRod.getEndX(), centreY = fishingRod.getEndY();
		if(getLeft() < game.getCliff().getRight() && !isTouchingCliff) {
			isTouchingCliff = true;
		}
		aRad += (isTouchingCliff ? 0.011f : -0.011f);
		r -= pullAmount;
		final float x = (float) (centreX + Math.cos(aRad) * r);
		final float y = (float) (centreY + Math.sin(aRad) * r);
		setPositionByOrigin(x, y);
		onScreen = true;
	}

	private boolean isSubmerging() { return !isAttached && !isSubmerged && getY() <= waterLevel; }
	private boolean isSurfacing() { return !isAttached && isSubmerged && getY() > waterLevel; }

	private void submerge() {
		isCasting = false;
		isSubmerged = true;
		
		final float centreX = fishingRod.getEndX(), centreY = fishingRod.getEndY();
		final float dx = getByOriginX() - centreX;
		final float dy = getByOriginY() - centreY;
		r = (float) Math.sqrt((dx * dx) + (dy * dy));
		aRad = (float) Math.atan2(dy, dx);
	}
	
	private void surface() {
		isTouchingCliff = false;
		isSubmerged = false;
		isAttached = true;
		isCasting = false;
		onScreen = false;
	}
	
	public void update() {
		for(int i = 0; i < game.getFishies().size(); i++) {
			if(Collider.isColliding(this, game.getFishies().get(i))) {
				if(debug)	System.out.println("COLLIDING WITH FISH " + i + "!");
			}
		}
		
		// Transitions
		if (isSubmerging()) { submerge(); }
		else if (isSurfacing()) { surface(); }

		// States
		if (isAttached) updateAttached();
		else if (isCasting) updateCasting();
		else if (isSubmerged) updateSubmerged();
		else {
			//WHAT!!
			System.err.println("Got into an bad state (" + isAttached + ", " + isCasting + ", " + isSubmerged + ")");
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
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(colliderBounds.x, colliderBounds.y, colliderBounds.width, colliderBounds.height);
		shapeRenderer.end();
	}
}
