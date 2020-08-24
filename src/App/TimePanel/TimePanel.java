package App.TimePanel;

import App.CardPanel.PlaylistHandler;
import App.JMediaPlayer;
import App.SongInformation;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TimePanel extends JPanel implements ActionListener {
    private JMediaPlayer mediaPlayer;
    private JSlider jSlider;
    private Timer timer;
    private JLabel currentTime;
    private JLabel maxTime;
    private boolean mousePressed;
    public TimePanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(0, 94, 550, 60);
        setBackground(new Color(7,63,86));
        setLayout(null);
        timer = new Timer(1000, this);
        timer.start();
        initCurrentTimeLabel();
        initMaxTimeLabel();
        initJSlider();
    }

    public void initCurrentTimeLabel()
    {
        currentTime = new JLabel("0:00");
        currentTime.setBounds(25, 21, 46, 14);
        currentTime.setForeground(new Color(34,202,237));
        add(currentTime);
    }

    public void initMaxTimeLabel()
    {
        maxTime = new JLabel("0:00");
        maxTime.setBounds(500, 21, 46, 14);
        maxTime.setForeground(new Color(34,202,237));
        add(maxTime);
    }

    public void initJSlider()
    {
        jSlider =new JSlider();
        jSlider.setBounds(58, 15, 430, 26);
        jSlider.setValue(0);
        jSlider.setMaximum(0);
        jSlider.setOpaque(false);
        jSlider.setUI(new TimeSlider(jSlider, 16, 3));
        jSlider.addChangeListener(e -> {
            currentTime.setText(getFormattedString(jSlider.getValue()));
        });
        jSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                int currentJSliderState = jSlider.getValue();
                double currentSongState = mediaPlayer.getPlayer().getCurrentTime().toSeconds();
                double difference = currentJSliderState - currentSongState;
                mediaPlayer.getPlayer().seek(mediaPlayer.getPlayer().getCurrentTime().add(new Duration(difference * 1000)));
                mousePressed=false;
            }
            @Override
            public void mousePressed(MouseEvent e)
            {
                mousePressed=true;
            }
        });
        jSlider.setFocusable(false);
        add(jSlider);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(mousePressed==false) {
            currentTime.setText(getFormattedString(mediaPlayer.getPlayer().getCurrentTime().toSeconds()));
            jSlider.setValue((int)mediaPlayer.getPlayer().getCurrentTime().toSeconds());
        }
    }

    private String getFormattedString(double currentTimeSeconds)
    {
        String formattedString="";
        int minutes = (((int)(currentTimeSeconds)/60));
        formattedString+=minutes+":";
        int seconds= (((int)currentTimeSeconds)%60);
        if(seconds >= 10)
        {
            formattedString+=seconds;
        }
        else
        {
            formattedString+="0"+seconds;
        }
        return formattedString;
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
        jSlider.setValue(0);
        jSlider.setMaximum(0);
        currentTime.setText("0:00");
        resetMaxTime();
    }

    public void setjSliderMaxValue()
    {
        int songTimeInSeconds = PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.getCurrentSongIndex()).getSongMillis()/1000;
        jSlider.setMaximum(songTimeInSeconds);
        jSlider.setValue(0);
    }

}
