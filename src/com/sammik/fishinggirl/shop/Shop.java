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
import com.sammik.fishinggirl.Collider;
import com.sammik.fishinggirl.FishingGirlGame;
import com.sammik.fishinggirl.GameObject;

public class Shop extends GameObject{
	private List<ShopItem> shopItems = new ArrayList<ShopItem>();
	private ShopItem activeItem = null;
	private ShapeRenderer shapeRenderer;
	
	public Shop(FishingGirlGame game, List<ShopItem> shopItems, Texture texture, float x, float y) {
		super(game, texture, x, y);
		this.shopItems = shopItems;
		shapeRenderer = new ShapeRenderer();
	}
	
	public void printShopItems() {
		for(ShopItem shopItem : shopItems) {
			System.out.println("Item: " + shopItem.getDescription() + ". Price: " + shopItem.getPrice());
		}
	}
	
	private Vector2 calculatePosInShop(int i, int maxCols, int maxRows) {
		Rectangle rect = getSpace();
		
		float cellWidth = rect.getWidth() / maxCols;
		float cellHeight = rect.getHeight() / maxRows;
		
		float cellCentreX = rect.getX() + (i % maxCols) * cellWidth + (cellWidth / 2f);
		float cellCentreY = rect.getY() + rect.getHeight() - (1 + i/maxRows) * cellHeight + (cellHeight/ 2f);
		
		return new Vector2(cellCentreX, cellCentreY);
	}
	
	public Rectangle getSpace() {
		float top = getTop() - 64, bottom = getBottom() + 95;
		float left = getLeft();
		return new Rectangle(left, bottom, getWidth(), top - bottom);
	}
	
	public void click(Vector2 vec) {
		for(ShopItem currentItem : shopItems) {
			if(Collider.isColliding(vec, currentItem)) {
				activeItem = currentItem;
				System.out.println("The active item is: " + activeItem.getName());
				break;
			}
		}
	}

	public void open(float f, float g) {
		game.spawn(this);
		setPosition(f, g);
		int i = 0;
		for(ShopItem currentItem : shopItems) {
			Vector2 vec = calculatePosInShop(i++, 2, 2);
			currentItem.setPosition(vec.x - currentItem.getWidth() / 2f, vec.y - currentItem.getHeight() / 2f);
			game.spawn(currentItem);
		}
	}
	
	public void close() {
		game.despawn(this);
		for(ShopItem currentItem : shopItems) {
			game.despawn(currentItem);
		}
		activeItem = null;
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		batch.end();
		shapeRenderer.begin(ShapeType.Rectangle);
		shapeRenderer.setColor(Color.YELLOW);
		shapeRenderer.setProjectionMatrix(game.camera.combined);
		
		if(activeItem != null) {
			shapeRenderer.rect(activeItem.getLeft(), activeItem.getBottom(), activeItem.getWidth(), activeItem.getHeight());
		}
		shapeRenderer.end();
		batch.begin();
	}
}