package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;

public class MediumFish extends Fish{

	public MediumFish(FishingGirlGame game, final Texture texture, final float x, float y){
		super(game, texture, x, y);
		speed = 45.0f;
	}
	
}
