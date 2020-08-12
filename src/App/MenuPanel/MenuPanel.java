package App.MenuPanel;

import App.JMediaPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextLayout;

public class MenuPanel extends JPanel {
    private JMediaPlayer mediaPlayer;
    private JLabel playlist;
    private JLabel playingNow;
    public MenuPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(0, 244, 550, 31);
        setBackground(new Color(7,63,86));
//        setBorder(new LineBorder(new Color(34,202,237), 1));
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
        playlist.setBorder(new LineBorder(new Color(34,202,237), 1));
        playlist.setForeground(new Color(34,202,237));
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
        playingNow.setForeground(new Color(34,202,237));
        playingNow.setOpaque(true);
        playingNow.setBorder(new LineBorder(new Color(34,202,237), 1));
        playingNow.setBackground(new Color(7,63,86));
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
        playingNow.setBackground(new Color(7,63,86));
    }

    private void playingNowPressed()
    {
        playingNow.setBackground(new Color(7, 48, 65));
        playlist.setBackground(new Color(7,63,86));
    }
}
