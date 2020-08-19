package App.CardPanel;

import App.JMediaPlayer;
import App.PlaylistDatabase;
import App.SongInformation;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;

public class PlaylistHandler {
    private static PlaylistHandler instance;
    private ArrayList<SongInformation> songs;
    private PlaylistHandler() {}
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

    public ArrayList<SongInformation> addNewPlaylistInformation(File [] files, boolean resetExistingItems)
    {
        MediaPlayer player;
        Media media;
        if(resetExistingItems) {
            songs = new ArrayList<>();
        }
        try {
            for (File file : files) {
                media = new Media(file.toURI().toString());
                player = new MediaPlayer(media);
                while(player.getStatus() != MediaPlayer.Status.READY){
                    Thread.sleep(10);
                }
                if (resetExistingItems || checkIfSongExists(file.getName())==false) {
                    songs.add(new SongInformation(file.getName(), file.getParent(),
                            (int) player.getTotalDuration().toMillis()));
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return songs;
    }

    private boolean checkIfSongExists(String name)
    {
        for(SongInformation song : songs)
        {
            if(song.getName().equals(name))
            {
                return true;
            }
        }
        return false;
    }

    public String getFormattedString(double currentTimeSeconds)
    {
        String formattedString="";
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
        PlaylistDatabase database=mediaPlayer.getPlaylistDatabase();
        database.getConnection();
        for(SongInformation song: songs)
        {
            database.addSong(song.getName(), song.getPath(), song.getSongMillis());
        }
        database.closeDatabase();
    }

    public void loadSongs(JMediaPlayer mediaPlayer)
    {
        PlaylistDatabase database=mediaPlayer.getPlaylistDatabase();
        songs=database.getData();
    }

    public void removeSong(int index)
    {
        songs.remove(index);
    }

    public ArrayList<SongInformation> getSongsInfo()
    {
        return new ArrayList<>(songs);
    }

    public int getSongMilliseconds(int index)
    {
        return songs.get(index).getSongMillis();
    }

    public int getSongsNumber()
    {
        return songs.size();
    }
}
