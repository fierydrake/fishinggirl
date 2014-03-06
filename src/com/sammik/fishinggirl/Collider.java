package com.sammik.fishinggirl;

import com.badlogic.gdx.math.Rectangle;

public class Collider {
	public static boolean isColliding(Rectangle rect, GameObject object) {
		if(object.getBoundingRectangle().overlaps(rect)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isColliding(Rectangle rect1, Rectangle rect2) {
		if(rect1.contains(rect2)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isColliding(GameObject object1, GameObject object2) {
		if(object1.getBoundingRectangle().contains(object2.getBoundingRectangle())) {
			return true;
		} else {
			return false;
		}
	}
}