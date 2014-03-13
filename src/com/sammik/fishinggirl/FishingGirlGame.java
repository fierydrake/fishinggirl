package com.sammik.fishinggirl;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sammik.fishinggirl.shop.Shop;
import com.sammik.fishinggirl.shop.ShopButton;

public class FishingGirlGame implements ApplicationListener {
	public static final float WORLD_WIDTH = 2048, WORLD_HEIGHT = 2048;
	
	Assets assets;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private GameObject background;
	private Ground cliff;
	private Water water;
	private Player player; 
	private FishingRod fishingRod;

	private List<GameObject> backgroundLayer = new ArrayList<GameObject>();
	private List<GameObject> baseLayer = new ArrayList<GameObject>();
	private List<GameObject> foregroundLayer = new ArrayList<GameObject>();
	private List<Fish> fishies = new ArrayList<Fish>();
	
	private ShopButton shopButton;
	private Shop shop;
 	
	@Override
	public void create() {
		// FIXME LATER: ignore window size for now. Assume it matches world size 
		float w = Gdx.graphics.getWidth()*2;
		float h = Gdx.graphics.getHeight()*2;
		
		Texture.setEnforcePotImages(false);
		
		MyInputProcessor inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		
		assets = new Assets();
		camera = new OrthographicCamera(w, h);
		camera.position.x += -w/2f;
		camera.position.y += -2048 + h/2f;
		camera.combined.translate(-w/2f, -2048+h/2, 0);
		batch = new SpriteBatch();
	
		cliff = new Ground(this, assets.texture("cliff"), 0, 0);
		background = new GameObject(this, assets.texture("background"), 0, cliff.getTop() - 50);
		water = new Water(this, assets.texture("water"), cliff.getRight() - 60, -200);
		fishingRod = new FishingRod(this, cliff.getRight() - 45, cliff.getTop() + 10);
		player = new Player(this, assets.texture("player"), cliff.getRight() - 80, cliff.getTop());
		
		SmallFish fish1 = new SmallFish(this,cliff.getRight() + 100, cliff.getTop() - 400);
		LargeFish fish2 = new LargeFish(this,cliff.getRight() + 300, cliff.getTop() - 300);
		
		fishies.add(fish1);
		fishies.add(fish2);
		
		backgroundLayer.add(background);
		backgroundLayer.add(water);
		float x = 40, y = cliff.getTop();
		baseLayer.add(new GameObject(this, assets.texture("smallTree"), x, y, 0, 74)); x+=assets.texture("smallTree").getWidth();
		baseLayer.add(new GameObject(this, assets.texture("largeTree"), x, y, 0, 4)); x+=assets.texture("largeTree").getWidth();
		baseLayer.add(new GameObject(this, assets.texture("lodge"), x, y, 0, 80)); x+=assets.texture("lodge").getWidth();
		baseLayer.add(new GameObject(this, assets.texture("house"), x - 30, y, 0, 10)); x+=assets.texture("house").getWidth();
		baseLayer.add(cliff);
		foregroundLayer.add(fishingRod);
		foregroundLayer.add(player);
		
		for(int i = 0; i < fishies.size(); i++){
			foregroundLayer.add(fishies.get(i));
		}
		
		//set up shop (guffaw)
		shopButton = new ShopButton(this, assets.texture("shopButton"), water.getCenterX(), water.getTop(), new Shop(this, assets.texture("shop"), 0, 0));
		foregroundLayer.add(shopButton);
	}
	
	public Water getWater() {
		return this.water;
	}
	
	public Ground getCliff() {
		return this.cliff;
	}
	
	public List<Fish> getFishies() {
		return fishies;
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
		shopButton.update(foregroundLayer);
		// logic
		fishingRod.update();
		for(int i = 0; i < fishies.size(); i++){
			fishies.get(i).update();
		}
		
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
	
	private class MyInputProcessor implements InputProcessor {
		   @Override
		   public boolean touchDown (int x, int y, int pointer, int button) {
			   if (button == Input.Buttons.LEFT) {
				   fishingRod.getLure().setPullAmount(5f);
			   } else {
				   fishingRod.getLure().setPullAmount(0);
			   }
			   return false;
		   }
		
			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}
		
			@Override
			public boolean keyUp(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}
		
			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}
		
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				if (button == Input.Buttons.LEFT) {
					Vector3 v = new Vector3(screenX, screenY, 1);
					camera.unproject(v);
					screenX = (int) (v.x - camera.position.x);
					screenY = (int) (v.y - camera.position.y);
					if(Collider.isColliding(new Vector2(screenX, screenY), shopButton)) {
						System.out.println("Clicked on shop!");
						shopButton.setShopActive(true);
					} else {
						fishingRod.getLure().setPullAmount(0);
				          if(fishingRod.isPulling()) {
				        	  fishingRod.Cast();
				        	  fishingRod.setCasting(true);
				        	  fishingRod.setPulling(false);
				          }
				          else if(fishingRod.getLure().isAttached()){
				        	  fishingRod.setPulling(true);
				          } else {
				        	  
				          }
				          return true;
					}
			      }
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}
		
			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}
		
			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}
		}
}
