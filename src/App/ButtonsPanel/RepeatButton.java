package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RepeatButton extends AbstractButton{
    private ImageIcon repeatPressedImage;
    private ImageIcon repeatReleasedImage;
    RepeatButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(15, 7, 83, 79);
        repeatPressedImage = new ImageIcon("images/repeat_pressed.png");
        repeatReleasedImage = new ImageIcon("images/repeat.png");
        setIcon(repeatReleasedImage);
        setToolTipText("Repeat song");
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!mediaPlayer.isRepeating())
                {
                    mediaPlayer.setRepeat(true);
                    setIcon(repeatPressedImage);
                }
                else
                {
                    mediaPlayer.setRepeat(false);
                    setIcon(repeatReleasedImage);
                }
            }
        });
    }
}
