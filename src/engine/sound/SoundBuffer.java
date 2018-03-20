package engine.sound;

import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ShortBuffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Create a sound buffer for any given sound. The buffer is used to store audio
 * data.
 * 
 * @author bhavi
 *
 */
public class SoundBuffer {

	private final int bufferId;
	private ShortBuffer pcm = null;
	private ByteBuffer vorbis = null;

	/**
	 * Create a sound buffer for the a given sound file
	 * @param file
	 */
	public SoundBuffer(String file) {
		this.bufferId = alGenBuffers();
		STBVorbisInfo info = STBVorbisInfo.malloc();
		ShortBuffer pcm = readVorbis(file, 32 * 1024, info);
		alBufferData(bufferId, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm, info.sample_rate());

	}

	/**
	 * Get the buffer id for the buffer that stores the data for an audio file
	 * @return
	 */
	public int getBufferId() {
		return bufferId;
	}

	/**
	 * Clean all buffers up by deleting them and freeing used memory
	 */
	public void cleanup() {
		alDeleteBuffers(this.bufferId);
		MemoryUtil.memFree(pcm);
	}

	/**
	 * Read the .ogg file in and return it in the form of a buffer
	 * @param resource
	 * @param bufferSize
	 * @param info
	 * @return
	 */
	private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) {
		ShortBuffer temp = null;
		MemoryStack stack = MemoryStack.stackPush();
		vorbis = SoundUtils.ioResourceToByteBuffer(resource, bufferSize);
		IntBuffer error = stack.mallocInt(1);
		long decoder = stb_vorbis_open_memory(vorbis, error, null);
		if (decoder == NULL) {
			throw new RuntimeException("Failed to open Vorbis file. Error: " + error.get(0));
		}
		stb_vorbis_get_info(decoder, info);
		int channels = info.channels();
		int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);
		pcm = MemoryUtil.memAllocShort(lengthSamples);
		pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
		stb_vorbis_close(decoder);
		temp = pcm;
		return temp;
	}
}