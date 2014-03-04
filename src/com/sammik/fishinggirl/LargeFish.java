package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class LargeFish extends Fish{

	public LargeFish(final float x, float y){
		
		super(new Texture(Gdx.files.local("fishingGirl/fishLarge1.png")), x, y);
	
	}
	
}
