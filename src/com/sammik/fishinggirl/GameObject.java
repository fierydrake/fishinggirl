package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

// Simple wrapper for a Sprite
public class GameObject extends Sprite {
	public GameObject(final Texture texture, final float x, final float y) {
		super(texture);
		setPosition(x, y);
	}
	
	public GameObject(final TextureRegion region, final float x, final float y) {
		super(region);
		setPosition(x, y);
	}

	public void draw(final SpriteBatch batch) {
		super.draw(batch);
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
