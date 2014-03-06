package com.sammik.fishinggirl;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FishingGirlGame implements ApplicationListener {
	public static final float WORLD_WIDTH = 2048, WORLD_HEIGHT = 2048;
	
	Assets assets;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private GameObject background;
	private Ground cliff;
	private Water water;
	private FishingRod fishingRod;

	private List<GameObject> backgroundLayer = new ArrayList<GameObject>();
	private List<GameObject> baseLayer = new ArrayList<GameObject>();
	private List<GameObject> foregroundLayer = new ArrayList<GameObject>();
	private List<GameObject> fishes = new ArrayList<GameObject>();
 	
	@Override
	public void create() {
		// FIXME LATER: ignore window size for now. Assume it matches world size 
		float w = Gdx.graphics.getWidth()*2;
		float h = Gdx.graphics.getHeight()*2;
		
		Texture.setEnforcePotImages(false);
		
		assets = new Assets();
		camera = new OrthographicCamera(w, h);
		camera.combined.translate(-w/2f, -2048+h/2, 0);
		batch = new SpriteBatch();
	
		cliff = new Ground(this, assets.texture("cliff"), 0, 0);
		background = new GameObject(this, assets.texture("background"), 0, cliff.getTop());
		water = new Water(this, assets.texture("water"), cliff.getRight(), 0);
		fishingRod = new FishingRod(this, cliff.getRight(), cliff.getTop());
		
		SmallFish fish1 = new SmallFish(this,cliff.getRight() + 100, cliff.getTop() - 400);
		LargeFish fish2 = new LargeFish(this,cliff.getRight() + 300, cliff.getTop() - 300);
		
		fishes.add(fish1);
		fishes.add(fish2);
		
		
		backgroundLayer.add(background);
		backgroundLayer.add(water);
		float x = 40, y = cliff.getTop();
		baseLayer.add(new GameObject(this, assets.texture("smallTree"), x, y, 0, 74)); x+=assets.texture("smallTree").getWidth();
		baseLayer.add(new GameObject(this, assets.texture("largeTree"), x, y, 0, 4)); x+=assets.texture("largeTree").getWidth();
		baseLayer.add(new GameObject(this, assets.texture("lodge"), x, y, 0, 80)); x+=assets.texture("lodge").getWidth();
		baseLayer.add(new GameObject(this, assets.texture("house"), x, y, 0, 10)); x+=assets.texture("house").getWidth();
		baseLayer.add(cliff);
		foregroundLayer.add(fishingRod);
		for(int i = 0; i < fishes.size(); i++){
			foregroundLayer.add(fishes.get(i));
		}
	}
	
	public Water getWater() {
		return this.water;
	}
	
	public Ground getCliff() {
		return this.cliff;
	}

	@Override
	public void dispose() {
		batch.dispose();
		assets.disposeAll();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		
		// logic
		fishingRod.update();
		
		// render
		batch.begin();
		background.draw(batch);
		for (final GameObject s : backgroundLayer) s.draw(batch);
		for (final GameObject s : baseLayer) s.draw(batch);
		for (final GameObject s : foregroundLayer) s.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public Camera getCamera() {
		return this.camera;
	}
}
