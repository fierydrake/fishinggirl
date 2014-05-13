package com.sammik.fishinggirl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class FishingRod extends GameObject {
	private float poleEndX, poleEndY;
	private Lure lure;
	
	private boolean pulling = false;
	private int pullingForce = 0, maxPullingForce = 150;
	
	private float rotateSpeed = 1.3f;
	private int pullForceIncreaseSpeed = 1;
	private int releasePoint = 30;
	private boolean casting;
	
	public FishingRod(final FishingGirlGame game, final float x, final float y) {
		super(game, new TextureRegion(new Texture(Gdx.files.local("fishingGirl/fishingRod1.png")), 0, 0, 197, 15), x, y);
		
		lure = new Lure(game, this, Lure.LureSize.SMALL);

		setOrigin(0, getHeight() / 2);
		setScale(0.8f);
		
		poleEndX = getX() + (getWidth()/2 + 45);
		poleEndY = getY() - 10;
		
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

		float r = getWidth() * getScaleX();
		float dr = 1;
		float ang = getRotation() - dr;
		
		poleEndX = (float) (getByOriginX() + r * Math.cos(2.0*Math.PI * (ang/360.0)));
		poleEndY = (float) (getByOriginY() + r * Math.sin(2.0*Math.PI * (ang/360.0)));
		
		if(lure != null) {
			lure.update();
			if(!lure.isOnScreen()) {
				lure.setAttached(true);
			}
		}
	}
	
	public void Cast() {
		casting = true;
	}
	
	
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		if(lure != null) {
			lure.draw(batch);
		}
		batch.end();
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.setProjectionMatrix(game.getCamera().combined);
		shapeRenderer.line(poleEndX, poleEndY, lure.getX() + lure.getWidth() / 2, lure.getY() + lure.getHeight() / 2);
		shapeRenderer.end();
		batch.begin();
	}
	
	public boolean isPulling() {
		return pulling;
	}
	
	public void pullBack() {
		rotate(rotateSpeed);
	}
	
	public void castAnimation() {
		if(lure.isAttached() && getRotation() < releasePoint) {
			lure.Cast(pullingForce);
			lure.setAttached(false);
		}
		
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
	
	public float getEndX() {
		return this.poleEndX;
	}
	
	public float getEndY() {
		return this.poleEndY;
	}
	
	public Lure getLure() {
		return this.lure;
	}

	public void setCasting(boolean b) {
		this.casting = b;
	}
}
