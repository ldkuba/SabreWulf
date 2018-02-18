package engine.sound;

public class Sound {
	
	public static void setupSounds(SoundManager soundMgr, String path, String name, boolean loop) {
		SoundBuffer buffFire = new SoundBuffer(path);
		soundMgr.addSoundBuffer(buffFire);
		SoundSource sourceFire = new SoundSource(loop, false); //loop will normally be true
		sourceFire.setBuffer(buffFire.getBufferId());
		soundMgr.addSoundSource(name, sourceFire);
		sourceFire.play();
		soundMgr.setListener(new SoundListener());
	}
}
