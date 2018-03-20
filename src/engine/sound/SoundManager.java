package engine.sound;

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

/**
 * SoundManager is used to manage all of the various sounds that exist in the
 * game. It contains methods to play, pause and stops sounds, as well as to add
 * and delete them from the game. It is used to add buffers and listeners to
 * each sound source as well.
 * 
 * @author bhavi
 *
 */
public class SoundManager {

	private long device;
	private long context;
	private boolean isMuted;
	private SoundListener listener;
	private final List<SoundBuffer> soundBufferList;
	private final Map<String, SoundSource> soundSourceMap;

	/**
	 * Create a Sound Manager
	 */
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

	/**
	 * Add a sound buffer to the list of known buffers
	 * 
	 * @param soundBuffer
	 */
	public void addSoundBuffer(SoundBuffer soundBuffer) {
		if (soundBuffer != null && !soundBufferList.contains(soundBuffer)) {
			soundBufferList.add(soundBuffer);
		} else {
			System.err.println("No sound buffer to add");
		}
	}
	
	/**
	 * Delete a sound buffer from the list of known buffers
	 * 
	 * @param soundBuffer
	 */
	public void deleteSoundBuffer(SoundBuffer soundBuffer) {
		if (soundBuffer != null && soundBufferList.contains(soundBuffer)) {
			soundBufferList.remove(soundBuffer);
		} else {
			System.err.println("No sound buffer to remove");
		}
	}

	/**
	 * Add a sound source to the map of known sound sources
	 * 
	 * @param name
	 * @param soundSource
	 */
	public void addSoundSource(String name, SoundSource soundSource) {
		if (name != null && soundSource != null) {
			if (!soundSourceMap.containsValue(soundSource)) {
				soundSourceMap.put(name, soundSource);
			} else {
				System.out.print("Trying to re-add a sound source");
			}
		} else {
			System.err.println("Cannot add sound source");
		}
	}

	/**
	 * Delete a sound source from the map of known sources
	 * 
	 * @param name
	 */
	public void deleteSoundSource(String name) {
		if (name != null && soundSourceMap.containsKey(name)) {
			soundSourceMap.remove(name);
		} else {
			System.out.print("No sound source to delete");
		}
	}

	/**
	 * Get the sound listener for the sound manager
	 * 
	 * @return
	 */
	public SoundListener getListener() {
		return listener;
	}

	/**
	 * Get the sound source for any given sound path
	 * 
	 * @param name
	 * @return
	 */
	public SoundSource getSoundSource(String name) {
		if (name != null && soundSourceMap.containsKey(name)) {
			return soundSourceMap.get(name);
		}
		return null;
	}

	/**
	 * Set the listener for the sound manager
	 * 
	 * @param sound
	 */
	public void setListener(SoundListener sound) {
		if (sound != null) {
			listener = sound;
		} else {
			System.err.println("Cannot set listener");
		}
	}

	/**
	 * Set the signal strength for a given model - for 3D
	 * 
	 * @param model
	 */
	public void setAttenuationModel(int model) {
		alDistanceModel(model);
	}

	/**
	 * Play the sound at the given sound path
	 * 
	 * @param name
	 */
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

	/**
	 * Pause the sound at the given sound path
	 * 
	 * @param name
	 */
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

	/**
	 * Stop the sound at the given sound path
	 * 
	 * @param name
	 */
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

	/**
	 * Calls the setUpSounds method for a sound so that it can be used via the
	 * playSoundSource, pauseSoundSource and stopSoundSource methods when
	 * necessary. This has to be called otherwise a sound cannot be used.
	 * 
	 * @param soundName
	 * @param loop
	 * @param autoPlay
	 */
	public void invokeSound(String soundName, boolean loop, boolean autoPlay) {
		if (soundName != null && SoundUtils.doesSoundFileExist(soundName)) {
			setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
			setupSounds(this, "res/sounds/" + soundName + ".ogg", soundName, loop, autoPlay);
		} else {
			System.err.println("Sound file does not exist");
		}
	}

	/**
	 * Sets up a sound by assigning it to a buffer, listener and sound source.
	 * This has to be called otherwise a sound cannot be used.
	 * 
	 * @param soundMgr
	 * @param path
	 * @param name
	 * @param loop
	 * @param autoPlay
	 */
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
	
	/**
	 * Clean up the sound manager
	 */
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

	/**
	 * Mute all sounds
	 */
	public void muteSounds() {
		this.isMuted = true;
	}

	/**
	 * Unmute all sounds
	 */
	public void unmuteSounds() {
		this.isMuted = false;
	}

	/**
	 * Find out whether or not the sounds in the game have been muted
	 * @return
	 */
	public boolean getIsMuted() {
		return this.isMuted;
	}
}