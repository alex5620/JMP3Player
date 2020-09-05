package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StopButton extends AbstractButton{
    private ImageIcon stopPressedImage;
    private ImageIcon stopReleasedImage;
    StopButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(250, 15, 56, 60);
        stopPressedImage = new ImageIcon("images/stop_pressed.png");
        stopReleasedImage = new ImageIcon("images/stop.png");
        setIcon(stopReleasedImage);
        setToolTipText("Stop");
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(stopPressedImage);
                handleSongText();
                handleSongStopped();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(stopReleasedImage);
            }
        });
    }

    private void handleSongText()
    {
        mediaPlayer.songNameToCenter();
        mediaPlayer.getSongNamePanel().setSongName("No song playing.");
        mediaPlayer.stopMovingText();
    }


    private void handleSongStopped() {
        if (!mediaPlayer.isStopped()) {
            mediaPlayer.setStopped(true);
            mediaPlayer.getPlayer().stop();
            mediaPlayer.getTimePanel().resetMaxTime();
            mediaPlayer.getTimePanel().resetjSlider();
            mediaPlayer.getCardPanel().refreshPlaylistTableCells();
            mediaPlayer.getCardPanel().getPlaylistPanel().delesectRow();
        }
    }
}
