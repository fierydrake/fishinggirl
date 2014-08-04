package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sammik.fishinggirl.Lure.LureSize;

public class Hook extends GameObject{
	
	private Hookable hookedObject;

	private float velX, velY;
	private boolean onScreen;
	private boolean isAttached, isCasting, isSubmerged, isTouchingCliff;
	private float pullAmount;
	
	static boolean debug = false;
	
	private FishingRod fishingRod;
	private float waterLevel = game.getWater().getWaterLine();
	
	public Hook(final FishingGirlGame game, FishingRod fishingRod) {
		super(game, game.assets.texture("crapHook"), fishingRod.getEndX(), fishingRod.getEndY(), 0, 0);
		this.fishingRod = fishingRod;
		onScreen = true;
		isAttached = true;
		isCasting = false;
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
		// TODO do we still need cliff collision? currently drag is high enough the lure never reaches cliff
		float x = getByOriginX(), y = getByOriginY();
		float dx = x - fishingRod.getEndX();
		float dy = y - fishingRod.getEndY();
		Vector2 radiusAdjustment = new Vector2(dx, dy).nor().scl(pullAmount);
		Vector2 tangentUnit = new Vector2(dx, dy).rotate(-90).nor();
		Vector2 gravity = new Vector2(0, -98f);
		Vector2 v = new Vector2(velX, velY);
		float gravityT = gravity.dot(tangentUnit);
		float vT = v.dot(tangentUnit);
		float speed = vT + gravityT * Gdx.graphics.getDeltaTime();
		speed *= 0.99f; // drag?
		v = tangentUnit;
		v.scl(speed);
		velX = v.x; velY = v.y;
		setPositionByOrigin(x - radiusAdjustment.x + velX * Gdx.graphics.getDeltaTime(), y - radiusAdjustment.y + velY * Gdx.graphics.getDeltaTime());
		onScreen = true;
	}

	private boolean isSubmerging() { return !isAttached && !isSubmerged && getY() <= waterLevel; }
	private boolean isSurfacing() { return !isAttached && isSubmerged && getY() > waterLevel; }

	private void submerge() {
		isCasting = false;
		isSubmerged = true;
		velX = 0f; velY = 0f;
	}
	
	private void surface() {
		if (hookedObject instanceof Fish) {
			game.despawn((Fish)hookedObject);
			//score
			Lure lure = new Lure(game, fishingRod, LureSize.SMALL);
			game.spawn(lure);
			attach(lure);
		}
		isTouchingCliff = false;
		isSubmerged = false;
		isAttached = true;
		isCasting = false;
		onScreen = false;
	}
	
	public void update() {
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
		if (hookedObject != null) {
			hookedObject.update();
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
	
	public void cast(int force) {
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
	
	@Override
	public void draw(Batch batch) {
	}

	@Override
	public void debugDraw(ShapeRenderer lineRenderer) {
		super.debugDraw(lineRenderer);
	}

	public void attach(Hookable hookable) {
		detach();
		hookedObject = hookable;
		hookable.hook();
	}
	
	public void detach() {
		if (hookedObject != null) { 
			hookedObject.unhook(); 
			hookedObject = null;
		}
	}
	
}
