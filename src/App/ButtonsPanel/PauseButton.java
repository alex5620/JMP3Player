package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PauseButton extends AbstractButton {
    public PauseButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(80, 30, 70, 80);
        setIcon(new ImageIcon("images/pause.png"));
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(new ImageIcon("images/pause_pressed.png"));
                mediaPlayer.getPlayer().pause();
                mediaPlayer.stopMovingText();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon("images/pause.png"));
            }
        });
    }
}
