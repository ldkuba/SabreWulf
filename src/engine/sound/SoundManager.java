package engine.sound;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.openal.*;
import org.lwjgl.openal.ALCCapabilities;

import static org.lwjgl.openal.ALC10.*;

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
		SoundSource source = null;
		if (sourceName != null){
			source = soundSourceMap.get(sourceName);
		}
		return source;		
	}
	
	public void playSoundSource(String sourceName){
		SoundSource source = soundSourceMap.get(sourceName);
		if (source != null && !source.isPlaying()){
			source.play();
		}
	}
	
	public void removeSoundSource(String sourceName){
		if(sourceName != null){
			soundSourceMap.remove(sourceName);
		}
	}
	
	public void addBuffer(SoundBuffer buffer){
		soundBufferList.add(buffer);
	}
	
	public void cleanup(){
		for (SoundSource source :  soundSourceMap.values()){
			source.cleanup();
		}
		soundSourceMap.clear();
		for (SoundBuffer buffer : soundBufferList){
			buffer.cleanup();
		}
		soundBufferList.clear();
		if(context != longNull){
			alcDestroyContext(context);
		} 
		if (device != longNull){
			alcCloseDevice(device);
		}
	}
	
	
}