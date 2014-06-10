package com.sammik.fishinggirl;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Fish extends GameObject{
	public enum Type {
		SMALL, MEDIUM, LARGE
	}
	private Vector2 direction;
	protected Vector2 mouthPosition;
	protected float speed = 30.0f;

	public Fish(FishingGirlGame game, Texture texture, float x, float y) {
		super(game, texture, x, y);
		direction = new Vector2(((new Random().nextInt(2) == 1) ? -1 : 1), 0);
		mouthPosition = new Vector2(getLeft(), getCenterY());
		if(direction.x > 0) { 
			flip(true, false);
			mouthPosition = new Vector2(getRight(), getCenterY());
		}
	}
	
	protected abstract Collider getEatCollider();
	protected abstract Collider getSeeCollider();
	
	public void update(){
		float newX = getX() + direction.x * Gdx.graphics.getDeltaTime() * speed;
		if (newX < game.getCliff().getRight() || newX > game.getWater().getRight()) {
			direction.x *= -1;
			newX = getX() + direction.x * Gdx.graphics.getDeltaTime() * speed;
			flip(true, false);
		}
		setPosition(newX, getY() + direction.y * Gdx.graphics.getDeltaTime() * speed);
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
	
	public void debugDraw(ShapeRenderer shapeRenderer) {
		final Rectangle[] collidersToDraw = new Rectangle[] {
				getEatCollider().getWorldCollisionRectangle(),
				getSeeCollider().getWorldCollisionRectangle() };
		final Color[] colours = new Color[] { Color.GREEN, Color.BLUE };
		shapeRenderer.setProjectionMatrix(game.camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		for (int i=0; i< collidersToDraw.length; i++) {
			final Rectangle collider = collidersToDraw[i];
			shapeRenderer.setColor(colours[i]);
			shapeRenderer.rect(collider.x, collider.y, collider.width, collider.height);
		}
		shapeRenderer.end();
	}
}
