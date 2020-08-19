package App.CardPanel;

import App.JMediaPlayer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.util.*;

public class AudioInfo {
    private AudioInputStream ais;
    private int channelsNumber;
    private int[][] channelsWithSamples;
    private int samplesNumber;
    public AudioInfo(AudioInputStream in, JMediaPlayer mediaPlayer)
    {
        AudioInputStream din;
        AudioFormat baseFormat = in.getFormat();
        AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);
        din = AudioSystem.getAudioInputStream(decodedFormat, in);
        channelsNumber=decodedFormat.getChannels();
        int songTimeInMilli = PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.getCurrentSongIndex()).getSongMillis();
        byte [] mybyte = new byte[songTimeInMilli*300];
        int result=0;
        try {
            result=din.read(mybyte);
            mybyte=Arrays.copyOf(mybyte, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                din.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        channelsWithSamples = new int[decodedFormat.getChannels()][result/(decodedFormat.getChannels()*2)];
        int sampleIndex = 0;

        for (int t = 0; t < result;) {
            for (int channel = 0; channel < channelsNumber; channel++) {
                int low = mybyte[t];
                t++;
                int high = mybyte[t];
                t++;
                int sample = getSixteenBitSample(high, low);
                channelsWithSamples[channel][sampleIndex] = sample;
            }
            ++sampleIndex;
        }
    }

    private int getSixteenBitSample(int high, int low) {
        return (high << 8) + (low & 0x00ff);
    }

    public int getNumberOfChannels()
    {
        return channelsNumber;
    }

    public int[][] getChannelsWithSamples()
    {
        return channelsWithSamples;
    }

    public int getSamplesNumber()
    {
        return samplesNumber;
    }
}

//        Folosit la inceput pt fisier wav
//                this.ais = ais;
//        try {
//            samplesNumber = (int)ais.getFrameLength();
//            int frameSize =  ais.getFormat().getFrameSize();
//            byte [] bytes = new byte[samplesNumber * frameSize];
//            ais.read(bytes);
//
//            channelsNumber = ais.getFormat().getChannels();
//            channelsWithSamples = new int[channelsNumber][samplesNumber];
//            int sampleIndex = 0;
//
//            for (int t = 0; t < bytes.length;) {
//                for (int channel = 0; channel < channelsNumber; channel++) {
//                    int low = bytes[t];
//                    t++;
//                    int high = bytes[t];
//                    t++;
//                    int sample = getSixteenBitSample(high, low);
//                    channelsWithSamples[channel][sampleIndex] = sample;
//                }
//                sampleIndex++;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }