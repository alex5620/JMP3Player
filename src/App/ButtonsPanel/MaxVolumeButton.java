package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MaxVolumeButton extends AbstractButton {
    public MaxVolumeButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setIcon(new ImageIcon("images/volume_full.png"));
        setBounds(465, 15, 46, 60);
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mediaPlayer.getButtonsPanel().volumeControl(1.0);
                highlightVolumeIsMaximized();
                mediaPlayer.getButtonsPanel().getMuteButton().highlightVolumeIsNotMuted();
            }
        });
    }

    public void highlightVolumeIsMaximized()
    {
        setIcon(new ImageIcon("images/volume_full_pressed.png"));
    }

    public void highlightVolumeIsNotMaximized()
    {
        setIcon(new ImageIcon("images/volume_full.png"));
    }
}
