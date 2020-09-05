package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PauseButton extends AbstractButton {
    private ImageIcon pausePressedImage;
    private ImageIcon pauseReleasedImage;
    PauseButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(80, 5, 70, 80);
        pausePressedImage = new ImageIcon("images/pause_pressed.png");
        pauseReleasedImage = new ImageIcon("images/pause.png");
        setIcon(pauseReleasedImage);
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(pausePressedImage);
                handleMediaPlayerNotPaused();
                handleMediaPlayerIsStopped();
                mediaPlayer.stopMovingText();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(pauseReleasedImage);
            }
        });
    }

    private void handleMediaPlayerNotPaused()
    {
        if(!mediaPlayer.isPaused()) {
            if(mediaPlayer.getPlayer()!=null) {
                mediaPlayer.getPlayer().pause();
            }
            mediaPlayer.setPaused(true);
        }
    }

    private void handleMediaPlayerIsStopped()
    {
        if(mediaPlayer.isStopped())
        {
            mediaPlayer.getCardPanel().getPlaylistPanel().delesectRow();
            mediaPlayer.getSongNamePanel().setSongName("No song playing.");
            mediaPlayer.getSongNamePanel().redraw();
        }
    }
}
