package engine.sound;

public class Sound {
	
	public static void setupSounds(SoundManager soundMgr, String path, String name, boolean loop) {
		SoundBuffer buffer = new SoundBuffer(path);
		soundMgr.addSoundBuffer(buffer);
		SoundSource source = new SoundSource(loop, false); //loop will normally be true
		source.setBuffer(buffer.getBufferId());
		soundMgr.addSoundSource(name, source);
		source.play();
		soundMgr.setListener(new SoundListener());
	}
}
