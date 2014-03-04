package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObject extends Sprite {
	protected final FishingGirlGame game;
	
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
	
	public GameObject(final FishingGirlGame game, final Texture texture, final float x, final float y, final float ox, final float oy) {
		this(game, texture, x, y);
		setOrigin(ox, oy);
	}
	
	public GameObject(final FishingGirlGame game, final TextureRegion region, final float x, final float y, final float ox, final float oy) {
		this(game, region, x, y);
		setOrigin(ox, oy);
	}

	public float getRight() {
		final Rectangle bounds = getBoundingRectangle();
		return bounds.x + bounds.width;
	}

	public float getTop() {
		final Rectangle bounds = getBoundingRectangle();
		return bounds.y + bounds.height;
	}

}
