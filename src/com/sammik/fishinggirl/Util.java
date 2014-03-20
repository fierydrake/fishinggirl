package com.sammik.fishinggirl;


public class Util {
	public static float randomBetween(final float min, final float max) {
		return (float)(min + Math.random() * (max - min));
	}
}
