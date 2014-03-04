package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MediumFish extends Fish {

	public MediumFish(final float x, float y){
		
		super(new Texture(Gdx.files.local("fishingGirl/fishMedium1.png")), x, y);
	
	}
	
}
