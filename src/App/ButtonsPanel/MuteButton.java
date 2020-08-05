package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MuteButton extends AbstractButton{
    public MuteButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setIcon(new ImageIcon("images/mute.png"));
        setBounds(505, 40, 46, 60);
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mediaPlayer.getButtonsPanel().volumeControl(0.0);
                highlightVolumeIsMuted();
                mediaPlayer.getButtonsPanel().getMaxVolumeButton().highlightVolumeIsNotMaximized();
            }
        });
    }

    public void highlightVolumeIsMuted()
    {
        setIcon(new ImageIcon("images/mute_pressed.png"));
    }


    public void highlightVolumeIsNotMuted()
    {
        setIcon(new ImageIcon("images/mute.png"));
    }
}
