package App.CardPanel;

import App.JMediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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

    public void play(AudioInputStream ais)
    {
        playingNowPanel.setAudioToDisplay(ais);
    }

    public void addValuesToPlaylistTable(ArrayList info)
    {
        playlistPanel.addValuesToTable(info);
    }

    public void selectFirstRow()
    {
        playlistPanel.selectRow(0);
    }

    public void selectNextRow()
    {
        playlistPanel.selectNextRow();
    }

    public void refreshPlaylistTableCells()
    {
        playlistPanel.refreshTableCells();
    }

    public void initWaveform()
    {
        playingNowPanel.initWaveform();
    }

    public int getRowSelected()
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
