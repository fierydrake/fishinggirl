package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MediumFish extends Fish{

	public MediumFish(FishingGirlGame fgg,final float x, float y){
		
		super(fgg,new Texture(Gdx.files.local("fishingGirl/fishMedium1.png")), x, y);
	
	}
	
	public void update(){
		super.update();
	}
	
}
