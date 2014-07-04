package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MediumFish extends Fish {
	private float mouthSize = 14f;
	private Collider seeCollider = new Collider(this, 1, 1, 2, 2);
	
	public MediumFish(FishingGirlGame game, final Texture texture, final float x, float y){
		super(game, texture, x, y, getMouthPositionForTexture(game, texture));
		speed = 45.0f;
	}
	
	@Override
	protected Collider getEatCollider() {
		return new Collider(this, mouthPosition.x - mouthSize / 2f, mouthPosition.y - mouthSize / 2f, mouthSize, mouthSize);
	}
	
	@Override
	protected Collider getSeeCollider() {
		return seeCollider;
	}
	
	private static Vector2 getMouthPositionForTexture(FishingGirlGame game, Texture texture) {
		Vector2 vec;
		if(texture == game.assets.texture("mediumFish1")) {
			vec = calculateRelativeMouthPosFromAbsValues(12, 25, texture);
		} else if(texture == game.assets.texture("mediumFish2")) {
			vec = calculateRelativeMouthPosFromAbsValues(11, 31, texture);
		} else if(texture == game.assets.texture("mediumFish3")) {
			vec = calculateRelativeMouthPosFromAbsValues(7, 23, texture);			
		} else {
			vec = calculateRelativeMouthPosFromAbsValues(11, 26, texture);
		}
		return vec;
	}
	
	private static Vector2 calculateRelativeMouthPosFromAbsValues(float px, float py, Texture texture) {
		float x = -(texture.getWidth() / 2f - px);
		float y = -(py - texture.getHeight() / 2f);
		return new Vector2(x, y);
	}
}
