package engine.sound;

import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.entity.component.AbstractComponent;

import static org.lwjgl.openal.AL10.*;

public class SoundSource extends AbstractComponent {

	private final int sourceId;

	public SoundSource(boolean loop, boolean relative) {
		sourceId = alGenSources();
		if (loop) {
			alSourcei(sourceId, AL_LOOPING, AL_TRUE);
		}
		if (relative) {
			alSourcei(sourceId, AL_SOURCE_RELATIVE, AL_TRUE);
		}
	}

	public void setBuffer(int bufferId) {
		stop();
		alSourcei(sourceId, AL_BUFFER, bufferId);
	}

	public void setGain(float gain) {
		alSourcef(sourceId, AL_GAIN, gain);
	}

	public void setPosition(Vec2 position) {
		alSource3f(sourceId, AL_POSITION, position.getX(), position.getY(), 0);
	}
	
	public void setProperty(int param, float value) {
		alSourcef(sourceId, param, value);
	}

	public void setSpeed(Vec3 speed) {
		alSource3f(sourceId, AL_VELOCITY, speed.getX(), speed.getY(), speed.getZ());
	}

	public void play() {
		alSourcePlay(sourceId);
	}

	public boolean isPlaying() {
		return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
	}

	public void pause() {
		alSourcePause(sourceId);
	}

	public void stop() {
		alSourceStop(sourceId);
	}

	public void cleanup() {
		stop();
		alDeleteSources(sourceId);
	}
}