package App.CardPanel;

import App.Colors;
import App.FileTypeFilter;
import App.JMediaPlayer;
import App.SongInformation;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;

public class PlaylistPanel extends JPanel {
    private PlaylistScrollPane playlistScrollPane;
    private AddButton addButton;
    private JLabel removeButton;

    PlaylistPanel(JMediaPlayer mediaPlayer) {
        setBackground(Colors.color7_63_86);
        setLayout(null);
        playlistScrollPane = new PlaylistScrollPane(mediaPlayer);
        add(playlistScrollPane.getScrollPane());
        createSongFilterPanel();
        addButton = new AddButton(this, mediaPlayer);
        add(addButton);
        initRemoveButton();
        populatePlaylistTable();
    }

    public int getRowSelected()
    {
        return playlistScrollPane.getRowSelected();
    }

    private void createSongFilterPanel()
    {
        JPanel songFilterPanel=new JPanel();
        songFilterPanel.setBounds(40,315, 360,60);
        songFilterPanel.setOpaque(false);
        SongFilter songFilter = new SongFilter(this);
        JLabel label= new JLabel("Search song:    ");
        label.setFont(new Font(Font.DIALOG,Font.PLAIN, 16));
        label.setForeground(Colors.color34_202_237);
        songFilterPanel.add(label);
        songFilterPanel.add(songFilter);
        add(songFilterPanel);
    }

    private void initRemoveButton()
    {
        removeButton = new JLabel();
        removeButton.setBounds(440,310, 40,40);
        removeButton.setIcon(new ImageIcon("images/remove_button.png"));
        removeButton.setToolTipText("Remove selected song");
        addListenerToRemoveButton();
        add(removeButton);
    }

    private void addListenerToRemoveButton()
    {
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playlistScrollPane.getTable().deleteSelectedSong();
            }
        });
    }

    public void removeRow(int arrayIndex)
    {
        playlistScrollPane.removeRow(arrayIndex);
    }

    private void populatePlaylistTable()
    {
        ArrayList<SongInformation> songs = PlaylistHandler.getInstance().getSongsInfo();
        Object [] values={"","",""};
        int size = songs.size();
        DefaultTableModel tableModel = playlistScrollPane.getTableModel();
        for(int i=0;i<size;++i)
        {
            values[0]=Integer.toString(i+1);
            values[1]=songs.get(i).getName();
            values[2]=PlaylistHandler.getInstance().getFormattedString(songs.get(i).getSongMillis()/1000);
            tableModel.addRow(values);
        }
        if(size>0) {
            selectRow(0);
        }
    }

    void selectRow(int index)
    {
        if(playlistScrollPane.getTableModel().getRowCount()>=0)
        {
            playlistScrollPane.getTable().setRowSelectionInterval(index, index);
        }
    }

    public void delesectRow()
    {
        playlistScrollPane.getTable().clearSelection();
    }

    void addValuesToTable(ArrayList<SongInformation> infos)
    {
        addButton.addValuesToTable(infos);
    }

    public void refreshTableCells()
    {
        playlistScrollPane.refreshTableCells();
    }

    public int getIndexOfSelectedRow(){
        return playlistScrollPane.getIndexOfSelectedRow();
    }

    PlaylistScrollPane getPlaylistScrollPane()
    {
        return playlistScrollPane;
    }
}

