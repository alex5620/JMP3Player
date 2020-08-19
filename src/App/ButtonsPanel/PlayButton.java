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
                if (mediaPlayer.getCardPanel().getPlaylistPanel().getRowSelected() != -1) {
                    mediaPlayer.setStopped(false);
                    if(mediaPlayer.isPaused())
                    {
                        mediaPlayer.setPaused(false);
                        mediaPlayer.getSongNamePanel().startMovingText();
                        mediaPlayer.getPlayer().play();
                        return;
                    }
                    mediaPlayer.setPaused(false);
                    mediaPlayer.songNameToBeginning();
                    mediaPlayer.updateCurrentSongIndex();
                    mediaPlayer.createPlayerByCurrentIndex();
                    mediaPlayer.getCardPanel().refreshPlaylistTableCells();
                }
                else
                {
                    mediaPlayer.getSongNamePanel().setSongName("No sound selected.");
                    mediaPlayer.stopMovingText();
                    mediaPlayer.getSongNamePanel().redraw();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon("images/play.png"));
            }
        });
    }
}
