package App.CardPanel;

import App.JMediaPlayer;
import App.SongInformation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CardPanel extends JPanel {
    private CardLayout cardLayout;
    private PlayingNowPanel playingNowPanel;
    private PlaylistPanel playlistPanel;
    public CardPanel(JMediaPlayer mediaPlayer)
    {
        setBounds(0, 275, 550, 375);
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setOpaque(true);
        playingNowPanel = new PlayingNowPanel(mediaPlayer);
        playlistPanel = new PlaylistPanel(mediaPlayer);
        add(playlistPanel, "panel1");
        add(playingNowPanel, "panel2");
        cardLayout.show(this, "panel1");
    }

    public void setPlaylistPanel()
    {
        cardLayout.show(this, "panel1");
    }

    public void setPlayingPanel()
    {
        cardLayout.show(this, "panel2");
    }

    public void addValuesToPlaylistTable(ArrayList<SongInformation> info)
    {
        playlistPanel.addValuesToTable(info);
    }

    public void selectFirstRow()
    {
        playlistPanel.selectRow(0);
    }

    public void refreshPlaylistTableCells()
    {
        playlistPanel.refreshTableCells();
    }

    public void initWaveform()
    {
        playingNowPanel.initWaveform();
    }

    int getRowSelected()
    {
        return playlistPanel.getRowSelected();
    }

    public PlayingNowPanel getPlayingNowPanel() {
        return playingNowPanel;
    }

    public PlaylistPanel getPlaylistPanel()
    {
        return playlistPanel;
    }
}
