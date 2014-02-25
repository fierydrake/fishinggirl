package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FishingRod {
	private int x, y;
	private Sprite fishingRodSprite;
	private Texture fishingRodTexture;
	
	private Lure lure;
	
	private boolean pulling = false;
	private int pullingForce = 0, maxPullingForce = 100;
	
	private int rotateSpeed = 1;
	private int pullForceIncreaseSpeed = 1;
	private boolean casting;
	

	public FishingRod() {
		MyInputProcessor inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		
		x = 50;
		y = 500;
		
		Texture.setEnforcePotImages(false);
		fishingRodTexture = new Texture(Gdx.files.local("fishingGirl/fishingRod1.png"));
		fishingRodTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
		TextureRegion region = new TextureRegion(fishingRodTexture, 0, 0, 197, 15);
		
		fishingRodSprite = new Sprite(region);
		fishingRodSprite.setOrigin(0, fishingRodSprite.getHeight());
		fishingRodSprite.setScale(0.8f);
		fishingRodSprite.setPosition(x, y);
		
		lure = null;
	}
	
	public void update() {
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
			System.out.println("lure not null!");
			lure.update();
			if(!lure.isOnScreen()) {
				lure = null;
			}
		}
		//fishingRodSprite.setPosition(x, y);
	}
	
	public void Cast() {
		casting = true;
		lure = new Lure(this, pullingForce);
	}
	
	
	public void draw(SpriteBatch batch) {
		fishingRodSprite.draw(batch);
		if(lure != null) {
			lure.draw(batch);
		}
	}
	
	public boolean isPulling() {
		return pulling;
	}
	
	public void pullBack() {
		fishingRodSprite.rotate(rotateSpeed);
	}
	
	public void castAnimation() {
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
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}


	private class MyInputProcessor implements InputProcessor {
		   @Override
		   public boolean touchDown (int x, int y, int pointer, int button) {
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
		          if(pulling) {
		        	  Cast();
		        	  casting = true;
		        	  pulling = false;
		          }
		          else {
		        	  pulling = true;
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
