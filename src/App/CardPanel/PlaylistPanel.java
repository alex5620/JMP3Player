package App.CardPanel;

import App.FileTypeFilter;
import App.JMediaPlayer;
import App.SongInformation;
import com.sun.media.ui.ToolTip;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;

public class PlaylistPanel extends JPanel {
    private JMediaPlayer mediaPlayer;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField songFilter;
    private JLabel addButton;
    private JLabel removeButton;

    public PlaylistPanel(JMediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        setBackground(new Color(7, 63, 86));
        setLayout(null);
        initTableModel();
        initTable();
        initScrollPane();
        initSongFilter();
        initAddButton();
        initRemoveButton();
        populatePlaylistTable();
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

    private void initTable()
    {
        table = new JTable(tableModel);
        table.setSelectionForeground(Color.black);
        table.setOpaque(false);
        table.setShowGrid(false);
        table.setTableHeader(null);
        table.setForeground(new Color(34,202,237));
        table.setFont(new Font(Font.DIALOG,Font.PLAIN, 16));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initTableColumns();
        table.setRowHeight(18);
        addListenerToColumnSelected();
    }

    private void initTableColumns()
    {
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(410);
        table.getColumnModel().getColumn(2).setPreferredWidth(60);
        table.getColumnModel().getColumn(0).setCellRenderer( new MyCellRenderer(mediaPlayer, true) );
        table.getColumnModel().getColumn(1).setCellRenderer( new MyCellRenderer(mediaPlayer, false) );
        table.getColumnModel().getColumn(2).setCellRenderer( new MyCellRenderer(mediaPlayer, true) );
    }

    private void addListenerToColumnSelected()
    {
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
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
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        table.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                mediaPlayer.updateCurrentSongIndex();
                mediaPlayer.getCardPanel().refreshPlaylistTableCells();
                mediaPlayer.createPlayerByCurrentIndex();
                mediaPlayer.setStopped(false);
                mediaPlayer.setPaused(false);
            }
        });
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
        table.getActionMap().put("Down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (table.getSelectedRow() < table.getRowCount() - 1) {
                    table.setRowSelectionInterval(table.getSelectedRow() + 1, table.getSelectedRow() + 1);
                    if(mediaPlayer.isStopped())
                    {
                        mediaPlayer.getSongNamePanel().setSongName(PlaylistHandler.getInstance().getSongsInfo().
                                get(mediaPlayer.getCardPanel().getRowSelected()).getName());
                        mediaPlayer.getSongNamePanel().redraw();
                        mediaPlayer.stopMovingText();
                    }
                    table.changeSelection(table.getSelectedRow(), 0, false, false);
                }
            }
        });
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Up");
        table.getActionMap().put("Up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (table.getSelectedRow() > 0) {
                    table.setRowSelectionInterval(table.getSelectedRow() - 1, table.getSelectedRow() - 1);
                    if(mediaPlayer.isStopped())
                    {
                        mediaPlayer.getSongNamePanel().setSongName(PlaylistHandler.getInstance().getSongsInfo().
                                get(mediaPlayer.getCardPanel().getRowSelected()).getName());
                        mediaPlayer.getSongNamePanel().redraw();
                        mediaPlayer.stopMovingText();
                    }
                    table.changeSelection(table.getSelectedRow(), 0, false, false);
                }
            }
        });
        table.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
        table.getActionMap().put("Delete", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                removeRow(Integer.parseInt((String)table.getValueAt(table.getSelectedRow(),0))-1);
                checkIfSongDeletedIsUnderSongSelected();
            }
        });
    }

    public int getRowSelected()
    {
        return table.getSelectedRow();
    }

    private void initScrollPane()
    {
        scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(true);
        scrollPane.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(34,202,237)),
                BorderFactory.createMatteBorder(7, 7, 7, 7, new Color(0,0,0,0))));
        scrollPane.setBackground(new Color(23, 59, 79));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBounds(25,25,500,270);
        initScrollBar();
        add(scrollPane);
    }

    private void initScrollBar()
    {
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            private final Dimension d = new Dimension();

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);;
                JScrollBar sb = (JScrollBar) c;
                if (!sb.isEnabled() || r.width > r.height) {
                    return;
                }
                g2.setPaint(new Color(7, 80, 110));
                g2.fillRect(r.x, r.y, r.width, r.height);
                g2.setPaint(new Color(13, 160, 220));
                g2.drawRect(r.x, r.y, r.width-1, r.height);
                g2.dispose();
            }

            @Override
            protected void setThumbBounds(int x, int y, int width, int height) {
                super.setThumbBounds(x, y, width, height);
                scrollbar.repaint();
            }
        });
        scrollPane.getVerticalScrollBar().setBackground(new Color(53, 169, 180));
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(14,300));
        scrollPane.getVerticalScrollBar().setBorder(new LineBorder(new Color(0,0,0),1));
    }

    private void initSongFilter()
    {
        JPanel songFilterPanel=new JPanel();
        songFilterPanel.setBounds(40,315, 360,60);
        songFilterPanel.setOpaque(false);
        songFilter=new JTextField();
        songFilter.setPreferredSize(new Dimension(200, 24));
        songFilter.setFont(new Font(Font.DIALOG,Font.PLAIN, 14));
        songFilter.setBackground(new Color(7, 48, 65));
        songFilter.setForeground(new Color(34,202,237));
        songFilter.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0,0,0,100)),
                BorderFactory.createMatteBorder(2, 4, 2, 4, new Color(0,0,0,0))));;
        songFilter.setCaretColor(new Color(34,202,237));
        JLabel label= new JLabel("Search song:    ");
        label.setFont(new Font(Font.DIALOG,Font.PLAIN, 16));
        label.setForeground(new Color(34,202,237));
        songFilterPanel.add(label);
        songFilterPanel.add(songFilter);
        add(songFilterPanel);
        addSearchListenerToSearchIdField();
    }

    private void addSearchListenerToSearchIdField()
    {
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);
        songFilter.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateRowsBySearchIdFilter(rowSorter);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateRowsBySearchIdFilter(rowSorter);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
    }
    private void updateRowsBySearchIdFilter(TableRowSorter<TableModel> rowSorter)
    {
        String text = songFilter.getText();
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
        }
    }

    private void initAddButton()
    {
        addButton = new JLabel();
        addButton.setBounds(390,310, 40,40);
        addButton.setIcon(new ImageIcon("images/add_button.png"));
        addButton.setToolTipText("Add songs");
        UIManager.put("ToolTip.background", new Color(7, 48, 65));
        UIManager.put("ToolTip.foreground", new Color(34,202,237));
        addListenerToAddButton();
        add(addButton);
    }

    private void addListenerToAddButton()
    {
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser openFileChooser = new MyJFileChooser(mediaPlayer.getCurrentDirectory());
                openFileChooser.setMultiSelectionEnabled(true);
                openFileChooser.setFileFilter(new FileTypeFilter(".mp3", "Open MP3 Files Only!"));
                openFileChooser.showOpenDialog(null);
                File[] files = openFileChooser.getSelectedFiles();
                if(files.length>0)
                {
                    ArrayList<SongInformation> info = PlaylistHandler.getInstance().addNewPlaylistInformation(files, false);
                    addValuesToTable(info);
                    mediaPlayer.updateCurrentDirectory(files[0].getAbsolutePath());
                }
            }
        });
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
                removeRow(Integer.parseInt((String)table.getValueAt(table.getSelectedRow(),0))-1);
                checkIfSongDeletedIsUnderSongSelected();
            }
        });
    }

    private void checkIfSongDeletedIsUnderSongSelected()
    {
        if(table.getSelectedRow()<mediaPlayer.getCurrentSongIndex())
        {
            mediaPlayer.decreaseCurrentSongIndex();
        }
    }

    public void removeRow(int arrayIndex)
    {
        int rowSelected = table.getSelectedRow();
        if(table.getValueAt(rowSelected,1).equals(PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.getCurrentSongIndex()).getName()) && mediaPlayer.isStopped()==false)
        {
            return;
        }
        tableModel.removeRow(arrayIndex);
        PlaylistHandler.getInstance().removeSong(arrayIndex);
        table.setRowSelectionInterval(rowSelected, rowSelected);
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

    private void populatePlaylistTable()
    {
        ArrayList<SongInformation> songs = PlaylistHandler.getInstance().getSongsInfo();
        Object [] values={"","",""};
        int size = songs.size();
        for(int i=0;i<size;++i)
        {
            values[0]=Integer.toString(i+1);
            values[1]=songs.get(i).getName();
            values[2]=PlaylistHandler.getInstance().getFormattedString(songs.get(i).getSongMillis()/1000);
            tableModel.addRow(values);
        }
        selectRow(0);
    }

    public void selectRow(int index)
    {
        if(tableModel.getRowCount()>=0)
        {
            table.setRowSelectionInterval(index, index);
        }
    }

    public void delesectRow()
    {
        table.clearSelection();
    }

    public void selectNextRow()
    {
        int currentRow = mediaPlayer.getCurrentSongIndex();
        int maxRow =  tableModel.getRowCount();
        if(currentRow < maxRow)
        {
            table.setRowSelectionInterval(currentRow+1, currentRow+1);
        }
    }

    public void addValuesToTable(ArrayList<SongInformation> infos)
    {
        Object [] values={0,"",""};
        tableModel.setRowCount(0);
        for(SongInformation info: infos)
        {
            values[0]=Integer.toString(tableModel.getRowCount()+1);
            values[1]=info.getName();
            values[2]=PlaylistHandler.getInstance().getFormattedString(info.getSongMillis()/1000);
            tableModel.addRow(values);
        }
    }

    public void refreshTableCells()
    {
        table.repaint();
    }

    public int getIndexOfSelectedRow(){
        return Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 0));
    }
}
