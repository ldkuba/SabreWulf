package engine.sound;

import static org.lwjgl.openal.AL10.*;

import engine.entity.component.AbstractComponent;
import engine.maths.Vec3;

public class SoundSource extends AbstractComponent {
	private final int sourceId;

	public SoundSource(boolean loop, boolean relative) {
		this.sourceId = alGenSources();
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

	public void setPosition(Vec3 position) {
		alSource3f(sourceId, AL_POSITION, position.getX(), position.getY(), position.getZ());
	}

	public void setSpeed(Vec3 speed) {
		alSource3f(sourceId, AL_VELOCITY, speed.getX(), speed.getY(), speed.getZ());
	}

	public void setGain(float gain) {
		alSourcef(sourceId, AL_GAIN, gain);
	}

	public void setProperty(int param, float value) {
		alSourcef(sourceId, param, value);
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
