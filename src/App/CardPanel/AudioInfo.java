package App.CardPanel;

import App.JMediaPlayer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.IOException;
import java.util.Arrays;

class AudioInfo {
    private int[][] channelsWithSamples;
    AudioInfo(AudioInputStream ais, JMediaPlayer mediaPlayer) {
        if (ais.getFormat().getEncoding().toString().equals("MPEG1L3")) {
            AudioFormat decodedFormat = getDecodedFormat(ais);
            AudioInputStream decoded_ais = AudioSystem.getAudioInputStream(decodedFormat, ais);
            int songTimeInMilli = PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.getCurrentSongIndex()).getSongMillis();
            byte[] mybyte = new byte[songTimeInMilli * 300];
            try {
                int result = decoded_ais.read(mybyte);
                mybyte = Arrays.copyOf(mybyte, result);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    ais.close();
                    decoded_ais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            getSamples(decodedFormat, mybyte);
        }
    }

    private AudioFormat getDecodedFormat(AudioInputStream ais)
    {
        AudioFormat baseFormat = ais.getFormat();
        return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);
    }

    private void getSamples(AudioFormat decodedFormat, byte []mybyte)
    {
        int channelsNumber = decodedFormat.getChannels();
        int result = mybyte.length;
        channelsWithSamples = new int[channelsNumber][result/(decodedFormat.getChannels()*2)];
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

    int[][] getChannelsWithSamples()
    {
        return channelsWithSamples;
    }
}
