
package com.sammik.fishinggirl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

// Should probably use the libgdx asset manager stuff
public class Assets {
	private Map<String, Texture> textures = new HashMap<String, Texture>();

	public Assets() {
		load("background", "background.png");
		load("cliff", "cliff.png");
		load("water", "watertile.png");
		load("fishingRod", "fishingRod1.png");
		load("rock3", "rock3.png");
		load("smallTree", "landscape1.png");
		load("largeTree", "landscape2.png");
		load("lodge", "landscape3.png");
		load("house", "landscape4.png");
		load("smallFish1", "fishSmall1.png");
		load("smallFish2", "fishSmall2.png");
		load("smallFish3", "fishSmall3.png");
		load("smallFish4", "fishSmall4.png");
		load("mediumFish1", "fishMedium1.png");
		load("mediumFish2", "fishMedium2.png");
		load("mediumFish3", "fishMedium3.png");
		load("mediumFish4", "fishMedium4.png");
		load("largeFish1", "fishLarge1.png");
		load("largeFish2", "fishLarge2.png");
		load("largeFish3", "fishLarge3.png");
		load("largeFish4", "fishLarge4.png");
		load("smallLure", "lureSmall.png");
		load("mediumLure", "lureMedium.png");
		load("largeLure", "lureLarge.png");
		load("player", "bearPurple.png");
		load("shop", "storeContainer.png");
		load("shopPurchase", "purchasedialog.png");
		load("shopTipRod", "rodshoptip.png");
		load("shopTipLure", "lureshoptip.png");
		load("bombLure", "lureBomb.png");
	}

	public Texture texture(final String name) {
		return textures.get(name);
	}
	
	public BitmapFont font(final String name) {
		return new BitmapFont();
	}
	
	public Texture randomTextureStartingWith(final String startsWith) {
		final List<String> candidateLabels = new ArrayList<String>();
		for (final String textureLabel : textures.keySet()) {
			if (textureLabel.startsWith(startsWith)) {
				candidateLabels.add(textureLabel);
			}
		}
		if (candidateLabels.size() == 0) return null;
		Collections.shuffle(candidateLabels);
		return texture(candidateLabels.get(0));
	}
	
	private void load(final String name, final String file) {
		if (textures.containsKey(name)) return;
		final Texture t = new Texture(Gdx.files.local("fishingGirl/" + file));
		t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		textures.put(name, t);
	}
	
	public void disposeAll() {
		for (final String name : textures.keySet()) {
			textures.get(name).dispose();
		}
		textures.clear();
	}
}
