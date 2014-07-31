package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class LargeFish extends Fish {
//	private Rectangle eatColliderBounds = new Rectangle(mouthPosition.x - 25, mouthPosition.y - 25, 25, 25);
//	private Rectangle seeColliderBounds = new Rectangle(mouthPosition.x, mouthPosition.y - 25, 200, 25);
//	private Collider eatCollider = new Collider(this, eatColliderBounds.x, eatColliderBounds.y, eatColliderBounds.width, eatColliderBounds.height);
//	private Collider seeCollider = new Collider(this, seeColliderBounds.x, seeColliderBounds.y, seeColliderBounds.width, seeColliderBounds.height);
	
	private float mouthSize = 24f;
	private RectangleCollider seeCollider = new RectangleCollider(this, 1, 1, 2, 2);

	public LargeFish(final FishingGirlGame game, final Texture texture, final float x, float y){
		super(game, texture, x, y, getMouthPositionForTexture(game, texture));
	}

	@Override
	protected RectangleCollider getEatCollider() {
		return new RectangleCollider(this, mouthPosition.x - mouthSize / 2f, mouthPosition.y - mouthSize / 2f, mouthSize, mouthSize);
	}
	
	@Override
	protected Collider getSeeCollider() {
		return new TriangleCollider(this, 0, 0, 300, 25, getDirection());
	}
	
	private static Vector2 getMouthPositionForTexture(FishingGirlGame game, Texture texture) {
		Vector2 vec;
		if(texture == game.assets.texture("largeFish1")) {
			vec = calculateRelativeMouthPosFromAbsValues(35, 60, texture);
		} else if(texture == game.assets.texture("largeFish2")) {
			vec = calculateRelativeMouthPosFromAbsValues(25, 70, texture);
		} else if(texture == game.assets.texture("largeFish3")) {
			vec = calculateRelativeMouthPosFromAbsValues(25, 60, texture);			
		} else {
			vec = calculateRelativeMouthPosFromAbsValues(27, 75, texture);
		}
		return vec;
	}
	
	private static Vector2 calculateRelativeMouthPosFromAbsValues(float px, float py, Texture texture) {
		float x = -(texture.getWidth() / 2f - px);
		float y = -(py - texture.getHeight() / 2f);
		return new Vector2(x, y);
	}
 	
}
