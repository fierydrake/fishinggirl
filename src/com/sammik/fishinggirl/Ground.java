package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ground {
	private Sprite sprite;
	
	public Ground(Texture texture) {
		sprite = new Sprite(texture);
		sprite.setPosition(0, 0);
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
