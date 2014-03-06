package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;

public class Fish extends GameObject{
	
	private int x = 0;
	private int y = 0;

	public Fish(FishingGirlGame game, Texture texture, float x, float y) {
		super(game, texture, x, y);
	}
	
	public void update(){
		/*if(x < super.game.getCliff().getRight()){
			
			 x += 1;
			 this.setPosition(x, y);
		
		 }
		
		
		 else{
			 x += 1;
			 this.setPositionByOrigin(x, y);
		 }
		 
		 */
	}

}
