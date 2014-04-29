package com.sammik.fishinggirl;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collider {
	private GameObject parent;
	private Rectangle collisionBounds;
	
	public Collider(GameObject parent, float x, float y, float w, float h) {
		this.parent = parent;
		collisionBounds = new Rectangle(x, y, w, h);
	}
	
	public boolean isCollidingWith(Collider other) {
		return Intersector.intersectRectangles(getWorldCollisionRectangle(), other.getWorldCollisionRectangle());
	}
	
	public Rectangle getWorldCollisionRectangle() {
		return new Rectangle(collisionBounds.x + parent.getX(), collisionBounds.y + parent.getY(), collisionBounds.width, collisionBounds.height);
	}

	public static boolean isColliding(Rectangle rect, GameObject object) {
		return object.getBoundingRectangle().overlaps(rect);
	}
	
	public static boolean isColliding(Rectangle rect1, Rectangle rect2) {
		return rect1.overlaps(rect2);
	}
	
	public static boolean isColliding(GameObject object1, GameObject object2) {
		return object1.getBoundingRectangle().overlaps(object2.getBoundingRectangle());
	}
	
	public static boolean isColliding(Vector2 v, GameObject object2) {
		return object2.getBoundingRectangle().contains(v.x, v.y);
	}
	
	public static boolean isColliding(Vector2 v, Rectangle rect) {
		return rect.contains(v.x, v.y);
	}
}
