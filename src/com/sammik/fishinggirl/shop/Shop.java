package com.sammik.fishinggirl.shop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sammik.fishinggirl.FishingGirlGame;
import com.sammik.fishinggirl.GameObject;
import com.sammik.fishinggirl.Lure.LureSize;

public class Shop extends GameObject{
	public enum Type { LURE_SHOP, ROD_SHOP }
	private Type type;
	
	private ShopItem activeItem = null;
	
	public Shop(FishingGirlGame game, Type type, Texture texture, float x, float y) {
		super(game, texture, x, y);
		this.type = type;
	}
	
	public Rectangle getSpace() {
		float top = getTop() - 64, bottom = getBottom() + 95;
		float left = getLeft();
		return new Rectangle(left, bottom, getWidth(), top - bottom);
	}
	
	public void click(Vector2 vec) {
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
	
	public void open(float f, float g) {
		System.out.println("Open!");
		if (calcShopItem() /*&& game.getPlayer().canAfford(activeItem)*/) {
			game.spawn(this);
			setPosition(f, g);
			Rectangle space = getSpace();
			activeItem.setPosition(space.x + 10, space.y + (space.height - activeItem.getHeight()) / 2f);
		}
	}
	
	public void close() {
		game.despawn(this);
		game.despawn(activeItem);
		activeItem = null;
	}
	
	public void drawLines(ShapeRenderer lineRenderer) {
		if(activeItem != null) {
			lineRenderer.setColor(Color.YELLOW);
			lineRenderer.rect(activeItem.getLeft(), activeItem.getBottom(), activeItem.getWidth(), activeItem.getHeight());
		}
	}
}