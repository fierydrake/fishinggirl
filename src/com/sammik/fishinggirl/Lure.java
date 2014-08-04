package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Lure extends GameObject implements Hookable {
	public enum LureSize { 
		SMALL, MEDIUM, LARGE;

		public LureSize next() {
			return values()[ordinal()+1];
		} 
	}
	
	private final float size = 50f;
	private RectangleCollider collider = new RectangleCollider(this, -size/2f, -size/2f, size, size);
	
	static boolean debug = false;
	
	private final Texture[] lureTextures = new Texture[] {
		game.assets.texture("smallLure"),
		game.assets.texture("mediumLure"),
		game.assets.texture("largeLure"),
	};
	
	private FishingRod fishingRod;
	private float waterLevel = game.getWater().getWaterLine();
	
	
	public Lure(final FishingGirlGame game, FishingRod fishingRod, LureSize lureSize) {
		super(game, game.assets.texture("smallLure"), fishingRod.getEndX(), fishingRod.getEndY(), game.assets.texture("smallLure").getWidth() / 2f, game.assets.texture("smallLure").getHeight() / 2f);
		this.fishingRod = fishingRod;
	}
	
	
	public void update() {
		setPositionByOrigin(game.getHook().getByOriginX(), game.getHook().getByOriginY());

		// Collisions
		if (collider.isCollidingWith(game.getLureShop().getCollider())) {
			game.pushMessage("LURE SHOP");
		}
		if (collider.isCollidingWith(game.getRodShop().getCollider())) {
			game.getRodShop().open();
		}
		for(int i = 0; i < game.getFishies().size(); i++) {
			if(collider.isCollidingWith(game.getFishies().get(i).getEatCollider())) {
				game.getHook().attach(game.getFishies().get(i));
			} else 
			if(collider.isCollidingWith(game.getFishies().get(i).getSeeCollider())) {
				game.pushMessage("FISH SEE");
			}
		}
	}
	
	@Override
	public void debugDraw(ShapeRenderer lineRenderer) {
		super.debugDraw(lineRenderer);

		final Polygon colliderPolygon = collider.getWorldCollisionPolygon();
		lineRenderer.setColor(Color.GREEN);
		lineRenderer.polygon(colliderPolygon.getVertices());
	}

	@Override
	public void hook() {
	}

	@Override
	public void unhook() {
		game.despawn(this);
	}
}
