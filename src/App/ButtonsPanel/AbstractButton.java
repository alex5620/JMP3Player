package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.swing.*;

public abstract class AbstractButton extends JLabel{
    protected JMediaPlayer mediaPlayer;
    public AbstractButton(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
    }
    protected abstract void addListeners();
}
