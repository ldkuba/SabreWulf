package engine.sound;
//Imports here

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.openal.*;
import org.lwjgl.openal.ALCCapabilities;

import static org.lwjgl.openal.ALC10.*;

import engine.maths.Mat4;

public class SoundManager {

	private long device;
	private long context;
	private Long longNull = null;
	private SoundListener listener;

	private final List<SoundBuffer> soundBufferList;
	private final Map<String, SoundSource> soundSourceMap;

	public SoundManager() {
		this.soundBufferList = new ArrayList<>();
		this.soundSourceMap = new HashMap<>();
	}

	public void init() throws Exception {
		this.device = alcOpenDevice((ByteBuffer) null);
		if (device == longNull) {
			throw new IllegalStateException("Failed to open the default OpenAL device.");
		}
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		this.context = alcCreateContext(device, (IntBuffer) null);
		if (context == longNull) {
			throw new IllegalStateException("Failed to create OpenAL context.");
		}
		alcMakeContextCurrent(context);
		AL.createCapabilities(deviceCaps);
	}
	//add other methods
	
	public void setListener(SoundListener lis){
		listener = lis;
	}
	
	public SoundListener getListener(){
		return listener;
	}
	
	public void addSoundSource(String sourceName, SoundSource sound){
		soundSourceMap.put(sourceName, sound);
	}
	
	public SoundSource getSoundSource(String sourceName){
		SoundSource source = soundSourceMap.get(sourceName);
		return source;
		
	}
	
	public void playSoundSource(String sourceName){
		SoundSource source = soundSourceMap.get(sourceName);
		if (source != null || !source.isPlaying()){
			source.play();
		}
	}
	
	public void removeSoundSource(String sourceName){
		
	}
}