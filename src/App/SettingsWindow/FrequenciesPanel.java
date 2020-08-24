package App.SettingsWindow;

import App.JMediaPlayer;
import javafx.collections.ObservableList;
import javafx.scene.media.EqualizerBand;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrequenciesPanel extends JPanel{
    private JMediaPlayer mediaPlayer;
    private ObservableList<EqualizerBand> bands;
    private final int BANDS_NUM = 7;
    private final int START_FREQ = 250;
    private JLabel minLabel;
    private JLabel maxLabel;
    private JLabel zeroLabel;
    private int [] frequenciesValues;
    public FrequenciesPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(0, 240, 700, 260);
        setLayout(null);
        setBackground(Color.black);
        frequenciesValues = new int[BANDS_NUM];
        for(int i=0;i<BANDS_NUM;++i)
        {
            frequenciesValues[i]=0;
        }
    }
    public void addSliders()
    {
        removeAll();
        minLabel = new JLabel(String.format("%d dB", (int)EqualizerBand.MIN_GAIN));
        minLabel.setForeground(new Color(46, 255, 0));
        minLabel.setBounds(48,129,80,40);
        add(minLabel);
        maxLabel = new JLabel(String.format("+%d dB", (int)EqualizerBand.MAX_GAIN));
        maxLabel.setForeground(new Color(46, 255, 0));
        maxLabel.setBounds(48,11,80,40);
        add(maxLabel);
        zeroLabel = new JLabel(String.format("%d dB", 0));
        zeroLabel.setForeground(new Color(46, 255, 0));
        zeroLabel.setBounds(61,50,80,40);
        add(zeroLabel);
        bands = mediaPlayer.getPlayer().getAudioEqualizer().getBands();
        bands.clear();
        double min = EqualizerBand.MIN_GAIN;
        double max = EqualizerBand.MAX_GAIN;
        double freq = START_FREQ;
        for (int j = 0; j < BANDS_NUM; ++j) {
            bands.add(new EqualizerBand(freq, freq / 2, frequenciesValues[j]));
            freq *= 2;
        }
        for(int i=0;i<bands.size();++i)
        {
            EqualizerBand band = bands.get(i);
            JSlider slider = new JSlider((int)min, (int)max, frequenciesValues[i]);
            slider.setName(Integer.toString(i));
            slider.setOpaque(false);
            slider.setUI(new CustomizedSliderUI(slider));
            slider.setBounds(i*60+80,20,60,140);
            slider.setOrientation(JSlider.VERTICAL);
            slider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    slider.repaint();
                }
            });
            slider.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    band.setGain(slider.getValue());
                    frequenciesValues[Integer.parseInt(slider.getName())] = slider.getValue();
                    for(int i=0;i<frequenciesValues.length;++i)
                    {
                        System.out.print(frequenciesValues[i]+" ");
                    }
                    System.out.println();
                }
            });
            add(slider);
            JLabel label = new JLabel(formatFrequency(band.getCenterFrequency()));
            label.setForeground(new Color(46, 255, 0));
            label.setBounds(i*60+90, 160, 60, 40);
            add(label);
        }
        repaint();
    }

    private String formatFrequency(double centerFrequency)
    {
        if(centerFrequency<1000)
        {
            return String.format("%.0f Hz", centerFrequency);
        }
        else
        {
            return String.format("%.1f kHz", centerFrequency/1000);
        }
    }
}
