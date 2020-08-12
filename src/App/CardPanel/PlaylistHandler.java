package App.CardPanel;

import App.JMediaPlayer;
import App.PlaylistDatabase;
import App.SongInformation;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class PlaylistHandler {
    private static PlaylistHandler instance;
    private ArrayList<SongInformation> songs;
    public static PlaylistHandler getInstance()
    {
        if(instance==null)
        {
            synchronized (PlaylistHandler.class)
            {
                if(instance==null)
                {
                    instance=new PlaylistHandler();
                }
            }
        }
        return instance;
    }

    public ArrayList<SongInformation> addNewPlaylistInformation(File [] files)
    {
        songs = new ArrayList<>();
        try {
            Media media;
            MediaPlayer player;
            for (File file : files) {
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                Thread.sleep(50);
                songs.add(new SongInformation(file.getName(), file.getParent(),
                        getFormattedString(player.getTotalDuration())));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>(songs);
    }

    private String getFormattedString(Duration duration)
    {
        String formattedString="";
        double currentTimeSeconds = duration.toSeconds();
        int minutes = (((int)(currentTimeSeconds)/60));
        formattedString+=minutes+":";
        int seconds= (((int)currentTimeSeconds)%60);
        if(seconds >= 10)
        {
            formattedString+=seconds;
        }
        else
        {
            formattedString+="0"+seconds;
        }
        return formattedString;
    }

    public void saveSongs(JMediaPlayer mediaPlayer)
    {
        PlaylistDatabase database=mediaPlayer.getDatabase();
        database.getConnection();
        for(SongInformation song: songs)
        {
            database.addSong(song.getName(), song.getPath(), song.getTime());
        }
        database.closeDatabase();
    }

    public void loadSongs(JMediaPlayer mediaPlayer)
    {
        PlaylistDatabase database=mediaPlayer.getDatabase();
        songs=database.getData();
    }

    public ArrayList<SongInformation> getSongsInfo()
    {
        return new ArrayList<>(songs);
    }
}
