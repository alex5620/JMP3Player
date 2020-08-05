package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RepeatButton extends AbstractButton{
    public RepeatButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(0, 5, 83, 79);
        setIcon(new ImageIcon("images/repeat.png"));
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(mediaPlayer.isRepeating()==false)
                {
                    mediaPlayer.setRepeat(true);
                    setIcon(new ImageIcon("images/repeat_pressed.png"));
                }
                else
                {
                    mediaPlayer.setRepeat(false);
                    setIcon(new ImageIcon("images/repeat.png"));
                }
            }
        });
    }
}
