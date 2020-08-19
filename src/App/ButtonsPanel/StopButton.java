package App.ButtonsPanel;

import App.JMediaPlayer;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StopButton extends AbstractButton{
    public StopButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(250, 15, 56, 60);
        setIcon(new ImageIcon("images/stop.png"));
        setToolTipText("Stop");
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(new ImageIcon("images/stop_pressed.png"));
                mediaPlayer.songNameToCenter();
                mediaPlayer.getSongNamePanel().setSongName("No song playing.");
                mediaPlayer.stopMovingText();
                mediaPlayer.getCardPanel().getPlaylistPanel().delesectRow();
                if (mediaPlayer.isStopped() == false) {
                    mediaPlayer.setStopped(true);
                    mediaPlayer.getPlayer().stop();
                    mediaPlayer.getTimePanel().resetMaxTime();
                    mediaPlayer.getTimePanel().resetjSlider();
                    mediaPlayer.getCardPanel().refreshPlaylistTableCells();
                    mediaPlayer.getCardPanel().getPlaylistPanel().delesectRow();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon("images/stop.png"));
            }
        });
    }
}
