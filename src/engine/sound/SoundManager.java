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

	public void init()  {

	}

	public void addSoundBuffer(SoundBuffer soundBuffer) {
		soundBufferList.add(soundBuffer);
	}
	
	public void addSoundSource(String name, SoundSource soundSource) {
		soundSourceMap.put(name, soundSource);
	}

	public void deleteSoundSource(String name) {
		soundSourceMap.remove(name);
	}
	
	public SoundListener getListener() {
		return listener;
	}

	public SoundSource getSoundSource(String name) {
		return soundSourceMap.get(name);
	}
	
	public void setListener(SoundListener sound) {
		listener = sound;
	}

	public void setAttenuationModel(int model) {
		alDistanceModel(model);
	}
	
	public void playSoundSource(String name) {
		SoundSource soundSource = this.soundSourceMap.get(name);
		if (soundSource != null && !soundSource.isPlaying()) {
			soundSource.play();
		}
	}
	
	public void stopSoundSource(String name){
		SoundSource soundSource = this.soundSourceMap.get(name);
		if (soundSource != null && soundSource.isPlaying()) {
			soundSource.stop();
		}
	}
	
	public void pauseSoundSource(String name){
		SoundSource soundSource = this.soundSourceMap.get(name);
		if (soundSource != null && soundSource.isPlaying()) {
			soundSource.pause();
		}
	}


	public void invokeSound(String soundName, boolean loop){
		setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
		Sound.setupSounds(this,"res/sounds/"+soundName+".ogg", soundName, loop);
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
		//if (context != NULL) {
			alcDestroyContext(context);
		//}
	//	if (device != NULL) {
			alcCloseDevice(device);
		//}
	}
}