
package com.sammik.fishinggirl;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

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
		load("smallLure", "lureSmall.png");
		load("mediumLure", "lureMedium.png");
		load("largeLure", "lureLarge.png");
	}

	public Texture texture(final String name) {
		return textures.get(name);
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
