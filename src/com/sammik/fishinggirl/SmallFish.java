package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SmallFish extends Fish{
	private float mouthSize = 7f;
	private RectangleCollider seeCollider = new RectangleCollider(this, 0, 0, 1, 1);
	
	public SmallFish(final FishingGirlGame game, final Texture texture, final float x, float y) {
		super(game, texture, x, y, getMouthPositionForTexture(game, texture));
		speed = 60.0f;
	}
	
	@Override
	protected RectangleCollider getEatCollider() {
		return new RectangleCollider(this, mouthPosition.x - mouthSize / 2f, mouthPosition.y - mouthSize / 2f, mouthSize, mouthSize);
	}
	
	@Override
	protected RectangleCollider getSeeCollider() {
		return seeCollider;
	}
	
	private static Vector2 getMouthPositionForTexture(FishingGirlGame game, Texture texture) {
		return calculateRelativeMouthPosFromAbsValues(5, 26, texture);
	}
	
	private static Vector2 calculateRelativeMouthPosFromAbsValues(float px, float py, Texture texture) {
		float x = -(texture.getWidth() / 2f - px);
		float y = -(py - texture.getHeight() / 2f);
		return new Vector2(x, y);
	}
	
}
