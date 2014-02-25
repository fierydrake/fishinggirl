package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Water {
	private Sprite sprite;
	
	public Water(final Texture texture, final float x, final float y) {
		sprite = new Sprite(texture);
		sprite.setPosition(x, y);
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	public Rectangle getBounds() { return sprite.getBoundingRectangle(); }

	public float getRight() {
		final Rectangle bounds = sprite.getBoundingRectangle();
		return bounds.x + bounds.width;
	}

	public float getTop() {
		final Rectangle bounds = sprite.getBoundingRectangle();
		return bounds.y + bounds.height;
	}
}
