package App;

import App.ButtonsPanel.ButtonsPanel;
import App.HeaderPanel.HeaderPanel;
import App.SongNamePanel.SongNamePanel;
import jaco.mp3.player.MP3Player;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;


public class JMediaPlayer {
    private JFrame frame;
    private HeaderPanel headerPanel;
    private SongNamePanel songNamePanel;
    private ButtonsPanel buttonsPanel;

    private MP3Player player;
    //	private MediaPlayer player;
    private File songFile;
    private String currentDirectory="home.user";
    private String currentPath;
    boolean repeat = false;
    boolean windowCollapsed=false;

    public JMediaPlayer(String title) {
        initFrame();
        initPanels(title);
        songFile=new File("E:\\Muzica\\02 - Satra I.mp3");
        String fileName=songFile.getName();
        player= createMP3Player();

        player.addToPlayList(songFile);
        currentPath=Paths.get(".").toAbsolutePath().normalize().toString();
        try
        {
            int duration=0;
            AudioFile audioFile = AudioFileIO.read(songFile);
            duration= audioFile.getAudioHeader().getTrackLength();
            System.out.print("time in seconds= "+duration);
        }catch(Exception e){
        }
    }

    private void initFrame()
    {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 550, 216);
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
        songNamePanel = new SongNamePanel();
        frame.getContentPane().add(songNamePanel);
        buttonsPanel = new ButtonsPanel(this);
        frame.getContentPane().add(buttonsPanel);
    }

    public void makeVisible()
    {
        frame.setVisible(true);
    }

    public void stopMovingText()
    {
        songNamePanel.stopMovingText();
    }

    private MP3Player createMP3Player()
    {
        MP3Player mp3player= new MP3Player();
        return mp3player;
    }

    public MP3Player getPlayer()
    {
        return player;
    }

    public void setRepeat(boolean repeat)
    {
        this.repeat=repeat;
        player.setRepeat(repeat);
    }

    public void setSongFile(File songFile)
    {
        this.songFile = songFile;
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
}










