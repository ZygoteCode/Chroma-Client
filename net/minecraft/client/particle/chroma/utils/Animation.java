package net.minecraft.client.particle.chroma.utils;

public class Animation {
	private static float defaultSpeed = 0.125f;

	public static float moveTowards(float current, float end, float minSpeed) {
		return moveTowards(current, end, defaultSpeed, minSpeed);
	}

	public static float moveTowards(float current, float end, float smoothSpeed, float minSpeed) {
		float movement = (end - current) * smoothSpeed;

		if (movement > 0) {
			movement = Math.max(minSpeed, movement);
			movement = Math.min(end - current, movement);
		} else if (movement < 0) {
			movement = Math.min(-minSpeed, movement);
			movement = Math.max(end - current, movement);
		}

		return current + movement;
	}
}