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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sammik.fishinggirl.FishingRod.RodState;
import com.sammik.fishinggirl.shop.Shop;
import com.sammik.fishinggirl.shop.ShopButton;
import com.sammik.fishinggirl.shop.ShopItem;

public class FishingGirlGame implements ApplicationListener {
	public static final float WORLD_WIDTH = 2048, WORLD_HEIGHT = 2048;
	public static final int MAX_FISH = 40;
	public enum Layer {
		BG, FG, BASE
	}
	
	public Assets assets;
	public OrthographicCamera camera;
	private SpriteBatch batch;
	private GameObject background;
	private Ground cliff;
	private Water water;
	private Player player; 
	private FishingRod fishingRod;
	private Lure lure;

	private List<GameObject> backgroundLayer = new ArrayList<GameObject>();
	private List<GameObject> baseLayer = new ArrayList<GameObject>();
	private List<GameObject> foregroundLayer = new ArrayList<GameObject>();
	private List<Fish> fishies = new ArrayList<Fish>();
	private List<ShopItem> shopItems = new ArrayList<ShopItem>();
	
	private ShopButton shopButton;
	private Shop shop;
 	
	@Override
	public void create() {
		// FIXME LATER: ignore window size for now. Assume it matches world size 
		float w = Gdx.graphics.getWidth() * 1.8f;
		float h = Gdx.graphics.getHeight() * 1.8f;
		
		Texture.setEnforcePotImages(false);
		
		MyInputProcessor inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		
		assets = new Assets();
		camera = new OrthographicCamera(w, h);
		camera.translate(w/1.5f, -h/2f + 2048);
		camera.update();
		batch = new SpriteBatch();

		cliff = new Ground(this, assets.texture("cliff"), 0, 0);
		background = new GameObject(this, assets.texture("background"), 0, cliff.getTop() - 50);
		final Texture waterTexture = assets.texture("water");
		water = new Water(this, waterTexture, cliff.getRight() - 60, -200, waterTexture.getWidth()*2f, waterTexture.getHeight());
		fishingRod = new FishingRod(this, cliff.getRight() - 45, cliff.getTop() + 10);
		player = new Player(this, assets.texture("player"), cliff.getRight() - 80, cliff.getTop());
		
		lure = new Lure(this, fishingRod, Lure.LureSize.SMALL);
		spawn(lure);
		
		for (int i=0; i < MAX_FISH; i++) {
			final Fish fishie = Fish.randomFish(this, cliff.getRight(), water.getRight(), water.getBottom(), water.getTop()); 
			fishies.add(fishie);
			spawn(fishie);
		}
		
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
		
		//set up shop (guffaw)
		shop = new Shop(this, Shop.Type.LURE_SHOP, assets.texture("shop"), 0, 0);
		shopButton = new ShopButton(this, assets.texture("shopButton"), water.getCenterX(), water.getTop(), shop);
		foregroundLayer.add(shopButton);
	}

	public Water getWater() { return this.water; }
	public Ground getCliff() { return this.cliff; }
	public List<Fish> getFishies() { return fishies; }
	public Player getPlayer() { return player; }
	public Lure getLure() { return lure; }
	
	public void spawn(final GameObject obj, final Layer layer) {
		if (obj == null) return;
		switch (layer) {
		case BG: backgroundLayer.add(obj); break;
		case BASE: baseLayer.add(obj); break;
		case FG: foregroundLayer.add(obj); break;
		default:
			foregroundLayer.add(obj);
		}
	}
	
	public void spawn(final GameObject obj) {
		spawn(obj, Layer.FG);
	}
	
	public void despawn(final GameObject obj) {
		foregroundLayer.remove(obj);
		baseLayer.remove(obj);
		backgroundLayer.remove(obj);
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
		for (final Fish fishie : fishies) {
			fishie.update();
		}
		
		lure.update();
		// render
		batch.begin();
		background.draw(batch);
		for (final GameObject s : backgroundLayer) s.draw(batch);
		for (final GameObject s : baseLayer) s.draw(batch);
		for (final GameObject s : foregroundLayer) s.draw(batch);
		batch.end();
		
		// debug!
		final ShapeRenderer shapeRenderer = new ShapeRenderer();
		for (final GameObject s : backgroundLayer) s.debugDraw(shapeRenderer);
		for (final GameObject s : baseLayer) s.debugDraw(shapeRenderer);
		for (final GameObject s : foregroundLayer) s.debugDraw(shapeRenderer);
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
				   lure.setPullAmount(5f);
			   } else {
				   lure.setPullAmount(0);
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
				if (button == Input.Buttons.LEFT) {
					Vector3 v = new Vector3(screenX, screenY, 0);
					camera.unproject(v);
					if(Collider.isColliding(new Vector2(v.x, v.y), shopButton)) {
						System.out.println("Clicked on shop button!");
						shopButton.click();
					} else if(Collider.isColliding(new Vector2(v.x, v.y), shop.getSpace())) {
						System.out.println("Clicked on shop!");
						shop.click(new Vector2(v.x, v.y));
					} else {
						lure.setPullAmount(0);
				          if(fishingRod.getRodState() == RodState.PULLING && lure.isAttached()) {
				        	  fishingRod.Cast();
				          }
				          else if(fishingRod.getRodState() == RodState.IDLE && lure.isAttached()){
				        	  fishingRod.setRodState(RodState.PULLING);
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
