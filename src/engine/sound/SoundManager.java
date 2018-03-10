package engine.sound;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	private SoundListener listener;

	private final List<SoundBuffer> soundBufferList;
	private final Map<String, SoundSource> soundSourceMap;
	private final String[] Sounds = {"countEnd", "count", "background/game", "background/lobby", "lockIn", "background/menu", "quit", "click"};
	
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
	}

	public void addSoundBuffer(SoundBuffer soundBuffer) {
		if (soundBuffer != null) {
			soundBufferList.add(soundBuffer);
		} else {
			System.err.println("No sound buffer to add");
		}
	}

	public void addSoundSource(String name, SoundSource soundSource) {
		if (name != null && soundSource != null) {
			soundSourceMap.put(name, soundSource);
		} else {
			System.err.println("Cannot add sound source");
		}
	}

	public void deleteSoundSource(String name) {
		if (name != null) {
			soundSourceMap.remove(name);
		} else {
			System.err.println("No sound source to delete");
		}
	}

	public SoundListener getListener() {
		return listener;
	}

	public SoundSource getSoundSource(String name) {
		if (name != null) {
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

	public void stopSoundSource(String name) {
		if (name != null) {
			SoundSource soundSource = this.soundSourceMap.get(name);
			if (soundSource != null && soundSource.isPlaying()) {
				soundSource.stop();
			} else {
				System.err.println("Cannot pause sound source: No source exists or sound is not playing");
			}
		} else {
			System.err.println("Cannot stop sound source: No source specified");
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

	public void invokeSound(String soundName, boolean loop) {
		if (doesSoundFileExist(soundName)) {
			setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
			setupSounds(this, "res/sounds/" + soundName + ".ogg", soundName, loop);
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

	public void setupSounds(SoundManager soundMgr, String path, String name, boolean loop) {
		if (soundMgr != null && path != null && name != null) {
			SoundBuffer buffer;
			try {
				buffer = new SoundBuffer(path);
				soundMgr.addSoundBuffer(buffer);
				SoundSource source = new SoundSource(loop, false); //normally true
				source.setBuffer(buffer.getBufferId());
				soundMgr.addSoundSource(name, source);
				source.play();
				soundMgr.setListener(new SoundListener());
			} catch (Exception e) {
				e.getMessage();
			}
		} else {
			System.err.println("Cannot set up the sound");
		}
	}
	
	public boolean doesSoundFileExist(String soundName){
		for (int i = 0; i < Sounds.length; i++){
			if (Sounds[i].equals(soundName)){
				return true;
			}
		}
		return false;
	}

}