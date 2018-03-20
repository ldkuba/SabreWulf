package engine.sound;

import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.entity.component.AbstractComponent;

import static org.lwjgl.openal.AL10.*;

/**
 * Sound Sources are used to emit sound and alter various components of the
 * sound such as its expected position and gain,
 * 
 * @author bhavi
 *
 */
public class SoundSource {

	private final int sourceId;

	/**
	 * Create a new sound source. The majority of sound sources will have a
	 * relative that equates to true
	 * 
	 * @param loop
	 * @param relative
	 */
	public SoundSource(boolean loop, boolean relative) {
		sourceId = alGenSources();
		if (loop) {
			alSourcei(sourceId, AL_LOOPING, AL_TRUE);
		}
		if (relative) {
			alSourcei(sourceId, AL_SOURCE_RELATIVE, AL_TRUE);
		}
	}

	/**
	 * Set the sound buffer, given its id, for a sound source
	 * 
	 * @param bufferId
	 */
	public void setBuffer(int bufferId) {
		stop();
		alSourcei(sourceId, AL_BUFFER, bufferId);
	}

	/**
	 * Set the gain (intensity) of the sound from the source
	 * 
	 * @param gain
	 */
	public void setGain(float gain) {
		alSourcef(sourceId, AL_GAIN, gain);
	}

	/**
	 * Set the location of the sound source
	 * 
	 * @param position
	 */
	public void setPosition(Vec2 position) {
		alSource3f(sourceId, AL_POSITION, position.getX(), position.getY(), 0);
	}

	public void setProperty(int param, float value) {
		alSourcef(sourceId, param, value);
	}

	/**
	 * Used to specify the speed at which the source is moving.
	 * 
	 * @param speed
	 */
	public void setSpeed(Vec3 speed) {
		alSource3f(sourceId, AL_VELOCITY, speed.getX(), speed.getY(), speed.getZ());
	}

	/**
	 * Play a sound source
	 */
	public void play() {
		alSourcePlay(sourceId);
	}

	/**
	 * Check if a sound source is already playing or not
	 * 
	 * @return
	 */
	public boolean isPlaying() {
		return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
	}

	/**
	 * Pause the sound source
	 */
	public void pause() {
		alSourcePause(sourceId);
	}

	/**
	 * Stop the sound source from playing
	 */
	public void stop() {
		alSourceStop(sourceId);
	}

	/**
	 * Delete all sound sources
	 */
	public void cleanup() {
		stop();
		alDeleteSources(sourceId);
	}
}