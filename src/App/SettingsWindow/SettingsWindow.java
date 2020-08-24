package App.SettingsWindow;

import App.JMediaPlayer;
import com.sun.media.jfxmedia.events.AudioSpectrumEvent;
import com.sun.media.jfxmedia.events.AudioSpectrumListener;
import javafx.collections.ObservableList;
import javafx.scene.media.EqualizerBand;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SettingsWindow {
    private JMediaPlayer mediaPlayer;
    private JFrame frame;
    private JPanel titlePanel;
    private JPanel rotativeButtonsPanel;
    private JPanel frequenciesPanel;
    public SettingsWindow(JMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        initFrame();
        initTitlePanel();
        initRotativeButtonsPanel();
        initFrequenciesPanel();
    }

    public void initFrame()
    {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(320, 100, 700, 500);
        frame.setUndecorated(true);
        frame.getContentPane().setLayout(null);
        frame.getRootPane().setBorder(new LineBorder(Color.black));
        frame.setVisible(true);
    }

    public void initTitlePanel()
    {
        titlePanel = new TitlePanel(frame);
        frame.getContentPane().add(titlePanel);
    }

    public void initRotativeButtonsPanel()
    {
        rotativeButtonsPanel = new KnobsPanel(mediaPlayer);
        frame.getContentPane().add(rotativeButtonsPanel);
    }

    private void initFrequenciesPanel()
    {
        frequenciesPanel = new FrequenciesPanel(mediaPlayer);
        frame.getContentPane().add(frequenciesPanel);
    }

    public void addFrequenciesSliders()
    {
        ((FrequenciesPanel)frequenciesPanel).addSliders();
    }
}






