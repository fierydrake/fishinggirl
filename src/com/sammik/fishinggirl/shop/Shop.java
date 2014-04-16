package com.sammik.fishinggirl.shop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sammik.fishinggirl.FishingGirlGame;
import com.sammik.fishinggirl.GameObject;

public class Shop extends GameObject{
	private boolean showing = false;
	private List<ShopItem> shopItems = new ArrayList<ShopItem>();
	private ShopItem activeItem = null;
	
	public Shop(FishingGirlGame game, List<ShopItem> shopItems, Texture texture, float x, float y) {
		super(game, texture, x, y);
		this.shopItems = shopItems;
	}
	
	public void printShopItems() {
		for(ShopItem shopItem : shopItems) {
			System.out.println("Item: " + shopItem.getDescription() + ". Price: " + shopItem.getPrice());
		}
	}
	
	public List<ShopItem> getShopItems() {
		return shopItems;
	}
	
	private Vector2 calculatePosInShop(int i, int maxCols, int maxRows) {
		Rectangle rect = getSpace();
		
		float cellWidth = rect.getWidth() / maxCols;
		float cellHeight = rect.getHeight() / maxRows;
		
		float cellCentreX = rect.getX() + (i % maxCols) * cellWidth + (cellWidth / 2f);
		float cellCentreY = rect.getY() + rect.getHeight() - (1 + i/maxRows) * cellHeight + (cellHeight/ 2f);
		
		return new Vector2(cellCentreX, cellCentreY);
	}
	
	private Rectangle getSpace() {
		float top = getTop() - 64, bottom = getBottom() + 95;
		float left = getLeft();
		return new Rectangle(left, bottom, getWidth(), top - bottom);
	}

	public void open(float f, float g) {
		game.addToForegroundLayer(this);
		setPosition(f, g);
		ShopItem currentItem = null;
		for(int i = 0; i < shopItems.size(); i++) {
			currentItem = shopItems.get(i);
			Vector2 vec = calculatePosInShop(i, 2, 2);
			currentItem.setPosition(vec.x - currentItem.getWidth() / 2f, vec.y - currentItem.getHeight() / 2f);
			game.addToForegroundLayer(currentItem);
		}
	}
}