package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayButton extends AbstractButton {
    private ImageIcon playPressedImage;
    private ImageIcon playReleasedImage;
    PlayButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(135, 0, 120, 90);
        playPressedImage = new ImageIcon("images/play_pressed.png");
        playReleasedImage = new ImageIcon("images/play.png");
        setIcon(playReleasedImage);
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(playPressedImage);
                if (mediaPlayer.getCardPanel().getPlaylistPanel().getRowSelected() != -1) {
                    mediaPlayer.setStopped(false);
                    if(mediaPlayer.isPaused())
                    {
                        handleMediaPlayerIsPaused();
                        return;
                    }
                    playSelectedSong();
                }
                else
                {
                    handleNoColumnSelected();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(playReleasedImage);
            }
        });
    }

    private void handleMediaPlayerIsPaused()
    {
        mediaPlayer.setPaused(false);
        mediaPlayer.getSongNamePanel().startMovingText();
        mediaPlayer.getPlayer().play();
    }

    private void playSelectedSong()
    {
        mediaPlayer.setPaused(false);
        mediaPlayer.songNameToBeginning();
        mediaPlayer.updateCurrentSongIndex();
        mediaPlayer.createPlayerByCurrentIndex();
        mediaPlayer.getCardPanel().refreshPlaylistTableCells();
    }

    private void handleNoColumnSelected() {
        if (mediaPlayer.isStopped() == true) {
            mediaPlayer.getSongNamePanel().setSongName("No sound selected.");
            mediaPlayer.stopMovingText();
            mediaPlayer.getSongNamePanel().redraw();
        }
    }
}
