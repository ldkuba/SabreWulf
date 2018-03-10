package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.sound.SoundBuffer;
import engine.sound.SoundManager;
import engine.sound.SoundSource;

public class AudioTest {

	private String basePath = "res/sounds/";
	private SoundManager soundManager = new SoundManager();
	private SoundBuffer buffer;
	private SoundSource source;

	@Test
	public void bufferTest() {
		//ones that exist
		String path = basePath + "beep.ogg";
		buffer = new SoundBuffer(path);
		int actual = buffer.getBufferId();
		int expected = 2;
		assertEquals(expected, actual);	
		
		path = basePath + "background/Menu.ogg";
		buffer = new SoundBuffer(path);
		actual = buffer.getBufferId();
		expected = 3;
		assertEquals(expected, actual);	
		
		buffer.cleanup();
	}
	
	@Test
	public void sourceTest() {
		String path = basePath + "count.ogg";
		buffer = new SoundBuffer(path);
		source = new SoundSource(false, false);
		soundManager.addSoundSource("count", source);
		source.setBuffer(buffer.getBufferId());
		
		boolean playing = source.isPlaying(); //should not be playing initially
		assertEquals(false, playing);
		
		source.play(); //start playing
		playing = source.isPlaying();
		assertEquals(true, playing); 
		
		source.pause(); //pause
		playing = source.isPlaying();
		assertEquals(false, playing);
		
		source.stop(); //stop
		playing = source.isPlaying();
		assertEquals(false, playing);
		source.cleanup(); //clean up just to be nice
	}
	
	@Test
	public void existingSoundChecks(){
		boolean exists = soundManager.doesSoundFileExist("lemon");
		assertEquals(false, exists);
		
		exists = soundManager.doesSoundFileExist("lockIn");
		assertEquals(true, exists);
	}
}
