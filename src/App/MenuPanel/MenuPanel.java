package App.MenuPanel;

import App.Colors;
import App.JMediaPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {
    private JMediaPlayer mediaPlayer;
    private JLabel playlist;
    private JLabel playingNow;
    public MenuPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(0, 244, 550, 31);
        setBackground(Colors.color7_63_86);
        setLayout(null);
        initLabels();
    }

    private void initLabels()
    {
        initPlaylistLabel();
        initPlayingNowLabel();
    }

    private void initPlaylistLabel()
    {
        playlist= new JLabel("PLAYLIST", SwingConstants.CENTER);
        playlist.setBounds(0, 1, 274, 29);
        playlist.setBackground(new Color(7, 48, 65));
        playlist.setOpaque(true);
        playlist.setBorder(new LineBorder(Colors.color34_202_237, 1));
        playlist.setForeground(Colors.color34_202_237);
        playlist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playlistPressed();
                mediaPlayer.getCardPanel().setPlaylistPanel();
            }
        });
        add(playlist);
    }

    private void initPlayingNowLabel()
    {
        playingNow = new JLabel("PLAYING NOW", SwingConstants.CENTER);
        playingNow.setBounds(273, 1, 275, 29);
        playingNow.setForeground(Colors.color34_202_237);
        playingNow.setOpaque(true);
        playingNow.setBorder(new LineBorder(Colors.color34_202_237, 1));
        playingNow.setBackground(Colors.color7_63_86);
        playingNow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playingNowPressed();
                mediaPlayer.getCardPanel().setPlayingPanel();
            }
        });
        add(playingNow);
    }

    private void playlistPressed()
    {
        playlist.setBackground(new Color(7, 48, 65));
        playingNow.setBackground(Colors.color7_63_86);
    }

    private void playingNowPressed()
    {
        playingNow.setBackground(new Color(7, 48, 65));
        playlist.setBackground(Colors.color7_63_86);
    }
}
