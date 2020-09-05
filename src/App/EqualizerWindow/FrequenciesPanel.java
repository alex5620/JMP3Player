package App.EqualizerWindow;

import App.Colors;
import App.JMediaPlayer;
import javafx.collections.ObservableList;
import javafx.scene.media.EqualizerBand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class FrequenciesPanel extends JPanel{
    private static int [] frequenciesValues;
    private JMediaPlayer mediaPlayer;
    private ObservableList<EqualizerBand> bands;
    private JSlider [] sliders;
    private static final int BANDS_NUM = 10;

    static{
        frequenciesValues = new int[BANDS_NUM];
        for(int i=0;i<BANDS_NUM;++i)
        {
            frequenciesValues[i]= 0;
        }
    }

    FrequenciesPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(0, 230, 590, 185);
        setLayout(null);
        setBackground(Color.black);
        addMinLabel();
        addMaxLabel();
        addZeroLabel();
        addFrequenciesLabels();
        sliders = new JSlider[BANDS_NUM];
    }

    private void addMinLabel()
    {
        JLabel minLabel = new JLabel(String.format("%d dB", (int) EqualizerBand.MIN_GAIN));
        minLabel.setForeground(Colors.color46_255_0);
        minLabel.setBounds(12,114,80,40);
        add(minLabel);
    }

    private void addMaxLabel()
    {
        JLabel maxLabel = new JLabel(String.format("+%d dB", (int) EqualizerBand.MAX_GAIN));
        maxLabel.setForeground(Colors.color46_255_0);
        maxLabel.setBounds(10,0,80,30);
        add(maxLabel);
    }

    private void addZeroLabel()
    {
        JLabel zeroLabel = new JLabel(String.format("%d dB", 0));
        zeroLabel.setForeground(Colors.color46_255_0);
        zeroLabel.setBounds(23,35,80,40);
        add(zeroLabel);
    }

    private void addFrequenciesLabels()
    {
        for(int i=0;i<BANDS_NUM;++i) {
            JLabel label = new JLabel(formatFrequency(32*Math.pow(2, i)));
            label.setForeground(Colors.color46_255_0);
            label.setBounds(i * 52 + 65, 145, 50, 40);
            add(label);
        }
    }

    void addSliders()
    {
        if(mediaPlayer.getPlayer()==null)
        {
            return;
        }
        bands = mediaPlayer.getPlayer().getAudioEqualizer().getBands();
        bands.clear();
        double min = EqualizerBand.MIN_GAIN;
        double max = EqualizerBand.MAX_GAIN;
        double freq = 32;
        for (int j = 0; j < BANDS_NUM; ++j) {
            bands.add(new EqualizerBand(freq, freq / 2, frequenciesValues[j]));
            freq *= 2;
        }
        for(int i=0;i<BANDS_NUM;++i)
        {
            EqualizerBand band = bands.get(i);
            JSlider slider = new JSlider((int)min, (int)max, frequenciesValues[i]);
            slider.setName(Integer.toString(i));
            slider.setOpaque(false);
            slider.setUI(new CustomizedSliderUI(slider));
            slider.setBounds(i*52+48,5,50,140);
            slider.setOrientation(JSlider.VERTICAL);
            slider.addChangeListener((event) -> slider.repaint() );
            slider.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    band.setGain(slider.getValue());
                    frequenciesValues[Integer.parseInt(slider.getName())] = slider.getValue();
                }
            });
            if(sliders[i]!=null) {
                remove(sliders[i]);
            }
            add(slider);
            sliders[i]=slider;
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

    void updateFrequencies()
    {
        ObservableList<EqualizerBand> bands=mediaPlayer.getPlayer().getAudioEqualizer().getBands();
        for(int i=0;i<BANDS_NUM;++i)
        {
            frequenciesValues[i] = (int)bands.get(i).getGain();
            sliders[i].setValue((int)bands.get(i).getGain());
        }
    }

    void resetFrequencies()
    {
        for(int i=0;i<BANDS_NUM;++i)
        {
            bands.get(i).setGain(0);
            sliders[i].setValue(0);
            frequenciesValues[i]=0;
        }
    }
}
