package App.ButtonsPanel;

import App.CardPanel.MyJFileChooser;
import App.CardPanel.PlaylistHandler;
import App.FileTypeFilter;
import App.JMediaPlayer;
import App.SongInformation;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;


public class LoadButton extends AbstractButton {
    private ImageIcon buttonPressedImage;
    private ImageIcon buttonReleasedImage;
    LoadButton(JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        setBounds(320, 15, 62, 60);
        buttonPressedImage = new ImageIcon("images/open_pressed.png");
        buttonReleasedImage = new ImageIcon("images/open.png");
        setIcon(buttonReleasedImage);
        setToolTipText("Load new playlist");
        addListeners();
    }

    @Override
    protected void addListeners()
    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(buttonPressedImage);
                File[] files = createFileChooser();
                if(files.length>0)
                {
                    ArrayList<SongInformation> info = PlaylistHandler.getInstance().addNewPlaylistInformation(files, true);
                    mediaPlayer.getCardPanel().addValuesToPlaylistTable(info);
                    if(mediaPlayer.getPlayer()!=null) {
                        mediaPlayer.getPlayer().stop();
                    }
                    File songFile = files[0];
                    mediaPlayer.getCardPanel().selectFirstRow();
                    mediaPlayer.resetCurrentIndex();
                    mediaPlayer.setSongFile(songFile);
                    mediaPlayer.updateCurrentDirectory(songFile.getAbsolutePath());
                    mediaPlayer.updateSongName();
                    mediaPlayer.createPlayer();
                    mediaPlayer.getPlayer().play();
                    mediaPlayer.setStopped(false);
                    mediaPlayer.setPaused(false);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    mediaPlayer.setjSliderMaxValue();
                    mediaPlayer.setSongTime();
                    mediaPlayer.initWaveform();
                }
                setIcon(buttonReleasedImage);
            }
        });
    }

    private File[] createFileChooser()
    {
        JFileChooser openFileChooser = new MyJFileChooser(mediaPlayer.getCurrentDirectory());
        openFileChooser.setMultiSelectionEnabled(true);
        openFileChooser.setFileFilter(new FileTypeFilter(".mp3", "Open MP3 Files Only!"));
        openFileChooser.showOpenDialog(null);
        return openFileChooser.getSelectedFiles();
    }
}
