package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayButton extends AbstractButton {
    public PlayButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(135, 10, 120, 120);
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
                mediaPlayer.getPlayer().play();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon("images/play.png"));
            }
        });
    }
}
