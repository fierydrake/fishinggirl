package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class GameObject extends Sprite {
	protected final FishingGirlGame game;
	protected float offsetX, offsetY;
	
	public GameObject(final FishingGirlGame game, final Texture texture, final float x, final float y) {
		super(texture);
		this.game = game;
		setPosition(x, y);
	}
	
	public GameObject(final FishingGirlGame game, final TextureRegion region, final float x, final float y) {
		super(region);
		this.game = game;
		setPosition(x, y);
	}
	
	public GameObject(final FishingGirlGame game, final Texture texture, final float x, final float y, final float offx, final float offy) {
		super(texture);
		this.game = game;
		offsetX = offx; offsetY = offy;
		setPosition(x, y);
	}
	
	public GameObject(final FishingGirlGame game, final TextureRegion region, final float x, final float y, final float offx, final float offy) {
		super(region);
		this.game = game;
		offsetX = offx; offsetY = offy;
		setPosition(x, y);
	}
	
	public float getAdjustedX() { return getX() + offsetX; }
	public float getAdjustedY() { return getY() + offsetY; }
	
	public float getLeft() {
		final Rectangle bounds = getBoundingRectangle();
		return bounds.x;
	}

	public void setPositionByOrigin(final float x, final float y) {
		setPosition(x - getOriginX(), y - getOriginY());
	}

	public float getByOriginX() { return getX() + getOriginX(); }
	public float getByOriginY() { return getY() + getOriginY(); }
	
	public float getRight() {
		final Rectangle bounds = getBoundingRectangle();
		return bounds.x + bounds.width;
	}

	public float getTop() {
		final Rectangle bounds = getBoundingRectangle();
		return bounds.y + bounds.height;
	}
	
	public float getBottom() {
		final Rectangle bounds = getBoundingRectangle();
		return bounds.y;
	}

	public float getCenterX() {
		final Rectangle bounds = getBoundingRectangle();
		return bounds.x + bounds.width / 2f;
	}
	
	public float getCenterY() {
		final Rectangle bounds = getBoundingRectangle();
		return bounds.y + bounds.height / 2f;
	}

	public void debugDraw(ShapeRenderer rectangleRenderer) {
	}

}
