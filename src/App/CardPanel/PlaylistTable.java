package App.CardPanel;

import App.Colors;
import App.JMediaPlayer;
import App.SongInformation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

class PlaylistTable extends JTable {
    private PlaylistScrollPane scrollPane;
    PlaylistTable(PlaylistScrollPane scrollPane)
    {
        super(scrollPane.getTableModel());
        this.scrollPane = scrollPane;
        init();
        initTableColumns();
        addListeners();
    }

    private void init()
    {
        setSelectionForeground(Color.black);
        setOpaque(false);
        setShowGrid(false);
        setTableHeader(null);
        setForeground(Colors.color34_202_237);
        setFont(new Font(Font.DIALOG,Font.PLAIN, 15));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setRowHeight(18);
    }

    private void initTableColumns()
    {
        JMediaPlayer mediaPlayer = scrollPane.getMediaPlayer();
        getColumnModel().getColumn(0).setPreferredWidth(30);
        getColumnModel().getColumn(1).setPreferredWidth(410);
        getColumnModel().getColumn(2).setPreferredWidth(60);
        getColumnModel().getColumn(0).setCellRenderer( new MyCellRenderer(mediaPlayer, true) );
        getColumnModel().getColumn(1).setCellRenderer( new MyCellRenderer(mediaPlayer, false) );
        getColumnModel().getColumn(2).setCellRenderer( new MyCellRenderer(mediaPlayer, true) );
    }

    private void addListeners() {
        addMouseListener();
        addEnterKeyAction();
        addDownKeyAction();
        addUpKeyAction();
        addDeleteKeyAction();
        setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent dtde) {
                if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    Transferable t = dtde.getTransferable();
                    java.util.List fileList;
                    try {
                        fileList =  (java.util.List)t.getTransferData(DataFlavor.javaFileListFlavor);
                        File [] files = new File[fileList.size()];
                        for(int i=0;i<fileList.size();++i)
                        {
                            files[i] = (File)fileList.get(i);
                        }
                        if(getRowCount()==0)
                        {
                            JMediaPlayer mediaPlayer = scrollPane.getMediaPlayer();
                            mediaPlayer.getSongNamePanel().setSongName("No song selected.");
                            mediaPlayer.getSongNamePanel().stopMovingText();
                            mediaPlayer.getSongNamePanel().redraw();
                        }
                        ArrayList<SongInformation> info = PlaylistHandler.getInstance().addNewPlaylistInformation(files, false);
                        addValuesToTable(info);
                        scrollRectToVisible(getCellRect(getRowCount() - 1, 0, true));
                    } catch (UnsupportedFlavorException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dtde.rejectDrop();
                }
            }

        });
    }

    void addValuesToTable(ArrayList<SongInformation> infos)
    {
        Object [] values={0,"",""};
        DefaultTableModel tableModel= (DefaultTableModel) getModel();
        tableModel.setRowCount(0);
        for(SongInformation info: infos)
        {
            values[0]=Integer.toString(tableModel.getRowCount()+1);
            values[1]=info.getName();
            values[2]=PlaylistHandler.getInstance().getFormattedString(info.getSongMillis()/1000);
            tableModel.addRow(values);
        }
    }

    private void addMouseListener()
    {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JMediaPlayer mediaPlayer = scrollPane.getMediaPlayer();
                if (mouseEvent.getClickCount() == 2) {
                    mediaPlayer.updateCurrentSongIndex();
                    mediaPlayer.getCardPanel().refreshPlaylistTableCells();
                    mediaPlayer.createPlayerByCurrentIndex();
                    mediaPlayer.setStopped(false);
                    mediaPlayer.setPaused(false);
                }
                else if (mouseEvent.getClickCount()==1 && mediaPlayer.isStopped())
                {
                    mediaPlayer.getSongNamePanel().setSongName(PlaylistHandler.getInstance().getSongsInfo().
                            get(mediaPlayer.getCardPanel().getRowSelected()).getName());
                    mediaPlayer.getSongNamePanel().redraw();
                    mediaPlayer.stopMovingText();
                }
            }
        });
    }

    private void addEnterKeyAction()
    {
        getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JMediaPlayer mediaPlayer = scrollPane.getMediaPlayer();
                mediaPlayer.updateCurrentSongIndex();
                mediaPlayer.getCardPanel().refreshPlaylistTableCells();
                mediaPlayer.createPlayerByCurrentIndex();
                mediaPlayer.setStopped(false);
                mediaPlayer.setPaused(false);
            }
        });
    }

    private void addDownKeyAction()
    {
        getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
        getActionMap().put("Down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JMediaPlayer mediaPlayer = scrollPane.getMediaPlayer();
                if (getSelectedRow() < getRowCount() - 1) {
                    setRowSelectionInterval(getSelectedRow() + 1, getSelectedRow() + 1);
                    if(mediaPlayer.isStopped())
                    {
                        mediaPlayer.getSongNamePanel().setSongName(PlaylistHandler.getInstance().getSongsInfo().
                                get(mediaPlayer.getCardPanel().getRowSelected()).getName());
                        mediaPlayer.getSongNamePanel().redraw();
                        mediaPlayer.stopMovingText();
                    }
                    changeSelection(getSelectedRow(), 0, false, false);
                }
            }
        });
    }

    private void addUpKeyAction()
    {
        getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Up");
        getActionMap().put("Up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JMediaPlayer mediaPlayer = scrollPane.getMediaPlayer();
                if (getSelectedRow() > 0) {
                    setRowSelectionInterval(getSelectedRow() - 1, getSelectedRow() - 1);
                    if(mediaPlayer.isStopped())
                    {
                        mediaPlayer.getSongNamePanel().setSongName(PlaylistHandler.getInstance().getSongsInfo().
                                get(mediaPlayer.getCardPanel().getRowSelected()).getName());
                        mediaPlayer.getSongNamePanel().redraw();
                        mediaPlayer.stopMovingText();
                    }
                    changeSelection(getSelectedRow(), 0, false, false);
                }
            }
        });
    }

    private void addDeleteKeyAction()
    {
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
        getActionMap().put("Delete", new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                deleteSelectedSong();
            }
        });
    }

    void deleteSelectedSong()
    {
        JMediaPlayer mediaPlayer = scrollPane.getMediaPlayer();
        if (getSelectedRow() != -1) {
            scrollPane.removeRow(Integer.parseInt((String) getValueAt(getSelectedRow(), 0)) - 1);
            scrollPane.checkIfSongDeletedIsUnderSongSelected();
            if(mediaPlayer.isStopped()) {
                try {
                    mediaPlayer.getSongNamePanel().setSongName(PlaylistHandler.getInstance().getSongsInfo().
                            get(mediaPlayer.getCardPanel().getRowSelected()).getName());
                }catch (ArrayIndexOutOfBoundsException e) {
                    if (getRowCount() == 0) {
                        mediaPlayer.getSongNamePanel().setSongName("No sound available.");
                    } else {
                        mediaPlayer.getSongNamePanel().setSongName("No sound selected.");
                    }
                }
                mediaPlayer.getSongNamePanel().stopMovingText();
                mediaPlayer.getSongNamePanel().redraw();
            }
        }
    }
}

