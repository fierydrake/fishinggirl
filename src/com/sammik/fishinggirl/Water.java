package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Water extends GameObject {
	/* 
	 * These fields define the extent of the water separately from the sprite,
	 * so that the sprite can be repeated to fill the area. Couldn't find a
	 * way (that I could understand) to do this with libgdx Texture/Sprite APIs.
	 */
	final float widthExtent;
	final float heightExtent;
	public Water(final FishingGirlGame game, final Texture texture, final float x, final float y, final float w, final float h) {
		super(game, texture, x, y);
		widthExtent = w;
		heightExtent = h;
	}

	public float getWidth() { return widthExtent; }
	public float getHeight() { return heightExtent; }
	public Rectangle getBoundingRectangle() {
		return new Rectangle(getX(), getY(), widthExtent, heightExtent);
	}
	
	public float getWaterLine() { return getTop() - 20f; }
	
	public void debugDraw(ShapeRenderer lineRenderer) {
		lineRenderer.setColor(Color.BLUE);
		lineRenderer.line(getLeft(), getWaterLine(),  getRight(), getWaterLine());
	}
	
	@Override
	public void draw(Batch batch) {
		final float w = super.getWidth() - 1f, h = super.getHeight();
		if (w == 0f || h == 0f) return;
		if (w == widthExtent && h == heightExtent) {
			super.draw(batch);
		} else {
			float offsetX = 0f, offsetY = 0f;
			float remainingWidth = widthExtent, remainingHeight = heightExtent;
			while (remainingHeight > 0) {
				// Tile
				float drawWidth = Math.min(remainingWidth, w);
				float drawHeight = Math.min(remainingHeight, h);
				batch.draw(getTexture(), getX() + offsetX, getY() + offsetY, 0, 0, (int)drawWidth, (int)drawHeight);
				remainingWidth -= drawWidth;
				offsetX += drawWidth;
				if (remainingWidth <= 0f) {
					// reset to left
					remainingWidth = widthExtent;
					offsetX = 0f;
					// move down
					remainingHeight -= drawHeight; 
					offsetY += drawHeight;
				}
			}
		}
	}
}
