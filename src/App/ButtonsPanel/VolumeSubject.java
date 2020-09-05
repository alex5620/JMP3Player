package App.ButtonsPanel;

import App.JMediaPlayer;
import App.TimePanel.TimeSlider;
import App.VolumeObserver;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class VolumeSubject extends JSlider {
    private ArrayList<VolumeObserver> observers;
    private JMediaPlayer mediaPlayer;
    VolumeSubject(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(425, 15, 95, 60);
        setValue(mediaPlayer.getSettingsDatabaseDatabase().getSettingsInformation("volume"));
        setMaximum(100);
        setOpaque(false);
        setUI(new TimeSlider(this, 12, 3));
        addMouseListener();
        observers = new ArrayList<>();
    }

    public void attach(VolumeObserver observer)
    {
        observers.add(observer);
    }

    private void addMouseListener()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mediaPlayer.getPlayer().setVolume(getValue()/100.0);
                notifyAllObservers();
            }
        });
    }

    void setValue(double value)
    {
        setValue((int)(value*100));
        notifyAllObservers();
    }

    private void notifyAllObservers()
    {
        for(VolumeObserver observer: observers)
        {
            observer.update();
        }
    }

    public JMediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }
}
