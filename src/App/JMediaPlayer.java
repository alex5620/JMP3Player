package App;

import App.ButtonsPanel.ButtonsPanel;
import App.CardPanel.CardPanel;
import App.CardPanel.PlaylistHandler;
import App.HeaderPanel.HeaderPanel;
import App.MenuPanel.MenuPanel;
import App.SongNamePanel.SongNamePanel;
import App.TimePanel.TimePanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;


public class JMediaPlayer{
    private JFrame frame;
    private HeaderPanel headerPanel;
    private SongNamePanel songNamePanel;
    private TimePanel timePanel;
    private ButtonsPanel buttonsPanel;
    private CardPanel cardPanel;
    private PlaylistDatabase playlistDatabase;
    private SettingsDatabase settingsDatabase;

    private MediaPlayer player;
    private Media media;
    private File songFile;
    private String currentDirectory;
    private int currentSongIndex;
    private boolean repeat = false;
    private boolean paused = false;
    private boolean stopped = true;
    private boolean isEqualizerActive=false;

    public JMediaPlayer(String title) {
        com.sun.javafx.application.PlatformImpl.startup(()->{});//just to be able to use javafx
        playlistDatabase = new PlaylistDatabase();
        settingsDatabase = new SettingsDatabase();
        currentDirectory = settingsDatabase.getCurrentDirectory();
        PlaylistHandler.getInstance().loadSongs(this);
        initFrame();
        loadFirstSong();
        initPanels(title);
        createPlayer();
        currentSongIndex=0;
    }

    private void initFrame()
    {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 550, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.getContentPane().setLayout(null);
        frame.getRootPane().setBorder(new LineBorder(Color.BLACK));
    }

    private void loadFirstSong() {
        try {
            SongInformation song = PlaylistHandler.getInstance().getSongsInfo().get(0);
            songFile = new File(song.getPath() + "\\" + song.getName());
        }catch (Exception e)
        {
            paused=true;
            stopped=true;
            songFile = null;
        }
    }

    public void createPlayer()
    {
        if(songFile!=null) {
            media = new Media(songFile.toURI().toString());
            player = new MediaPlayer(media);
            getPlayer().setVolume(buttonsPanel.getVolumeValueLabel() / 100.0);
            player.setOnEndOfMedia(this::initNextSong);
        }
    }

    private void initNextSong()
    {
        if(!repeat) {
            currentSongIndex++;
            cardPanel.refreshPlaylistTableCells();
            createPlayerByCurrentIndex();
        }
        else
        {
            player.seek(Duration.ZERO);
        }
    }

    private void setPlaylistFinished()
    {
        songNamePanel.setSongName("Playlist finished.");
        songNameToCenter();
        stopMovingText();
        stopped = true;
        cardPanel.getPlayingNowPanel().resetAudioInfo();
        player.stop();
        player.seek(new Duration(0));
        timePanel.resetjSlider();
        cardPanel.refreshPlaylistTableCells();
    }

    public void createPlayerByCurrentIndex()
    {
        player.stop();
        if (currentSongIndex >= PlaylistHandler.getInstance().getSongsNumber()) {
            setPlaylistFinished();
            return;
        }
        SongInformation song = PlaylistHandler.getInstance().getSongsInfo().get(currentSongIndex);
        String songPath = song.getPath()+"\\"+song.getName();
        try {
            media = new Media(new File(songPath).toURI().toString());
        }catch (Exception e)
        {
                cardPanel.getPlaylistPanel().removeRow(currentSongIndex);
                cardPanel.getPlaylistPanel().refreshTableCells();
                createPlayerByCurrentIndex();
                return;
        }
        player = new MediaPlayer(media);
        sleepThread();
        player.play();
        getPlayer().setVolume(buttonsPanel.getVolumeValueLabel()/100.0);
        reinitSongInfo();
        player.setOnEndOfMedia(this::initNextSong);
    }

    private void reinitSongInfo()
    {
        updateSongName();
        songNameToBeginning();
        paused=false;
        setSongTime();
        setjSliderMaxValue();
        initWaveform();
        if(isEqualizerActive)
        {
            headerPanel.updateFrequenciesSliders();
        }
    }

    private void initPanels(String title)
    {
        headerPanel = new HeaderPanel(this, title);
        frame.getContentPane().add(headerPanel);
        songNamePanel = new SongNamePanel(songFile);
        frame.getContentPane().add(songNamePanel);
        timePanel = new TimePanel(this);
        frame.getContentPane().add(timePanel);
        buttonsPanel = new ButtonsPanel(this);
        frame.getContentPane().add(buttonsPanel);
        MenuPanel menuPanel = new MenuPanel(this);
        frame.getContentPane().add(menuPanel);
        cardPanel = new CardPanel(this);
        frame.getContentPane().add(cardPanel);
    }

    public void initWaveform()
    {
        cardPanel.initWaveform();
    }

    void makeVisible()
    {
        frame.setVisible(true);
    }

    public void stopMovingText()
    {
        songNamePanel.stopMovingText();
    }

    public void songNameToCenter()
    {
        songNamePanel.songNameToCenter();
    }

    public void songNameToBeginning()
    {
        songNamePanel.songNameToBeginning();
    }

    public MediaPlayer getPlayer()
    {
        return player;
    }

    public void setRepeat(boolean repeat)
    {
        this.repeat = repeat;
    }

    public void setSongFile(File songFile)
    {
        this.songFile = songFile;
    }

    public void setPaused(boolean paused)
    {
        this.paused=paused;
    }

    public void setEqualizerActive(boolean isActive)
    {
        isEqualizerActive = isActive;
    }

    public void updateSongName()
    {
        songNamePanel.setSongName(PlaylistHandler.getInstance().getSongsInfo().
                get(currentSongIndex).getName());
    }

    public void updateCurrentDirectory(String currentDirectory)
    {
        this.currentDirectory = currentDirectory;
    }

    public void resetCurrentIndex()
    {
        currentSongIndex=0;
    }

    public void decreaseCurrentSongIndex()
    {
        --currentSongIndex;
    }

    public void updateCurrentSongIndex()
    {
        currentSongIndex = cardPanel.getPlaylistPanel().getIndexOfSelectedRow()-1;
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public ButtonsPanel getButtonsPanel()
    {
        return buttonsPanel;
    }

    public String getCurrentDirectory()
    {
        return currentDirectory;
    }

    public boolean isRepeating()
    {
        return repeat;
    }

    public boolean isPaused()
    {
        return paused;
    }

    public void setSongTime()
    {
        timePanel.setMaxTime();
    }

    public void setjSliderMaxValue()
    {
        timePanel.setjSliderMaxValue();
    }

    public void setStopped(boolean value)
    {
        stopped = value;
    }

    public boolean isStopped()
    {
        return stopped;
    }

    public SongNamePanel getSongNamePanel()
    {
        return songNamePanel;
    }

    public TimePanel getTimePanel()
    {
        return timePanel;
    }

    private void sleepThread()
    {
        try {
            Thread.sleep(70);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public CardPanel getCardPanel()
    {
        return cardPanel;
    }

    public PlaylistDatabase getPlaylistDatabase()
    {
        return playlistDatabase;
    }

    public SettingsDatabase getSettingsDatabaseDatabase()
    {
        return settingsDatabase;
    }

    public int getCurrentSongIndex()
    {
        return currentSongIndex;
    }

    public void updateFrequencies()
    {
        headerPanel.updateFrequencies();
    }

    public boolean isEqualizerActive()
    {
        return isEqualizerActive;
    }
}










