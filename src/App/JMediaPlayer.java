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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;


public class JMediaPlayer {
    private JFrame frame;
    private HeaderPanel headerPanel;
    private SongNamePanel songNamePanel;
    private TimePanel timePanel;
    private ButtonsPanel buttonsPanel;
    private MenuPanel menuPanel;
    private CardPanel cardPanel;
    private PlaylistDatabase database;

    private MediaPlayer player;
    private File songFile;
    private String currentDirectory="home.user";
    boolean repeat = false;
    boolean paused = false;
    boolean windowCollapsed=false;

    public JMediaPlayer(String title) {
        com.sun.javafx.application.PlatformImpl.startup(()->{});//just to be able to use javafx
        database = new PlaylistDatabase();
        database.initialize();
        PlaylistHandler.getInstance().loadSongs(this);
        initFrame();
        SongInformation song = PlaylistHandler.getInstance().getSongsInfo().get(0);
        songFile=new File(song.getPath()+"\\"+song.getName());
        initPanels(title);
        createPlayer();
    }

    public void createPlayer()
    {
        Media media = new Media(songFile.toURI().toString());
        player = new MediaPlayer(media);
        player.setOnEndOfMedia(()->
        {
            initNextSong();
        });
    }

    private void initNextSong()
    {
        int nextRow = cardPanel.getRowSelected()+1;
        createPlayer(nextRow);
//        sleepThread();
        cardPanel.selectNextRow();
//        initWaveform();
        songNamePanel.songNameToBeginning();
        songNamePanel.setSongName(PlaylistHandler.getInstance().getSongsInfo().get(nextRow).getName());
        setSongTime();
        setjSliderMaxValue();
    }

    public void createPlayer(int songIndex)
    {
        player.stop();
        SongInformation song= PlaylistHandler.getInstance().getSongsInfo().get(songIndex);
        String songPath = song.getPath()+"\\"+song.getName();
        Media media = new Media(new File(songPath).toURI().toString());
        player = new MediaPlayer(media);
        player.play();
        player.setOnEndOfMedia(()->
        {
            initNextSong();
        });
        reinitMediaPlayer();
    }

    public void reinitMediaPlayer()
    {
        sleepThread();
        updateSongName();
        songNameToBeginning();
        paused=false;
        setSongTime();
        setjSliderMaxValue();
        initWaveform();
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

    private void initPanels(String title)
    {
        headerPanel = new HeaderPanel(this, title);
        frame.getContentPane().add(headerPanel);
        songNamePanel = new SongNamePanel(songFile.getName());
        frame.getContentPane().add(songNamePanel);
        timePanel = new TimePanel(this);
        frame.getContentPane().add(timePanel);
        buttonsPanel = new ButtonsPanel(this);
        frame.getContentPane().add(buttonsPanel);
        menuPanel = new MenuPanel(this);
        frame.getContentPane().add(menuPanel);
        cardPanel = new CardPanel(this);
        frame.getContentPane().add(cardPanel);
    }

    public void initWaveform()
    {
        cardPanel.initWaveform();
    }

    public void makeVisible()
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
        if(repeat==true) {
            this.repeat = repeat;
            player.setStartTime(player.getCurrentTime());
            player.setAutoPlay(true);
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.play();
        }
        else
        {
            player.setAutoPlay(false);
        }
    }

    public void setSongFile(File songFile)
    {
        this.songFile = songFile;
    }

    public void setPaused(boolean paused)
    {
        this.paused=paused;
    }

    public void updateSongName()
    {
        songNamePanel.setSongName(PlaylistHandler.getInstance().getSongsInfo().
                get(cardPanel.getRowSelected()).getName());
    }

    public void updateCurrentDirectory(String currentDirectory)
    {
        this.currentDirectory = currentDirectory;
    }
    public JFrame getFrame()
    {
        return frame;
    }

    public ButtonsPanel getButtonsPanel()
    {
        return buttonsPanel;
    }

    public String getSongName()
    {
        return songFile.getName();
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

    public void sleepThread()
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

    public PlaylistDatabase getDatabase()
    {
        return database;
    }
}










