package com.sammik.fishinggirl;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Collider {
	
	public abstract Polygon getWorldCollisionPolygon();

	public boolean isCollidingWith(Collider collider) {
		return Intersector.overlapConvexPolygons(collider.getWorldCollisionPolygon(), getWorldCollisionPolygon());
	}
	
	public boolean isCollidingWith(Vector2 point) {
		return isCollidingWith(point.x, point.y);
	}
	
	public boolean isCollidingWith(float x, float y) {
		float[] points = getWorldCollisionPolygon().getVertices();
		return Intersector.isPointInPolygon(points, 0, points.length, x, y);
	}
	
	/* Statics */
	protected static Polygon makeOffsetPolygon(Polygon polygon, float offsetX, float offsetY) {
		float[] points = polygon.getVertices();
		float[] worldPoints = new float[points.length];
		for (int i=0; i<points.length; i+=2) {
			worldPoints[i] = points[i] + offsetX;
			worldPoints[i+1] = points[i+1] + offsetY;
		}
		return new Polygon(worldPoints);
	}

//	public static boolean isColliding(Rectangle rect, GameObject object) {
//		return object.getBoundingRectangle().overlaps(rect);
//	}
//	
//	public static boolean isColliding(Rectangle rect1, Rectangle rect2) {
//		return rect1.overlaps(rect2);
//	}
//	
//	public static boolean isColliding(GameObject object1, GameObject object2) {
//		return object1.getBoundingRectangle().overlaps(object2.getBoundingRectangle());
//	}
//	
//	public static boolean isColliding(Vector2 v, GameObject object2) {
//		return object2.getBoundingRectangle().contains(v.x, v.y);
//	}
//	
//	public static boolean isColliding(Vector2 v, Rectangle rect) {
//		return rect.contains(v.x, v.y);
//	}
}
