package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SmallFish extends Fish{
	
	public SmallFish(FishingGirlGame fgg,final float x, float y){
		
		super(fgg,new Texture(Gdx.files.local("fishingGirl/fishSmall1.png")), x, y);
	
	}

}
