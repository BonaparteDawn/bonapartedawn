package github.bonapartedawn.common.utils;

import org.springframework.util.Assert;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 音乐工具类
 * @author Fuzhong.Yan
 */
public class MusicUtil {
    /**
     * 播放音乐
     * @param musicPath
     * @throws Exception
     */
    public static void play(String musicPath) throws Exception{
        Assert.hasLength(musicPath, "musicUtil_musicPath_len0");
        File file = new File(musicPath);
        if (file.exists()) {
            play(file);
        }else {
            throw new Exception("music_exist_exception");
        }
    }
    /**
     * 播放音乐
     * @param music
     * @throws Exception
     */
    public static void play(File music) throws Exception{
        Assert.notNull(music, "musicUtil_music_null");
        if (music.exists()) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(music));
            play(bufferedInputStream);
            bufferedInputStream.close();
        }else {
            throw new Exception("music_exist_exception");
        }
    }
    /**
     * 播放音乐
     * @param inputStream
     * @throws Exception
     */
    public static void play(InputStream inputStream) throws Exception{
        Assert.notNull(inputStream, "musicUtil_inputStream_null");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        AudioFormat audioFormat = audioInputStream.getFormat();
        if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
        {
            audioFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    audioFormat.getSampleRate(),
                    16,
                    audioFormat.getChannels(),
                    audioFormat.getChannels() * 2,
                    audioFormat.getSampleRate(),
                    false);
            audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
        }
        DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
        sourceDataLine.open(audioFormat, sourceDataLine.getBufferSize());
        sourceDataLine.start();
        int bufferSize = (int) audioFormat.getSampleRate() * audioFormat.getFrameSize();
        byte[] buffer = new byte[bufferSize];
        int bytesRead = 0;
        while (bytesRead >= 0) {
            bytesRead = audioInputStream.read(buffer, 0, buffer.length);
            if (bytesRead >= 0) {
                sourceDataLine.write(buffer, 0, bytesRead);
            }
        }
        sourceDataLine.drain();
        sourceDataLine.close();
        audioInputStream.close();
    }
}
