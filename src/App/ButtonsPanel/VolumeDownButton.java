package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VolumeDownButton extends AbstractButton{
    public VolumeDownButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(390, 15, 33, 60);
        setIcon(new ImageIcon("images/volume_down.png"));
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                volumeDownControl(0.1);
                setIcon(new ImageIcon("images/volume_down_pressed.png"));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon("images/volume_down.png"));
            }
        });
    }

    public void volumeDownControl(double valueToPlusMinus)
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
                    float changedCalc = (float) ((float)currentVolume-(double)volumeToCut);
                    System.out.println(changedCalc);
                    if(changedCalc <= 0.001)
                    {
                        mediaPlayer.getButtonsPanel().getMuteButton().highlightVolumeIsMuted();
                    }
                    else if(changedCalc >= 0.8)
                    {
                        mediaPlayer.getButtonsPanel().getMaxVolumeButton().highlightVolumeIsNotMaximized();
                    }
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
