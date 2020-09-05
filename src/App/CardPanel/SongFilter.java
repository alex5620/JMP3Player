package App.CardPanel;

import App.Colors;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

class SongFilter extends JTextField {
    SongFilter(PlaylistPanel playlistPanel) {
        init(playlistPanel);
    }

    private void init(PlaylistPanel playlistPanel) {
        setPreferredSize(new Dimension(200, 24));
        setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        setBackground(new Color(7, 48, 65));
        setForeground(Colors.color34_202_237);
        setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 0, 0, 100)),
                BorderFactory.createMatteBorder(2, 4, 2, 4, new Color(0, 0, 0, 0))));
        setCaretColor(Colors.color34_202_237);
        addSearchListenerToSearchIdField(playlistPanel);
    }

    private void addSearchListenerToSearchIdField(PlaylistPanel playlistPanel) {
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(playlistPanel.
                getPlaylistScrollPane().getTableModel());
        playlistPanel.getPlaylistScrollPane().getTable().setRowSorter(rowSorter);
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateRowsBySearchIdFilter(rowSorter);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateRowsBySearchIdFilter(rowSorter);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void updateRowsBySearchIdFilter(TableRowSorter<TableModel> rowSorter) {
        String text = getText();
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
        }
    }
}