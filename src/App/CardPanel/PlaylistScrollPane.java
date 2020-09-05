package App.CardPanel;

import App.Colors;
import App.JMediaPlayer;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlaylistScrollPane{
    private JMediaPlayer mediaPlayer;
    private JScrollPane scrollPane;
    private PlaylistTable table;
    private DefaultTableModel tableModel;
    PlaylistScrollPane(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        initTableModel();
        table = new PlaylistTable(this);
        initScrollPane();
    }

    private void initTableModel()
    {
        String []columns={"index","name","time"};
        tableModel = new DefaultTableModel(0, 3)
        {
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(columns);
    }

    void checkIfSongDeletedIsUnderSongSelected()
    {
        if(table.getSelectedRow()!=-1 && table.getSelectedRow()<mediaPlayer.getCurrentSongIndex())
        {
            mediaPlayer.decreaseCurrentSongIndex();
        }
    }

    void removeRow(int arrayIndex)
    {
        int rowSelected = table.getSelectedRow();
        if(!mediaPlayer.isStopped() && table.getValueAt(rowSelected,1).
                equals(PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.
                getCurrentSongIndex()).getName()))
        {
            return;
        }
        tableModel.removeRow(arrayIndex);
        PlaylistHandler.getInstance().removeSong(arrayIndex);
        if(rowSelected<table.getRowCount()) {
            table.setRowSelectionInterval(rowSelected, rowSelected);
        }
        refreshTableIndexes(arrayIndex);
        refreshTableCells();
    }

    private void refreshTableIndexes(int index)
    {
        for(int i=index; i < tableModel.getRowCount(); ++i)
        {
            tableModel.setValueAt(Integer.toString(i+1),i,0);
        }
    }

    void refreshTableCells()
    {
        table.repaint();
    }

    private void initScrollPane()
    {
        scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(true);
        scrollPane.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Colors.color34_202_237),
                BorderFactory.createMatteBorder(7, 7, 7, 7, new Color(0,0,0,0))));
        scrollPane.setBackground(Colors.color23_59_79);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBounds(25,25,500,270);
        initScrollBar();
    }

    private void initScrollBar()
    {
        scrollPane.getVerticalScrollBar().setUI(new PlaylistBasicScrollBarUI());
        scrollPane.getVerticalScrollBar().setBackground(new Color(53, 169, 180));
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(14,300));
        scrollPane.getVerticalScrollBar().setBorder(new LineBorder(Color.black,1));
    }

    JScrollPane getScrollPane()
    {
        return scrollPane;
    }

    PlaylistTable getTable()
    {
        return table;
    }

    DefaultTableModel getTableModel()
    {
        return tableModel;
    }

    int getIndexOfSelectedRow(){
        return Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 0));
    }

    int getRowSelected()
    {
        return table.getSelectedRow();
    }

    public JMediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
