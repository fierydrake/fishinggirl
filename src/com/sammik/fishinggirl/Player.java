package com.sammik.fishinggirl;

import com.badlogic.gdx.graphics.Texture;
import com.sammik.fishinggirl.Lure.LureSize;
import com.sammik.fishinggirl.shop.ShopItem;
import com.sammik.fishinggirl.shop.ProgressData.LureLevel;

public class Player extends GameObject {
	public enum RodLevel { BRONZE, SILVER, GOLD, LEGENDARY }
	
	private int numOfBombLures;
	private RodLevel rodLevel;
	private LureSize lureSize;
	private int money;

	public Player(final FishingGirlGame game, final Texture texture, final float x, final float y) {
		super(game, texture, x, y,0,0);
		numOfBombLures = 0;
		money = 0;
		rodLevel = RodLevel.BRONZE;
		lureSize = LureSize.SMALL;
	}
	
	public RodLevel getRodLevel() { return rodLevel; }
	public void setRodLevel(RodLevel level) { rodLevel = level; }
	public LureSize getLureSize() { return lureSize; }
	public void setLureSize(LureSize level) { lureSize = level; }
	
	public int getMoney() { return money; }
	public boolean canAfford(ShopItem item) { return item.getPrice() <= money; }
	public boolean canAfford(int amount) { return amount <= money; }
	public void debit(int amount) { money -= amount; }  
	public void credit(int amount) { money += amount; }
}
