package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class LargeFish extends Fish {
	private Rectangle eatColliderBounds = new Rectangle(mouthPosition.x - 25, mouthPosition.y - 25, 25, 25);
	private Rectangle seeColliderBounds = new Rectangle(mouthPosition.x, mouthPosition.y - 25, 200, 25);
	private Collider eatCollider = new Collider(this, eatColliderBounds.x, eatColliderBounds.y, eatColliderBounds.width, eatColliderBounds.height);
	private Collider seeCollider = new Collider(this, seeColliderBounds.x, seeColliderBounds.y, seeColliderBounds.width, seeColliderBounds.height);
	
	public LargeFish(final FishingGirlGame game, final Texture texture, final float x, float y){
		super(game, texture, x, y);
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
