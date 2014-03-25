package com.sammik.fishinggirl.shop;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
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
	
	public Vector2 calculatePosInShop(int i, int maxCols, int maxRows) {
		Rectangle rect = getSpace();
		int count = 0;
		for(int j = 0; j < maxCols; j++) {
			for(int h = 0; h < maxRows; h++) {
				count++;
				if(count == i) { 
					System.out.println("SHOP: X: " + getX() + " Y: " + getY());
					System.out.println("RECT: X: " + rect.getX() + " Y: " + rect.getY());
					return new Vector2(rect.getX() + rect.getWidth() / maxCols * (h + 1), rect.getY() + rect.getHeight() / maxRows * (j + 1));
				}
			}
		}
		
		return new Vector2(0, 0);
	}
	
	private Rectangle getSpace() {
		float top = getTop() - 64, bottom = getBottom() + 95;
		float left = getLeft();
		return new Rectangle(bottom, left, getWidth(), top - bottom);
	}
}