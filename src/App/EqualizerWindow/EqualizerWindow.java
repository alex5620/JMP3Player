package App.EqualizerWindow;

import App.JMediaPlayer;
import App.EqualizerWindow.KnobsPanel.KnobsPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EqualizerWindow {
    private JMediaPlayer mediaPlayer;
    private JFrame frame;
    private KnobsPanel knobsPanel;
    private FrequenciesPanel frequenciesPanel;

    public EqualizerWindow(JMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        initFrame();
        initTitlePanel();
        initRotativeButtonsPanel();
        initFrequenciesPanel();
        initResetPanel();
    }

    private void initFrame()
    {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(385, 130, 590, 475);
        frame.setUndecorated(true);
        frame.getContentPane().setLayout(null);
        frame.getRootPane().setBorder(new LineBorder(Color.black));
        frame.setVisible(true);
    }

    private void initTitlePanel()
    {
        JPanel titlePanel = new TitlePanel(mediaPlayer, frame);
        frame.getContentPane().add(titlePanel);
    }

    private void initRotativeButtonsPanel()
    {
        knobsPanel = new KnobsPanel(mediaPlayer);
        frame.getContentPane().add(knobsPanel);
    }

    private void initFrequenciesPanel()
    {
        frequenciesPanel = new FrequenciesPanel(mediaPlayer);
        frame.getContentPane().add(frequenciesPanel);
    }

    private void initResetPanel()
    {
        JPanel resetPanel = new ResetPanel(this);
        frame.getContentPane().add(resetPanel);
    }

    public void addFrequenciesSliders()
    {
        (frequenciesPanel).addSliders();
    }

    public void updateFrequencies()
    {
        (frequenciesPanel).updateFrequencies();
    }

    KnobsPanel getKnobsPanel()
    {
        return knobsPanel;
    }

    FrequenciesPanel getFrequenciesPanel()
    {
        return frequenciesPanel;
    }
}






