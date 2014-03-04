package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class FishingRod {
	private float x, y, poleEndX, poleEndY;
	private Sprite fishingRodSprite;
	private Texture fishingRodTexture;
	
	private Lure lure;
	
	private boolean pulling = false;
	private int pullingForce = 0, maxPullingForce = 150;
	
	private float rotateSpeed = 1.3f;
	private int pullForceIncreaseSpeed = 1;
	private int releasePoint = 30;
	private boolean casting;
	

	public FishingRod() {
		MyInputProcessor inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		
		x = 50;
		y = 500;
		
		Texture.setEnforcePotImages(false);
		fishingRodTexture = new Texture(Gdx.files.local("fishingGirl/fishingRod1.png"));
		fishingRodTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		lure = new Lure(this);
		
		TextureRegion region = new TextureRegion(fishingRodTexture, 0, 0, 197, 15);
		
		fishingRodSprite = new Sprite(region);
		fishingRodSprite.setOrigin(0, fishingRodSprite.getHeight() /2);
		fishingRodSprite.setScale(0.8f);
		fishingRodSprite.setPosition(x, y);
		
		poleEndX = fishingRodSprite.getX() + (fishingRodSprite.getWidth()/2 + 45);
		poleEndY = fishingRodSprite.getY() - 10;
		
	}
	
	public void update() {
		
//		x = cx + r * cos(a)
//		y = cy + r * sin(a)
		
		float dx = (fishingRodSprite.getWidth()/2 + 45);
		float dy = -10;
	
		float ang = fishingRodSprite.getRotation() / 0.45f;
		float r = (float)Math.sqrt((dx * dx) + (dy * dy));
		
		poleEndX = (float) (fishingRodSprite.getX() + r * Math.cos(Math.PI * (ang/360.0)));
		poleEndY = (float) (fishingRodSprite.getY() + r * Math.sin(Math.PI * (ang/360.0)));
		
		if(isPulling()) {
			if(pullingForce < maxPullingForce) {
				pullingForce += pullForceIncreaseSpeed;
				pullBack();
			} else {
				pullingForce = 0;
				pulling = false;
			}
		} else if(casting){
			castAnimation();
		} else {
			fishingRodSprite.setRotation(0);
			pullingForce = 0;
		}
		
		if(lure != null) {
			lure.update();
			if(!lure.isOnScreen()) {
				lure.setAttached(true);
			}
		}
		//fishingRodSprite.setPosition(x, y);
	}
	
	public void Cast() {
		casting = true;
	}
	
	
	public void draw(SpriteBatch batch) {
		fishingRodSprite.draw(batch);
		if(lure != null) {
			lure.draw(batch);
		}
		batch.end();
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.line(poleEndX, poleEndY, lure.getX() + lure.getWidth() / 2, lure.getY() + lure.getHeight() / 2);
		shapeRenderer.end();
		batch.begin();
	}
	
	public boolean isPulling() {
		return pulling;
	}
	
	public void pullBack() {
		fishingRodSprite.rotate(rotateSpeed);
	}
	
	public void castAnimation() {
		if(lure.isAttached() && fishingRodSprite.getRotation() < releasePoint) {
			lure.Cast(pullingForce);
			lure.setAttached(false);
		}
		
		if(fishingRodSprite.getRotation() > 0 && casting) {
			fishingRodSprite.rotate(-rotateSpeed * 10);
		} else {
			fishingRodSprite.setRotation(0);
			casting = false;
		}
	}
	
	public void setPulling(boolean b) {
		pulling = b;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float getEndX() {
		return this.poleEndX;
	}
	
	public float getEndY() {
		return this.poleEndY;
	}


	private class MyInputProcessor implements InputProcessor {
	   @Override
	   public boolean touchDown (int x, int y, int pointer, int button) {
		   System.out.println(x + ", " + y);
		   if (button == Input.Buttons.LEFT) {
			   lure.setPullAmount(5f);
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
				lure.setPullAmount(0);
		          if(pulling) {
		        	  Cast();
		        	  casting = true;
		        	  pulling = false;
		          }
		          else if(lure.isAttached()){
		        	  pulling = true;
		          } else {
		        	  
		          }
		          return true;
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
