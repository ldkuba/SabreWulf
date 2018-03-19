package tests;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;

import engine.sound.SoundBuffer;
import engine.sound.SoundManager;
import engine.sound.SoundSource;
import engine.sound.SoundUtils;

public class AudioTest {

	private String basePath = "res/sounds/";
	private SoundManager soundManager = new SoundManager();
	private SoundBuffer buffer;
	private SoundSource source;

	@Test
	public void bufferTest() {
		// ones that exist
		String path = basePath + "beep.ogg";
		buffer = new SoundBuffer(path);
		int actual = buffer.getBufferId();
		int expected = 1;
		assertEquals(expected, actual);

		path = basePath + "background/Menu.ogg";
		buffer = new SoundBuffer(path);
		actual = buffer.getBufferId();
		expected = 2;
		assertEquals(expected, actual);
		
		path = basePath + "attack/scratch.ogg";
		buffer = new SoundBuffer(path);
		actual = buffer.getBufferId();
		expected = 3;
		assertEquals(expected, actual);

		buffer.cleanup();
		soundManager.clean();
	}

	@Test
	public void sourceTest() {
		String path = basePath + "count.ogg";
		buffer = new SoundBuffer(path);
		source = new SoundSource(false, false);
		soundManager.addSoundSource("count", source);
		source.setBuffer(buffer.getBufferId());

		boolean playing = source.isPlaying(); // should not be playing initially
		assertEquals(false, playing);

		source.play(); // start
		playing = source.isPlaying();
		assertEquals(true, playing);
		
		source.stop(); // stop
		playing = source.isPlaying();
		assertEquals(false, playing);
		
		source.play(); // play again
		playing = source.isPlaying();
		assertEquals(true, playing);
		
		source.play(); // try play even though its playing
		playing = source.isPlaying();
		assertEquals(true, playing);
		
		source.pause(); // pause
		playing = source.isPlaying();
		assertEquals(false, playing);
		
		//then stop
		source.stop(); 
		playing = source.isPlaying();
		assertEquals(false, playing);
		
		source.stop(); //try to stop again
		playing = source.isPlaying();
		assertEquals(false, playing);
		
		source.pause(); //try and pause even though its stopped
		playing = source.isPlaying();
		assertEquals(false, playing);
		
		source.cleanup(); 
		soundManager.clean();
	}

	@Test
	public void soundExistsTest() {
		boolean exists = SoundUtils.doesSoundFileExist("lemon");
		assertEquals(false, exists);

		exists = SoundUtils.doesSoundFileExist("lockIn");
		assertEquals(true, exists);
	}

	@Test
	public void managerTest() throws IOException {
		String path = basePath + "count.ogg";
		buffer = new SoundBuffer(path);
		source = new SoundSource(false, false);
		SoundSource check = soundManager.getSoundSource("background/lobby");
		assertNull(check); // source hasn't been added

		soundManager.addSoundSource("background/lobby", source);
		check = soundManager.getSoundSource("background/lobby");
		assertNotNull(check);

		soundManager.deleteSoundSource("background/lobby");
		check = soundManager.getSoundSource("background/lobby");
		assertNull(check); // its been deleted
		
		//re-add that same sound - should not be blocked
		soundManager.addSoundSource("background/lobby", source);
		check = soundManager.getSoundSource("background/lobby");
		assertNotNull(check);
		
		check = soundManager.getSoundSource("hiiiiiii");
		assertNull(check);
		
		//try and add the same source again
		/*soundManager.addSoundSource("background/lobby", source);
		check = soundManager.getSoundSource("background/lobby");
		assertEquals("Trying to re-add a sound source", soundManager.getOS().toString());
		
		soundManager.getOS().close();	*/
		soundManager.clean();
	}
}
