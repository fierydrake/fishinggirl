package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;

public class SmallFish extends Fish{
	
	public SmallFish(final FishingGirlGame game, final Texture texture, final float x, float y) {
		super(game, texture, x, y);
		speed = 60.0f;
	}
}
