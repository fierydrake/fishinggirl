package com.sammik.fishinggirl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sammik.fishinggirl.shop.Shop;
import com.sammik.fishinggirl.shop.ShopItem;

public class FishingGirlGame implements ApplicationListener {
	public static final float WORLD_WIDTH = 2048, WORLD_HEIGHT = 2048;
	public static final int MAX_FISH = 40;
	public enum Layer {
		BACKGROUND, FOREGROUND, BASE
	}

	public Assets assets;
	public OrthographicCamera camera;
	private SpriteBatch gameBatch, uiBatch;
	private ShapeRenderer lineRenderer;
	private BitmapFont font;
	private GameObject background;
	private Ground cliff;
	private Water water;
	private Player player; 
	private FishingRod fishingRod;
	private Shop lureShop, rodShop;
	private boolean debugMode, showFPS;

	private List<GameObject> backgroundLayer = new ArrayList<GameObject>();
	private List<GameObject> baseLayer = new ArrayList<GameObject>();
	private List<GameObject> foregroundLayer = new ArrayList<GameObject>();
	private List<GameObject> activeObjects = new ArrayList<GameObject>();
	private List<Fish> fishies = new ArrayList<Fish>();
	private List<ShopItem> shopItems = new ArrayList<ShopItem>();

	private Matrix4 def;
	
	@Override
	public void create() {
		// FIXME LATER: ignore window size for now. Assume it matches world size 
		float w = Gdx.graphics.getWidth() * 2f;
		float h = Gdx.graphics.getHeight() * 2f;

		Texture.setEnforcePotImages(false);

		MyInputProcessor inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

		debugMode = false;
		showFPS = false;
		assets = new Assets();
		camera = new OrthographicCamera(w, h);
		camera.translate(w/2f + 512, -h/2f + 2048);
		camera.update();
		gameBatch = new SpriteBatch();
		uiBatch = new SpriteBatch();
		lineRenderer = new ShapeRenderer();
		font = new BitmapFont();
//		font.setScale(2f);

		cliff = new Ground(this, assets.texture("cliff"), 0, 0);
		background = new GameObject(this, assets.texture("background"), 0, cliff.getTop() - 50);
		final Texture waterTexture = assets.texture("water");
		water = new Water(this, waterTexture, cliff.getRight() - 60, -200, waterTexture.getWidth()*2f, waterTexture.getHeight());
		final Water waterOverlay = new Water(this, waterTexture, cliff.getRight() - 60, -200, waterTexture.getWidth()*2f, waterTexture.getHeight(), 0.25f);
		fishingRod = new FishingRod(this, cliff.getRight() - 45, cliff.getTop() + 10);
		player = new Player(this, assets.texture("player"), cliff.getRight() - 80, cliff.getTop());

		for (int i=0; i < MAX_FISH; i++) {
			final Fish fishie = Fish.randomFish(this, cliff.getRight(), water.getRight(), water.getBottom(), water.getTop()); 
			fishies.add(fishie);
			spawn(fishie, true);
		}

		//set up shop (guffaw)
		rodShop = new Shop(this, Shop.Type.ROD_SHOP, 
				assets.texture("shop"), assets.texture("shopTipRod"), assets.texture("shopPurchase"),
				cliff.getRight() + 512f, water.getWaterLine());
		spawn(rodShop, Layer.BASE, true);
		
		lureShop = new Shop(this, Shop.Type.LURE_SHOP, 
				assets.texture("shop"), assets.texture("shopTipLure"), assets.texture("shopPurchase"), 
				water.getCenterX(), water.getWaterLine());
		spawn(lureShop, Layer.BASE, true);
		spawn(waterOverlay, Layer.BASE); // only covers shops (and normal water) atm

		spawn(background, Layer.BACKGROUND);
		spawn(water, Layer.BACKGROUND);
		float x = 40, y = cliff.getTop();
		spawn(new GameObject(this, assets.texture("smallTree"), x, y, 0, 74), Layer.BASE); x+=assets.texture("smallTree").getWidth();
		spawn(new GameObject(this, assets.texture("largeTree"), x, y, 0, 4), Layer.BASE); x+=assets.texture("largeTree").getWidth();
		spawn(new GameObject(this, assets.texture("lodge"), x, y, 0, 80), Layer.BASE); x+=assets.texture("lodge").getWidth();
		spawn(new GameObject(this, assets.texture("house"), x - 30, y, 0, 10), Layer.BASE); x+=assets.texture("house").getWidth();
		spawn(cliff, Layer.FOREGROUND);
		spawn(fishingRod, Layer.FOREGROUND, true);
		spawn(player, Layer.FOREGROUND, true);
		
		pushMessage("Welcome to Fishing Girl");
	}

	public Water getWater() { return this.water; }
	public Ground getCliff() { return this.cliff; }
	public List<Fish> getFishies() { return fishies; }
	public Shop getLureShop() { return lureShop; }
	public Shop getRodShop() { return rodShop; }
	public Player getPlayer() { return player; }

	public void spawn(final GameObject obj, final Layer layer, boolean active) {
		if (obj == null) return;
		switch (layer) {
		case BACKGROUND: backgroundLayer.add(obj); break;
		case BASE: baseLayer.add(obj); break;
		case FOREGROUND: foregroundLayer.add(obj); break;
		default:
			foregroundLayer.add(obj);
		}
		if (active) { activeObjects.add(obj); }
	}

	public void spawn(final GameObject obj) {
		spawn(obj, Layer.FOREGROUND, false);
	}

	public void spawn(final GameObject obj, final Layer layer) {
		spawn(obj, layer, false);
	}

	public void spawn(final GameObject obj, boolean active) {
		spawn(obj, Layer.FOREGROUND, active);
	}

	public void despawn(final GameObject obj) {
		foregroundLayer.remove(obj);
		baseLayer.remove(obj);
		backgroundLayer.remove(obj);
		activeObjects.remove(obj);
	}
	
	private static class Message {
		public String text;
		public float timeUntilDeath;
		public Message(final String msg) {
			text = msg;
			timeUntilDeath = 4f;
		}
	}
	private List<Message> messages = new ArrayList<Message>();
	public void pushMessage(final String message) {
		messages.add(new Message(message));
	}
	
	private void drawFPS(final SpriteBatch batch) {
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 4f,
				Gdx.graphics.getHeight() - 4f);
	}
	private void drawMessages(final SpriteBatch batch) {
		float cursorY = 0;
		final float paddingTop = 4f;
		Iterator<Message> i = messages.iterator();
		while (i.hasNext()) {
			final Message message = i.next();
			message.timeUntilDeath -= Gdx.graphics.getDeltaTime();
			if (message.timeUntilDeath > 0) {
				final TextBounds bounds = font.getBounds(message.text);
				font.draw(batch, message.text,
						(Gdx.graphics.getWidth() - bounds.width) / 2f,
						Gdx.graphics.getHeight() - paddingTop - cursorY);
				cursorY += bounds.height + paddingTop;
			} else {
				i.remove();
			}
		}
	}

	@Override
	public void dispose() {
		gameBatch.dispose();
		uiBatch.dispose();
		font.dispose();
		assets.disposeAll();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		gameBatch.setProjectionMatrix(camera.combined);
		lineRenderer.setProjectionMatrix(camera.combined);
		
		// logic
		for (final GameObject s : activeObjects) {
			s.update();
		}

		// render
		gameBatch.begin();
		background.draw(gameBatch);
		for (final GameObject s : backgroundLayer) s.draw(gameBatch);
		for (final GameObject s : baseLayer) s.draw(gameBatch);
		for (final GameObject s : foregroundLayer) s.draw(gameBatch);
		gameBatch.end();
		
		lineRenderer.begin(ShapeType.Line);
		for (final GameObject s : backgroundLayer) s.drawLines(lineRenderer);
		for (final GameObject s : baseLayer) s.drawLines(lineRenderer);
		for (final GameObject s : foregroundLayer) s.drawLines(lineRenderer);
		
		// debug!
		if (debugMode) {
			for (final GameObject s : backgroundLayer) s.debugDraw(lineRenderer);
			for (final GameObject s : baseLayer) s.debugDraw(lineRenderer);
			for (final GameObject s : foregroundLayer) s.debugDraw(lineRenderer);
		}
		lineRenderer.end();
		
		uiBatch.begin();
		drawMessages(uiBatch);
		if (showFPS) drawFPS(uiBatch);
		uiBatch.end();
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
			return fishingRod.touchDown(x, y, pointer, button);
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
			if (character == 'd' || character == 'D') debugMode = !debugMode;
			if (character == 'f' || character == 'F') showFPS = !showFPS;
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			if (button == Input.Buttons.LEFT) {
				Vector3 v = new Vector3(screenX, screenY, 0);
				camera.unproject(v);
				if (Collider.isColliding(new Vector2(v.x, v.y), lureShop)) {
					lureShop.click(new Vector2(v.x, v.y));
				} else if(Collider.isColliding(new Vector2(v.x, v.y), rodShop)) {
					rodShop.click(new Vector2(v.x, v.y));
				} else {
					return fishingRod.touchUp(screenX, screenY, pointer, button);
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
