package engine.sound;

import engine.entity.component.AbstractComponent;
import engine.maths.Vec3;
import static org.lwjgl.openal.AL10.*;

/**
 * Creates a Sound Listener component for the sound manager. It is used to
 * specify where given sounds should be heard
 * 
 * @author bhavi
 *
 */
public class SoundListener extends AbstractComponent {

	/**
	 * Create an empty sound listener
	 */
	public SoundListener() {

	}

	/**
	 * Create a sound listener at a given vector position
	 * 
	 * @param position
	 */
	public SoundListener(Vec3 position) {
		alListener3f(AL_POSITION, position.getX(), position.getY(), position.getZ());
		alListener3f(AL_VELOCITY, 0, 0, 0);
	}

	/**
	 * Set the position for the sound listener
	 * 
	 * @param position
	 */
	public void setPosition(Vec3 position) {
		alListener3f(AL_POSITION, position.getX(), position.getY(), position.getZ());
	}

	/**
	 * Set the relative speed of the listener
	 * 
	 * @param speed
	 */
	public void setSpeed(Vec3 speed) {
		alListener3f(AL_VELOCITY, speed.getX(), speed.getY(), speed.getZ());
	}

	/**
	 * Set the orientation of the listener
	 * 
	 * @param pos
	 * @param up
	 */
	public void setOrientation(Vec3 pos, Vec3 up) {
		float[] data = new float[6];
		data[0] = pos.getX();
		data[1] = pos.getY();
		data[2] = pos.getZ();
		data[3] = up.getX();
		data[4] = up.getY();
		data[5] = up.getZ();
		alListenerfv(AL_ORIENTATION, data);
	}

	/**
	 * Update the listener
	 */
	@Override
	public void update() {

	}
}