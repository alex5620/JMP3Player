package App.ButtonsPanel;

import App.JMediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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
                mediaPlayer.updateSongName();
                if(mediaPlayer.isPaused()==false)
                {
                    mediaPlayer.songNameToBeginning();
                }
                mediaPlayer.getPlayer().play();
                mediaPlayer.setPaused(false);
                mediaPlayer.setSongTime();
                mediaPlayer.setjSliderMaxValue();
                /////////////////////
                try {
                    File file = new File("E:\\Muzica\\Demis Roussos - My Friend The Wind.mp3");
                    AudioInputStream audioInputStream
                            = AudioSystem.getAudioInputStream(file);
                    mediaPlayer.getCardPanel().play(audioInputStream);
                }catch (Exception er)
                {
                    er.printStackTrace();
                }
                /////////////////////
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon("images/play.png"));
            }
        });
    }
}
