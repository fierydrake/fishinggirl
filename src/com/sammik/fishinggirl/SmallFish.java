package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SmallFish extends Fish{
	private Rectangle eatColliderBounds = new Rectangle(mouthPosition.x, mouthPosition.y, 50, 50);
	private Rectangle seeColliderBounds = new Rectangle(mouthPosition.x, mouthPosition.y, 200, 50);
	private Collider eatCollider = new Collider(this, eatColliderBounds.x, eatColliderBounds.y, eatColliderBounds.width, eatColliderBounds.height);
	private Collider seeCollider = new Collider(this, seeColliderBounds.x, seeColliderBounds.y, seeColliderBounds.width, seeColliderBounds.height);
	
	public SmallFish(final FishingGirlGame game, final Texture texture, final float x, float y) {
		super(game, texture, x, y, new Vector2(10, 0));
		speed = 60.0f;
	}
	
	@Override
	protected Collider getEatCollider() {
		return eatCollider;
	}
	
	@Override
	protected Collider getSeeCollider() {
		return seeCollider;
	}
}
