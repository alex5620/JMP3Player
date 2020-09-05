package App.CardPanel;

import App.ButtonsPanel.AbstractButton;
import App.FileTypeFilter;
import App.JMediaPlayer;
import App.SongInformation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.io.File;

public class AddButton extends AbstractButton {
    private PlaylistPanel playlistPanel;
    public AddButton(PlaylistPanel playlistPanel, JMediaPlayer mediaPlayer)
    {
        super(mediaPlayer);
        this.playlistPanel = playlistPanel;
        init();
    }

    private void init()
    {
        setBounds(390,310, 40,40);
        setIcon(new ImageIcon("images/add_button.png"));
        setToolTipText("Add songs");
        addListeners();
    }

    @Override
    protected void addListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser openFileChooser = new MyJFileChooser(mediaPlayer.getCurrentDirectory());
                openFileChooser.setMultiSelectionEnabled(true);
                openFileChooser.setFileFilter(new FileTypeFilter(".mp3", "Open MP3 Files Only!"));
                openFileChooser.showOpenDialog(null);
                File[] files = openFileChooser.getSelectedFiles();
                if(files.length>0)
                {
                    JTable table = playlistPanel.getPlaylistScrollPane().getTable();
                    if(table.getRowCount()==0)
                    {
                        mediaPlayer.getSongNamePanel().setSongName("No song selected.");
                        mediaPlayer.getSongNamePanel().stopMovingText();
                        mediaPlayer.getSongNamePanel().redraw();
                    }
                    ArrayList<SongInformation> info = PlaylistHandler.getInstance().addNewPlaylistInformation(files, false);
                    addValuesToTable(info);
                    mediaPlayer.updateCurrentDirectory(files[0].getAbsolutePath());
                    table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0, true));
                }
            }
        });
    }

    void addValuesToTable(ArrayList<SongInformation> infos)
    {
        playlistPanel.getPlaylistScrollPane().getTable().addValuesToTable(infos);
    }
}
