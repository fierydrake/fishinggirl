package com.sammik.fishinggirl;

import java.awt.geom.Arc2D;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TriangleCollider extends Collider{
	private GameObject parent;
	private Polygon collisionPolygon; 
	
	public TriangleCollider(GameObject parent, float x, float y, float radius, float angle, Vector2 direction) {
		this.parent = parent;
		Vector2 top = new Vector2(direction);
		Vector2 bottom = new Vector2(direction);
		top.rotate(-angle / 2f).nor().scl(radius);
		bottom.rotate(angle / 2f).nor().scl(radius);
		collisionPolygon = new Polygon(new float[] { x,y, x+top.x,y+top.y, x+bottom.x,y+bottom.y});
	}
	
	@Override
	public Polygon getWorldCollisionPolygon() {
		return makeOffsetPolygon(collisionPolygon, parent.getByOriginX(), parent.getByOriginY());
	}
}
