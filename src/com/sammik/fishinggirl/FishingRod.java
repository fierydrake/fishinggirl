package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FishingRod extends GameObject {
	private Lure lure;
	
	private boolean pulling = false;
	private int pullingForce = 0, maxPullingForce = 100;
	
	private int rotateSpeed = 1;
	private int pullForceIncreaseSpeed = 1;
	private boolean casting;
	
	public FishingRod(final float x, final float y) {
		super(new TextureRegion(new Texture(Gdx.files.local("fishingGirl/fishingRod1.png")), 0, 0, 197, 15), x, y);
		
		MyInputProcessor inputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		
		setOrigin(0, getHeight());
		setScale(0.8f);
		
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
			setRotation(0);
			pullingForce = 0;
		}
		
		if(lure != null) {
			System.out.println("lure not null!");
			lure.update();
			if(!lure.isOnScreen()) {
				lure = null;
			}
		}
		//sprite.setPosition(x, y);
	}
	
	public void Cast() {
		casting = true;
		lure = new Lure(this, pullingForce);
	}
	
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		if(lure != null) {
			lure.draw(batch);
		}
	}
	
	public boolean isPulling() {
		return pulling;
	}
	
	public void pullBack() {
		rotate(rotateSpeed);
	}
	
	public void castAnimation() {
		if(getRotation() > 0 && casting) {
			rotate(-rotateSpeed * 10);
		} else {
			setRotation(0);
			casting = false;
		}
	}
	
	public void setPulling(boolean b) {
		pulling = b;
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
