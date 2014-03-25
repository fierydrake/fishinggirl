package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Fish extends GameObject{
	public enum Type {
		SMALL, MEDIUM, LARGE
	}
	private float speed = 30.0f;

	public Fish(FishingGirlGame game, Texture texture, float x, float y) {
		super(game, texture, x, y);
	}
	
	public void update(){
		if (super.getRight() <= game.getWater().getRight()){
			setX(getX() + Gdx.graphics.getDeltaTime() * speed);
		}
		else if(super.getLeft() >= game.getCliff().getRight()){
			setX(getX() - Gdx.graphics.getDeltaTime() * speed);
		}
	}
	
	public static Type randomType() {
		final double random = Math.random();
		if (random < 0.1) return Type.LARGE;
		if (random < 0.3) return Type.MEDIUM;
		return Type.SMALL;
	}
	
	public static Fish randomFish(final FishingGirlGame game, final float minX, final float maxX, final float minY, final float maxY) {
		final Type type = randomType();
		final Fish fish = fish(game, type, minX, minY);
		final float x = Util.randomBetween(minX, maxX-fish.getWidth()), y = Util.randomBetween(minY, maxY-fish.getHeight());
		fish.setPosition(x, y);
		return fish;
	}
	
	public static Fish fish(FishingGirlGame game, Type type, final float x, final float y) {
		switch (type) {
		case SMALL: return new SmallFish(game, game.assets.randomTextureStartingWith("smallFish"), x, y);
		case MEDIUM: return new MediumFish(game, game.assets.randomTextureStartingWith("mediumFish"), x, y);
		case LARGE: return new LargeFish(game, game.assets.randomTextureStartingWith("largeFish"), x, y);
		default:
			return null;
		}
	}
}
