package engine.sound;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class SoundManager {

	private long device;
	private long context;
	private boolean isMuted;
	private SoundListener listener;
	//private OutputStream os = new ByteArrayOutputStream(); // for junit testing
	private final List<SoundBuffer> soundBufferList;
	private final Map<String, SoundSource> soundSourceMap;

	public SoundManager() {
		soundBufferList = new ArrayList<>();
		soundSourceMap = new HashMap<>();
		device = alcOpenDevice((ByteBuffer) null);
		if (device == NULL) {
			throw new IllegalStateException("Failed to open OpenAL device");
		}
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		context = alcCreateContext(device, (IntBuffer) null);
		if (context == NULL) {
			throw new IllegalStateException("Failed to create OpenAL context");
		}
		alcMakeContextCurrent(context);
		AL.createCapabilities(deviceCaps);
		this.isMuted = false;
	}

	public void addSoundBuffer(SoundBuffer soundBuffer) {
		if (soundBuffer != null) {
			soundBufferList.add(soundBuffer);
		} else {
			System.err.println("No sound buffer to add");
		}
	}

	public void addSoundSource(String name, SoundSource soundSource) {
		//PrintStream ps = new PrintStream(os);
		//System.setOut(ps);
		if (name != null && soundSource != null) {
			if (!soundSourceMap.containsValue(soundSource)) {
				soundSourceMap.put(name, soundSource);
			} else {
				System.out.print("Trying to re-add a sound source");
			}
		} else {
			System.err.println("Cannot add sound source");
		}
		//PrintStream originalOut = System.out;
		//System.setOut(originalOut);
	}

	//public OutputStream getOS() {
	//	return os;
	//}

	public void deleteSoundSource(String name) {
		//PrintStream ps = new PrintStream(os);
		//System.setOut(ps);
		if (name != null && soundSourceMap.containsKey(name)) {
			soundSourceMap.remove(name);
		} else {
			System.out.print("No sound source to delete");
		}
		//PrintStream originalOut = System.out;
		//System.setOut(originalOut);
	}

	public SoundListener getListener() {
		return listener;
	}

	public SoundSource getSoundSource(String name) {
		if (name != null && soundSourceMap.containsKey(name)) {
			return soundSourceMap.get(name);
		}
		return null;
	}

	public void setListener(SoundListener sound) {
		if (sound != null) {
			listener = sound;
		} else {
			System.err.println("Cannot set listener");
		}
	}

	public void setAttenuationModel(int model) {
		alDistanceModel(model);
	}

	public void playSoundSource(String name) {
		if (name != null) {
			SoundSource soundSource = this.soundSourceMap.get(name);
			if (soundSource != null && !soundSource.isPlaying()) {
				soundSource.play();
			} else {
				System.err.println("Cannot play sound source: No source exists or sound is already playing");
			}
		} else {
			System.err.println("Cannot play sound source: No source specified");
		}
	}

	public void pauseSoundSource(String name) {
		if (name != null) {
			SoundSource soundSource = this.soundSourceMap.get(name);
			if (soundSource != null && soundSource.isPlaying()) {
				soundSource.pause();
			} else {
				System.err.println("Cannot pause sound source: No source exists or sound is not playing");
			}
		} else {
			System.err.println("Cannot pause sound source: No source specified");
		}
	}

	public void stopSoundSource(String name) {
		if (name != null) {
			SoundSource soundSource = this.soundSourceMap.get(name);
			if (soundSource != null && soundSource.isPlaying()) {
				soundSource.stop();
			} else {
				System.err.println("Cannot stop sound source: No source exists or sound is not playing");
			}
		} else {
			System.err.println("Cannot stop sound source: No source specified");
		}
	}

	public void invokeSound(String soundName, boolean loop, boolean autoPlay) {
		if (soundName != null && SoundUtils.doesSoundFileExist(soundName)) {
			setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
			setupSounds(this, "res/sounds/" + soundName + ".ogg", soundName, loop, autoPlay);
		} else {
			System.err.println("Sound file does not exist");
		}
	}

	public void clean() {
		for (SoundSource soundSource : soundSourceMap.values()) {
			soundSource.cleanup();
		}
		soundSourceMap.clear();
		for (SoundBuffer soundBuffer : soundBufferList) {
			soundBuffer.cleanup();
		}
		soundBufferList.clear();
		alcDestroyContext(context);
		alcCloseDevice(device);
	}

	public void setupSounds(SoundManager soundMgr, String path, String name, boolean loop, boolean autoPlay) {
		if (soundMgr != null && path != null && name != null) {
			SoundBuffer buffer;
			try {
				buffer = new SoundBuffer(path);
				soundMgr.addSoundBuffer(buffer);
				SoundSource source = new SoundSource(loop, false); // normally
																	// true
				source.setBuffer(buffer.getBufferId());
				soundMgr.addSoundSource(name, source);
				if (!isMuted && autoPlay) {
					source.play();
				}
				soundMgr.setListener(new SoundListener());
			} catch (Exception e) {
				System.out.println("cannot set up sound because : " + e.getMessage());
			}
		} else {
			System.err.println("Cannot set up the sound");
		}
	}

	public void muteSounds() {
		this.isMuted = true;
	}

	public void unmuteSounds() {
		this.isMuted = false;
	}

	public boolean getIsMuted() {
		return this.isMuted;
	}
}