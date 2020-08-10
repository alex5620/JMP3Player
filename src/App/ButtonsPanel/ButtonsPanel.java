package App.ButtonsPanel;

import App.JMediaPlayer;
import App.TimePanel.CustomizedSliderUI;

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
    private JLabel volumeValue;
    private JLabel volumeLabel;
    private JSlider jSlider;
    private int volumeLevel;
    public ButtonsPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(0, 154, 550, 90);
        setBackground(new Color(7,63,86));
        setLayout(null);
        createButtons();
        addButtons();
        initJSlider();
        volumeValue=new JLabel("50");
        volumeLevel=3;
        volumeValue.setBounds(523, 13, 30, 60);
        volumeValue.setForeground(new Color(34,202,237));
        volumeLabel = new JLabel();
        volumeLabel.setIcon(new ImageIcon("images/volume_up.png"));
        volumeLabel.setBounds(390, 15, 33, 60);
        add(volumeValue);
        add(volumeLabel);
    }

    public void initJSlider()
    {
        jSlider = new JSlider();
        jSlider.setBounds(425, 15, 95, 60);
        jSlider.setValue(50);
        jSlider.setMaximum(100);
        jSlider.setOpaque(false);
        jSlider.setUI(new CustomizedSliderUI(jSlider, 12, 3));
        jSlider.addChangeListener(e -> {
            int currentVolumeValue= jSlider.getValue();
            volumeControl(((double)currentVolumeValue)/100);
            volumeValue.setText(Integer.toString(currentVolumeValue));
            changeVolumeImage(currentVolumeValue);
        });
        add(jSlider);
    }

    private void changeVolumeImage(double newVolumeValue)
    {
        if(newVolumeValue==0)
        {
            if(volumeLevel!=0)
            {
                volumeLevel=0;
                volumeLabel.setIcon(new ImageIcon("images/mute.png"));
            }
        }
        else if (newVolumeValue >=1 && newVolumeValue<50)
        {
            if(volumeLevel!=1)
            {
                volumeLevel=1;
                volumeLabel.setIcon(new ImageIcon("images/volume_down.png"));
            }
        }else if (newVolumeValue >= 50 && newVolumeValue <= 99)
        {
            if(volumeLevel!=2)
            {
                volumeLevel=2;
                volumeLabel.setIcon(new ImageIcon("images/volume_up.png"));
            }
        }else if(newVolumeValue == 100)
        {
            if(volumeLevel!=3)
            {
                volumeLevel=3;
                volumeLabel.setIcon(new ImageIcon("images/volume_full.png"));
            }
        }
    }

    private void createButtons()
    {
        repeatButton = new RepeatButton(mediaPlayer);
        pauseButton = new PauseButton(mediaPlayer);
        playButton = new PlayButton(mediaPlayer);
        stopButton = new StopButton(mediaPlayer);
        loadButton = new LoadButton(mediaPlayer);
    }

    private void addButtons()
    {
        add(repeatButton);
        add(pauseButton);
        add(playButton);
        add(stopButton);
        add(loadButton);
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
}

