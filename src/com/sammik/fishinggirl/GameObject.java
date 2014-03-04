package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObject extends Sprite {
	public GameObject(final Texture texture, final float x, final float y) {
		super(texture);
		setPosition(x, y);
	}
	
	public GameObject(final TextureRegion region, final float x, final float y) {
		super(region);
		setPosition(x, y);
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
