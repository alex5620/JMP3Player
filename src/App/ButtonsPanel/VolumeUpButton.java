package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VolumeUpButton extends AbstractButton{
    public VolumeUpButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setIcon(new ImageIcon("images/volume_up.png"));
        setBounds(425, 40, 33, 60);
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                volumeUpControl(0.1);
                setIcon(new ImageIcon("images/volume_up_pressed.png"));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon("images/volume_up.png"));
            }
        });
    }

    public void volumeUpControl(Double valueToPlusMinus)
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
                    if(currentVolume <= 0.01)
                    {
                        mediaPlayer.getButtonsPanel().getMuteButton().highlightVolumeIsNotMuted();
                    }
                    else if(currentVolume >= 0.9)
                    {
                        mediaPlayer.getButtonsPanel().getMaxVolumeButton().highlightVolumeIsMaximized();
                    }
                    Double volumeToCut = valueToPlusMinus;
                    float changedCalc = (float) ((float)currentVolume+(double)volumeToCut);
                    System.out.println(changedCalc);
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
}
