package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fish extends GameObject{
	
	private float x = 0.0f;
	private float y = 0.0f;
	private float speed = 30.0f;

	public Fish(FishingGirlGame game, Texture texture, float x, float y) {
		super(game, texture, x, y);
		
		this.x = x;
		this.y = y;
	}
	
	public void update(){
		
		if (super.getRight() <= game.getWater().getRight()){
			x += Gdx.graphics.getDeltaTime() * speed;
		}
		else if(super.getLeft() >= game.getCliff().getRight()){
			x -= Gdx.graphics.getDeltaTime() * speed;
		}
		
		setPosition(x, y);
	}
	
	public void draw(SpriteBatch batch){
		super.draw(batch);
	}

}
