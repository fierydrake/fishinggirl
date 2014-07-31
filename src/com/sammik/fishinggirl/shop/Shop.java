package com.sammik.fishinggirl.shop;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sammik.fishinggirl.RectangleCollider;
import com.sammik.fishinggirl.FishingGirlGame;
import com.sammik.fishinggirl.GameObject;
import com.sammik.fishinggirl.Lure.LureSize;

public class Shop extends GameObject {
	public enum Type { LURE_SHOP, ROD_SHOP }
	private Type type;
	
	Texture tooltipTexture;
	float padding = 2f;
	float showTooltipFor = 4f;
	float showTooltipTime = 0f;
	
	Texture purchaseTexture;
	boolean open = false;
	
	float bobSpeed = 2f;
	float bobAmplitude = 10f;
	float t = new Random().nextFloat() * (float)Math.PI;
	float initialY;
	
	RectangleCollider collider;

	private ShopItem activeItem = null;
	
	public Shop(FishingGirlGame game, Type type, Texture texture, Texture tipTexture, Texture purchaseTexture, float x, float y) {
		super(game, texture, x, y, texture.getWidth() / 2f, texture.getHeight() / 2f);

		this.tooltipTexture = tipTexture;
		this.purchaseTexture = purchaseTexture;
		collider = new RectangleCollider(this, -getWidth() / 2f, -getHeight() / 2f, getWidth(), getHeight());
		this.initialY = y;
		this.type = type;
	}
	
	@Override
	public void update() {
		t += bobSpeed * Gdx.graphics.getRawDeltaTime();
		if (t > 2f*Math.PI) { t -= 2f*Math.PI; }
		setYByOrigin(initialY + bobAmplitude * (float)Math.sin(t));
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		if (showTooltipTime > 0f) {
			batch.draw(tooltipTexture, getCenterX() - tooltipTexture.getWidth() / 2f, getTop() + padding);
			showTooltipTime -= Gdx.graphics.getRawDeltaTime();
		}
		if (open) {
			float textureLeft = getCenterX() - purchaseTexture.getWidth() / 2f - 9f;
			float textureTop = getTop() + padding + purchaseTexture.getHeight();
			batch.draw(purchaseTexture, textureLeft, getTop() + padding);
			activeItem = new ShopItem(game, game.assets.texture("fishingRod"), ShopConfig.SILVER_ROD);
//			activeItem.setPositionByOrigin(getCenterX(), getTop() + purchaseTexture.getHeight() / 2f);
//			activeItem.draw(batch);
			batch.draw(activeItem, getCenterX() - activeItem.getWidth() / 2f - 9f, getTop() + (purchaseTexture.getHeight() - activeItem.getHeight()) / 2f);
			BitmapFont font = game.assets.font("dialog");
			font.setScale(1.6f, 1.6f);
			font.draw(batch, "You have found a new item!", textureLeft + 20f, textureTop - 20f);
			font.setScale(2.2f, 2.2f);
			font.draw(batch, "Medium Lure", textureLeft + 120f, textureTop - 90f);
			font.setScale(1.6f, 1.6f);
			font.draw(batch, "The Medium Lure lets you catch Medium Sized fish", textureLeft + 120f, textureTop - 130f);
			font.draw(batch, "Cost", textureLeft + 120f, textureTop - 180f);
		}
	}

	public RectangleCollider getCollider() { return collider; }
	
	public Rectangle getSpace() {
		float top = getTop() - 64, bottom = getBottom() + 95;
		float left = getLeft();
		return new Rectangle(left, bottom, getWidth(), top - bottom);
	}
	
	public void click(Vector2 vec) {
		showTooltipTime = showTooltipFor;
	}


// TODO
//	shopItems.add(new ShopItem(this, assets.texture("fishingRod"), ShopConfig.SILVER_ROD, x, y));
//	shopItems.add(new ShopItem(this, assets.texture(""), ShopConfig.GOLD_ROD, x, y));
//	shopItems.add(new ShopItem(this, assets.texture(""), ShopConfig.LEGENDARY_ROD, x, y));
//	shopItems.add(new ShopItem(this, assets.texture("mediumLure"), ShopConfig.MEDIUM_LURE, x, y));
//	shopItems.add(new ShopItem(this, assets.texture("largeLure"), ShopConfig.LARGE_LURE, x, y));
//	shopItems.add(new ShopItem(this, assets.texture("bombLure"), ShopConfig.BOMB_LURE, x, y));
	
	private ShopItem buildLure(LureSize lureSize) {
		switch (lureSize) {
		case MEDIUM: return new ShopItem(game, game.assets.texture("mediumLure"), ShopConfig.MEDIUM_LURE);
		case LARGE: return new ShopItem(game, game.assets.texture("largeLure"), ShopConfig.MEDIUM_LURE);
		default:
			throw new RuntimeException("Should not happen!");
		}
	}
	
	private boolean calcShopItem() {
		switch (type) {
		case LURE_SHOP:
			LureSize lureSize = game.getPlayer().getLureSize();
			if (lureSize == LureSize.LARGE) return false;
			activeItem = buildLure(lureSize.next());
			return true;
		case ROD_SHOP:
			/* TODO */
			return false;
		default:
			throw new RuntimeException("Should not happen!");
		}
	}
	
	public void open() {
		open = true;
		if (calcShopItem() /*&& game.getPlayer().canAfford(activeItem)*/) {
			open = true;
			Rectangle space = getSpace();
			activeItem.setPosition(space.x + 10, space.y + (space.height - activeItem.getHeight()) / 2f);
		}
	}
	
	public void close() {
		open = false;
		activeItem = null;
	}
	
	@Override
	public void drawLines(ShapeRenderer lineRenderer) {
		if(activeItem != null) {
			lineRenderer.setColor(Color.YELLOW);
			lineRenderer.rect(activeItem.getLeft(), activeItem.getBottom(), activeItem.getWidth(), activeItem.getHeight());
		}
	}
	
	@Override
	public void debugDraw(ShapeRenderer lineRenderer) {
		super.debugDraw(lineRenderer);
		
		final Polygon colliderPolygon = collider.getWorldCollisionPolygon();
		lineRenderer.setColor(Color.GREEN);
		lineRenderer.polygon(colliderPolygon.getVertices());
	}
}
