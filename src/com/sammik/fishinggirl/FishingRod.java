package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sammik.fishinggirl.Lure.LureSize;

public class FishingRod extends GameObject {
	public enum RodState { IDLE, CASTING, PULLING }
	
	private Hook hook;
	private float poleEndX, poleEndY;
	private RodState rodState = RodState.IDLE;
	private int pullingForce = 0, maxPullingForce = 150;
	private float rotateSpeed = 1.3f;
	private int pullForceIncreaseSpeed = 1;
	private int releasePoint = 30;
	
	public FishingRod(final FishingGirlGame game, final float x, final float y) {
		super(game, new TextureRegion(new Texture(Gdx.files.local("fishingGirl/fishingRod1.png")), 0, 0, 197, 15), x, y);
		
		setOrigin(0, getHeight() / 2);
		setScale(0.8f);
		
		poleEndX = getX() + (getWidth()/2 + 45);
		poleEndY = getY() - 10;
		
		hook = new Hook(game, this);
		game.spawn(hook);
		Lure lure = new Lure(game, this, LureSize.SMALL);
		game.spawn(lure);
		hook.attach(lure);
	}
	
	private boolean touchDownLureAttached = false;
	public boolean touchDown(int x, int y, int pointer, int button) {
		touchDownLureAttached = hook.isAttached();
		if (button == Input.Buttons.LEFT) {
			hook.setPullAmount(5f);
		} else {
			hook.setPullAmount(0);
		}
		return false;
	}
	
	public boolean touchUp(int x, int y, int pointer, int button) {
		hook.setPullAmount(0);
		if (rodState == RodState.PULLING && hook.isAttached()) {
			cast();
		}
		else if (touchDownLureAttached && rodState == RodState.IDLE && hook.isAttached()){
			rodState = RodState.PULLING;
		}
		return true;
	}

	@Override
	public void update() {
		if(rodState == RodState.PULLING) {
			if(pullingForce < maxPullingForce) {
				pullingForce += pullForceIncreaseSpeed;
				pullBack();
			} else {
				rodState = RodState.CASTING;
			}
		} else if(rodState == RodState.CASTING){
			castAnimation();
		} else {
			setRotation(0);
			pullingForce = 0;
		}

		float r = getWidth() * getScaleX();
		float dr = 1;
		float ang = getRotation() - dr;
		
		poleEndX = (float) (getByOriginX() + r * Math.cos(2.0*Math.PI * (ang/360.0)));
		poleEndY = (float) (getByOriginY() + r * Math.sin(2.0*Math.PI * (ang/360.0)));
		
		if (hook != null) {
			hook.update();
		}
	}
	
	public void cast() {
		rodState = RodState.CASTING;
	}
	
	public void drawLines(ShapeRenderer lineRenderer) {
		lineRenderer.setColor(Color.BLACK);
		lineRenderer.line(poleEndX, poleEndY, hook.getX() + hook.getWidth() / 2, hook.getY() + hook.getHeight() / 2);
	}
	
	public void pullBack() {
		rotate(rotateSpeed);
	}
	
	public void castAnimation() {
		if((rodState == RodState.CASTING || rodState == RodState.IDLE) && getRotation() < releasePoint) {
			hook.cast(pullingForce);
			rodState = RodState.CASTING;
		}
		
		if(getRotation() > 0 && rodState == RodState.CASTING) {
			rotate(-rotateSpeed * 10);
		} else {
			setRotation(0);
			rodState = RodState.IDLE;
		}
	}
	
	public float getEndX() {
		return this.poleEndX;
	}
	
	public float getEndY() {
		return this.poleEndY;
	}

	public boolean hasAttachedLure() {
		return hook != null && hook.isAttached();
	}

	public Hook getHook() {
		return hook;
	}

}
