package engine.sound;

/*
 * set up a sound - currently does not allow sounds at paticular positions
 */

public class SetSound {

	private SoundManager soundManager = new SoundManager();
	private SoundBuffer soundbuffer;

	public SetSound(String soundPath) {
		try {
			this.soundbuffer = new SoundBuffer(soundPath);
			setUpSound();
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	private void setUpSound() {
		soundManager.addBuffer(soundbuffer);
		SoundSource source = new SoundSource(true, false);
		source.setBuffer(soundbuffer.getBufferID());
		soundManager.addSoundSource("background", source);
		source.play();
		soundManager.setListener(new SoundListener());
	}
}
