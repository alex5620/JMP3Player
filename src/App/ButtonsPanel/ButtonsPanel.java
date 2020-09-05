package App.ButtonsPanel;

import App.Colors;
import App.JMediaPlayer;

import javax.swing.*;
import java.awt.*;

public class ButtonsPanel extends JPanel{
    private JMediaPlayer mediaPlayer;
    private AbstractButton repeatButton;
    private AbstractButton pauseButton;
    private AbstractButton playButton;
    private AbstractButton stopButton;
    private AbstractButton loadButton;
    private VolumeLabelObserver volumeValueLabel;
    private VolumeSubject volumeSubject;
    public ButtonsPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(0, 154, 550, 90);
        setBackground(Colors.color7_63_86);
        setLayout(null);
        createButtons();
        addButtons();
        volumeSubject = new VolumeSubject(mediaPlayer);
        add(volumeSubject);
        volumeValueLabel =new VolumeLabelObserver(volumeSubject, Integer.toString(mediaPlayer.getSettingsDatabaseDatabase().getSettingsInformation("volume")));
        add(volumeValueLabel);
        volumeSubject.attach(volumeValueLabel);
        VolumeImageObserver volumeImage = new VolumeImageObserver(volumeSubject);
        volumeSubject.attach(volumeImage);
        add(volumeImage);
        UIManager.put("ToolTip.background", new Color(7, 48, 65));
        UIManager.put("ToolTip.foreground", Colors.color34_202_237);
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

    public void setSliderValue(double value)
    {
        volumeSubject.setValue(value);
    }

    public int getVolumeValueLabel()
    {
        return Integer.parseInt(volumeValueLabel.getText());
    }

    public VolumeSubject getVolumeSubject()
    {
        return volumeSubject;
    }
}

