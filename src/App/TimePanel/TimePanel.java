package App.TimePanel;

import App.CardPanel.PlaylistHandler;
import App.Colors;
import App.JMediaPlayer;
import App.SongInformation;
import javafx.util.Duration;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TimePanel extends JPanel implements ActionListener {
    private JMediaPlayer mediaPlayer;
    private JSlider slider;
    private JLabel currentTime;
    private JLabel maxTime;
    private boolean mousePressed;
    public TimePanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(0, 94, 550, 60);
        setBackground(Colors.color7_63_86);
        setLayout(null);
        Timer timer = new Timer(1000, this);
        timer.start();
        initCurrentTimeLabel();
        initMaxTimeLabel();
        initJSlider();
    }

    private void initCurrentTimeLabel()
    {
        currentTime = new JLabel("0:00", SwingConstants.CENTER);
        currentTime.setBounds(0, 21, 60, 16);
        currentTime.setForeground(Colors.color34_202_237);
        add(currentTime);
    }

    private void initMaxTimeLabel()
    {
        maxTime = new JLabel("0:00", SwingConstants.CENTER);
        maxTime.setBounds(482, 21, 60, 14);
        maxTime.setForeground(Colors.color34_202_237);
        add(maxTime);
    }

    private void initJSlider()
    {
        slider =new JSlider();
        slider.setBounds(58, 15, 430, 26);
        slider.setValue(0);
        slider.setMaximum(0);
        slider.setOpaque(false);
        slider.setUI(new TimeSlider(slider, 16, 3));
        slider.addChangeListener(e -> currentTime.setText(PlaylistHandler.
                getInstance().getFormattedString(slider.getValue())));
        slider.setFocusable(false);
        addMouseListenerToSlider();
        slider.addChangeListener((event) -> slider.repaint());
        add(slider);
    }

    private void addMouseListenerToSlider()
    {
        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if(mediaPlayer.getPlayer()!=null)
                {
                    int currentJSliderState = slider.getValue();
                    double currentSongState = mediaPlayer.getPlayer().getCurrentTime().toSeconds();
                    double difference = currentJSliderState - currentSongState;
                    mediaPlayer.getPlayer().seek(mediaPlayer.getPlayer().getCurrentTime().add(new Duration(difference * 1000)));
                    mousePressed = false;
                }
            }
            @Override
            public void mousePressed(MouseEvent e)
            {
                mousePressed=true;
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(!mousePressed && mediaPlayer.getPlayer()!=null) {
            currentTime.setText(PlaylistHandler.getInstance().getFormattedString
                    (mediaPlayer.getPlayer().getCurrentTime().toSeconds()));
            slider.setValue((int)mediaPlayer.getPlayer().getCurrentTime().toSeconds());
        }
    }

    public void setMaxTime()
    {
        int currentIndex = mediaPlayer.getCurrentSongIndex();
        SongInformation currentSong = PlaylistHandler.getInstance().getSongsInfo().get(currentIndex);
        maxTime.setText(PlaylistHandler.getInstance().getFormattedString(currentSong.getSongMillis()/1000));
    }

    public void resetMaxTime()
    {
        maxTime.setText("0:00");
    }


    public void resetjSlider()
    {
        slider.setValue(0);
        slider.setMaximum(0);
        currentTime.setText("0:00");
        resetMaxTime();
    }

    public void setjSliderMaxValue()
    {
        int songTimeInSeconds = PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.getCurrentSongIndex()).getSongMillis()/1000;
        slider.setMaximum(songTimeInSeconds);
        slider.setValue(0);
    }

}
