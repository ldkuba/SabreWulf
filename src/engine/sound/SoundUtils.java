package engine.sound;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import org.lwjgl.BufferUtils;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;

import static org.lwjgl.BufferUtils.*;

public class SoundUtils {
	
	private static final String[] Sounds = {"countEnd", "count", "background/game", "background/lobby", "lockIn", "background/menu", "quit", "click", 
			"movement/forest", "movement/grass", "movement/footstep", "attack/a1", "attack/a2", "attack/s1", "attack/s2", "attack/s3", "attack/s4",
			"attack/m1", "attack/m2", "item/i1", "item/i2"};
	
	public static  boolean doesSoundFileExist(String soundName){
		for (int i = 0; i < Sounds.length; i++){
			if (Sounds[i].equals(soundName)){
				return true;
			}
		}
		return false;
	}
	
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) {
		ByteBuffer buffer = null;
		Path path = Paths.get(resource);
		if (Files.isReadable(path)) {
			try (SeekableByteChannel fc = Files.newByteChannel(path)) {
				buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
				while (fc.read(buffer) != -1)
					;
			} catch (IOException e) {
				e.getMessage();
			}
		} else {
			try {
				InputStream source = SoundUtils.class.getResourceAsStream(resource);
				ReadableByteChannel rbc = Channels.newChannel(source);
				buffer = createByteBuffer(bufferSize);

				while (true) {
					int bytes = rbc.read(buffer);
					if (bytes == -1) {
						break;
					}
					if (buffer.remaining() == 0) {
						buffer = resizeBuffer(buffer, buffer.capacity() * 2);
					}
				}
			} catch (IOException e) {
				e.getMessage();
			}
		}

		buffer.flip();
		return buffer;
	}

	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}

}