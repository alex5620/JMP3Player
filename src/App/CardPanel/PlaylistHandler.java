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
                if (resetExistingItems || !checkIfSongExists(file.getName())) {
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
        StringBuilder formattedString=new StringBuilder();
        int hours= (int)currentTimeSeconds/3600;
        int minutes = (((int)(currentTimeSeconds)/60)%60);
        int seconds= (((int)currentTimeSeconds)%60);
        if(hours>0)
        {
            formattedString.append((int)currentTimeSeconds/3600).append(":");
            if(minutes<10)
            {
                formattedString.append("0");
            }
        }
        formattedString.append(minutes).append(":");
        formattedString.append(String.format("%02d", seconds));
        return formattedString.toString();
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

    void removeSong(int index)
    {
        songs.remove(index);
    }

    public ArrayList<SongInformation> getSongsInfo()
    {
        return new ArrayList<>(songs);
    }

    public int getSongsNumber()
    {
        return songs.size();
    }
}
