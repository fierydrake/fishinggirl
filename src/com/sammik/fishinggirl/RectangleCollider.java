package com.sammik.fishinggirl;

import com.badlogic.gdx.math.Polygon;

public class RectangleCollider extends Collider {
	private GameObject parent;
	private Polygon collisionPolygon;
	
	public RectangleCollider(GameObject parent, float x, float y, float w, float h) {
		this.parent = parent;
		collisionPolygon = new Polygon(new float[] { x,y, x+w,y, x+w,y+h, x,y+h });
	}

	@Override
	public Polygon getWorldCollisionPolygon() {
		return makeOffsetPolygon(collisionPolygon, parent.getByOriginX(), parent.getByOriginY());
	}

}
