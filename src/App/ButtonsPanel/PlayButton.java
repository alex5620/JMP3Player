package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayButton extends AbstractButton {
    public PlayButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(135, 0, 120, 90);
        setIcon(new ImageIcon("images/play.png"));
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(new ImageIcon("images/play_pressed.png"));
                mediaPlayer.updateSongName();
                if(mediaPlayer.isPaused()==false)
                {
                    mediaPlayer.songNameToBeginning();
                }
                mediaPlayer.sleepThread();
                mediaPlayer.getPlayer().play();
                mediaPlayer.setPaused(false);
                mediaPlayer.setSongTime();
                mediaPlayer.setjSliderMaxValue();
                mediaPlayer.initWaveform();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon("images/play.png"));
            }
        });
    }
}
