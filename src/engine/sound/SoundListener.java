package engine.sound;

import engine.entity.component.AbstractComponent;
import engine.maths.Vec3;

import static org.lwjgl.openal.AL10.*;

public class SoundListener extends AbstractComponent {

	public SoundListener() {

	}

	public SoundListener(Vec3 position) {
		alListener3f(AL_POSITION, position.getX(), position.getY(), position.getZ());
		alListener3f(AL_VELOCITY, 0, 0, 0);

	}

	public void setSpeed(Vec3 speed) {
		alListener3f(AL_VELOCITY, speed.getX(), speed.getY(), speed.getZ());
	}

	public void setPosition(Vec3 position) {
		alListener3f(AL_POSITION, position.getX(), position.getY(), position.getZ());
	}

	public void setOrientation(Vec3 at, Vec3 up) {
		float[] data = new float[6];
		data[0] = at.getX();
		data[1] = at.getY();
		data[2] = at.getZ();
		data[3] = up.getX();
		data[4] = up.getY();
		data[5] = up.getZ();
		alListenerfv(AL_ORIENTATION, data);
	}
}