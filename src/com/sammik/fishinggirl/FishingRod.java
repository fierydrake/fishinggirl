package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.sammik.fishinggirl.FishingRod.RodState;

public class FishingRod extends GameObject {
	public enum RodState { IDLE, CASTING, PULLING }
	
	private boolean isLureAttached;
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
		
	}
	
	public RodState getRodState() { return rodState; }
	public void setRodState(RodState rodState) { this.rodState = rodState; }
	
	public void update() {
		float dx = (getWidth()/2 + 45);
		float dy = -10;
	
		float ang = getRotation() / 0.45f;
		float r = (float)Math.sqrt((dx * dx) + (dy * dy));
		
		poleEndX = (float) (getX() + r * Math.cos(Math.PI * (ang/360.0)));
		poleEndY = (float) (getY() + r * Math.sin(Math.PI * (ang/360.0)));
		
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
	}
	
	public void Cast() {
		isLureAttached = false;
		rodState = RodState.CASTING;
	}
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		batch.end();
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.setProjectionMatrix(game.getCamera().combined);
		shapeRenderer.line(poleEndX, poleEndY, game.getLure().getX() + game.getLure().getWidth() / 2, game.getLure().getY() + game.getLure().getHeight() / 2);
		shapeRenderer.end();
		batch.begin();
	}
	
	public void pullBack() {
		rotate(rotateSpeed);
	}
	
	public void castAnimation() {
		if((rodState == RodState.CASTING || rodState == RodState.IDLE) && getRotation() < releasePoint) {
			game.getLure().Cast(pullingForce);
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
		return false;
	}

}
