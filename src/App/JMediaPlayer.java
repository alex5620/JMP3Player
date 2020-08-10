package App;

import App.ButtonsPanel.ButtonsPanel;
import App.CardPanel.CardPanel;
import App.HeaderPanel.HeaderPanel;
import App.MenuPanel.MenuPanel;
import App.SongNamePanel.SongNamePanel;
import App.TimePanel.TimePanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

    private MediaPlayer player;
    private File songFile;
    private String currentDirectory="home.user";
    boolean repeat = false;
    boolean paused = false;
    boolean windowCollapsed=false;

    public void createPlayer()
    {
        Media media = new Media(songFile.toURI().toString());
        player = new MediaPlayer(media);
        player.getStatus();
    }

    public JMediaPlayer(String title) {
        com.sun.javafx.application.PlatformImpl.startup(()->{});//just to be able to use javafx
        initFrame();
        songFile=new File("E:\\Muzica\\Demis Roussos - My Friend The Wind.mp3");
        initPanels(title);
        createPlayer();
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
        songNamePanel.setSongName(songFile.getName());
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

    public CardPanel getCardPanel()
    {
        return cardPanel;
    }
}










