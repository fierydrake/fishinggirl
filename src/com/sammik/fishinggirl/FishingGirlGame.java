package com.sammik.fishinggirl;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FishingGirlGame implements ApplicationListener {
	public static final float WORLD_WIDTH = 2048, WORLD_HEIGHT = 2048;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite background;
	private Ground cliff;
	private Water water;
	private Sprite house;
	private FishingRod fishingRod;
	
	
	
	@Override
	public void create() {
		// FIXME LATER: ignore window size for now. Assume it matches world size 
//		float w = Gdx.graphics.getWidth();
//		float h = Gdx.graphics.getHeight();
		
		Texture.setEnforcePotImages(false);
		
		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.combined.translate(-WORLD_WIDTH/2f, -WORLD_HEIGHT/2f, 0);
		batch = new SpriteBatch();
		
		cliff = new Ground(new Texture(Gdx.files.local("fishingGirl/cliff.png")));
		water = new Water(new Texture(Gdx.files.local("fishingGirl/watertile.png")), cliff.getRight(), 0);
		
		house = new Sprite(new Texture(Gdx.files.local("fishingGirl/landscape4.png")));
		house.setPosition(cliff.getRight() - house.getWidth(), cliff.getTop()-2f);

		background = new Sprite(new Texture(Gdx.files.local("fishingGirl/background.png")));
		background.setPosition(0, cliff.getTop());
		
		fishingRod = new FishingRod();
	}

	@Override
	public void dispose() {
		batch.dispose();
		background.getTexture().dispose();
//		cliff.dispose();
//		house.dispose();
//		water.dispose();
//		fishingRod.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		fishingRod.update();
		batch.begin();
		background.draw(batch);
		water.draw(batch);
		cliff.draw(batch);
		house.draw(batch);
		fishingRod.draw(batch);
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
}
