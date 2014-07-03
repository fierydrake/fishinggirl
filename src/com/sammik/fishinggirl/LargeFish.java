package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class LargeFish extends Fish {
//	private Rectangle eatColliderBounds = new Rectangle(mouthPosition.x - 25, mouthPosition.y - 25, 25, 25);
//	private Rectangle seeColliderBounds = new Rectangle(mouthPosition.x, mouthPosition.y - 25, 200, 25);
//	private Collider eatCollider = new Collider(this, eatColliderBounds.x, eatColliderBounds.y, eatColliderBounds.width, eatColliderBounds.height);
//	private Collider seeCollider = new Collider(this, seeColliderBounds.x, seeColliderBounds.y, seeColliderBounds.width, seeColliderBounds.height);
	
	private float mouthSize = 24f;
	private Collider eatCollider;
	private Collider seeCollider = new Collider(this, 1, 1, 2, 2);

	public LargeFish(final FishingGirlGame game, final Texture texture, final float x, float y){
		super(game, texture, x, y, new Vector2(-68f, -14f));
	}

	@Override
	protected Collider getEatCollider() {
		return new Collider(this, mouthPosition.x - mouthSize / 2f, mouthPosition.y - mouthSize / 2f, mouthSize, mouthSize);
	}
	
	@Override
	protected Collider getSeeCollider() {
		return seeCollider;
	}
	
}
