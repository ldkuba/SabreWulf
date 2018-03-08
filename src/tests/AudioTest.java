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
	/*
	 * public static void setupSounds(SoundManager soundMgr, String path, String name, boolean loop) {
		SoundBuffer buffer = new SoundBuffer(path);
		soundMgr.addSoundBuffer(buffer);
		SoundSource source = new SoundSource(loop, false); //loop will normally be true
		source.setBuffer(buffer.getBufferId());
		soundMgr.addSoundSource(name, source);
		source.play();
		soundMgr.setListener(new SoundListener());
		}
	 * 
	 * 
	 * 
	 */
	@Test
	public void bufferTest() {
		//ones that exist
		String path = basePath + "beep.ogg";
		buffer = new SoundBuffer(path);
		int actual = buffer.getBufferId();
		int expected = 1;
		assertEquals(expected, actual);	
		
		path = basePath + "background/Menu.ogg";
		buffer = new SoundBuffer(path);
		actual = buffer.getBufferId();
		expected = 2;
		System.out.println(actual);
		assertEquals(expected, actual);	
	}
}
