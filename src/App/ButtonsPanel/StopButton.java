package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StopButton extends AbstractButton{
    public StopButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(250, 15, 56, 60);
        setIcon(new ImageIcon("images/stop.png"));
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(new ImageIcon("images/stop_pressed.png"));
                mediaPlayer.getPlayer().stop();
                mediaPlayer.songNameToCenter();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon("images/stop.png"));
            }
        });
    }
}
