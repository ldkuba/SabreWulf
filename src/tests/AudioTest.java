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

		source.play(); // start playing
		playing = source.isPlaying();
		assertEquals(true, playing);

		source.pause(); // pause
		playing = source.isPlaying();
		assertEquals(false, playing);

		source.stop(); // stop
		playing = source.isPlaying();
		assertEquals(false, playing);
		source.cleanup(); // clean up just to be nice
		soundManager.clean();
	}

	@Test
	public void soundExistsTest() {
		boolean exists = soundManager.doesSoundFileExist("lemon");
		assertEquals(false, exists);

		exists = soundManager.doesSoundFileExist("lockIn");
		assertEquals(true, exists);
	}

	@Test
	public void managerTest() {
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
		soundManager.clean();
	}
}
