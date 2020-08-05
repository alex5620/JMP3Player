package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;

public class ButtonsPanel extends JPanel{
    private JMediaPlayer mediaPlayer;
    private AbstractButton repeatButton;
    private AbstractButton pauseButton;
    private AbstractButton playButton;
    private AbstractButton stopButton;
    private AbstractButton loadButton;
    private AbstractButton volumeDownButton;
    private AbstractButton volumeUpButton;
    private AbstractButton maxVolumeButton;
    private AbstractButton muteButton;
    public ButtonsPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
//        setBounds(0, 94, 550, 150);
        setBounds(0, 154, 550, 90);
        setBackground(new Color(7,63,86));
        setLayout(null);
        createButtons();
        addButtons();
    }

    private void createButtons()
    {
        repeatButton = new RepeatButton(mediaPlayer);
        pauseButton = new PauseButton(mediaPlayer);
        playButton = new PlayButton(mediaPlayer);
        stopButton = new StopButton(mediaPlayer);
        loadButton = new LoadButton(mediaPlayer);
        volumeDownButton = new VolumeDownButton(mediaPlayer);
        volumeUpButton = new VolumeUpButton(mediaPlayer);
        maxVolumeButton = new MaxVolumeButton(mediaPlayer);
        muteButton = new MuteButton(mediaPlayer);
    }

    private void addButtons()
    {
        add(repeatButton);
        add(pauseButton);
        add(playButton);
        add(stopButton);
        add(loadButton);
        add(volumeDownButton);
        add(volumeUpButton);
        add(maxVolumeButton);
        add(muteButton);
    }

    public void volumeControl(Double valueToPlusMinus)
    {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for(Mixer.Info mixerInfo: mixers)
        {
            Mixer mixer=AudioSystem.getMixer(mixerInfo);
            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            for(Line.Info lineInfo : lineInfos)
            {
                Line line = null;
                boolean opened = true;
                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    if(opened==false)
                    {
                        line.open();
                    }
                    FloatControl volControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    float currentVolume = volControl.getValue();
                    Double volumeToCut = valueToPlusMinus;
                    float changedCalc = (float)((double)volumeToCut);
                    volControl.setValue(changedCalc);
                }catch(Exception e)
                {
//					e.printStackTrace();
                }
                finally {
                    if(line != null && !opened)
                    {
                        line.close();
                    }
                }
            }
        }
    }

    public MuteButton getMuteButton()
    {
        return (MuteButton)muteButton;
    }

    public MaxVolumeButton getMaxVolumeButton()
    {
        return (MaxVolumeButton)maxVolumeButton;
    }
}

