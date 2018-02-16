package engine.sound;

public class Sound {
	
	public static void setupSounds(SoundManager soundMgr, String path, String name) {
		SoundBuffer buffFire = new SoundBuffer(path);
		soundMgr.addSoundBuffer(buffFire);
		SoundSource sourceFire = new SoundSource(true, false);
		sourceFire.setBuffer(buffFire.getBufferId());
		soundMgr.addSoundSource(name, sourceFire);
		sourceFire.play();
		soundMgr.setListener(new SoundListener());
	}
}
