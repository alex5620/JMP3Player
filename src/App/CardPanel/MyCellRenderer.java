package App.CardPanel;

import App.Colors;
import App.JMediaPlayer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class MyCellRenderer extends DefaultTableCellRenderer {
    private JMediaPlayer player;
    private boolean centered;
    MyCellRenderer(JMediaPlayer player, boolean centered)
    {
        this.player = player;
        this.centered=centered;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setOpaque(false);
        handleCellCentered();
        if (Integer.parseInt((String)table.getValueAt(row, 0))-1==player.getCurrentSongIndex() && player.getPlayer().getCurrentTime().toMillis()!=0) {
            handleCellSelected(isSelected);
        } else if (isSelected) {
            setForeground(Colors.color255_201_0);
        }
        else {
            setForeground(Colors.color34_202_237);
        }
        setBorder(noFocusBorder);
        return this;
    }

    private void handleCellCentered()
    {
        if(centered)
        {
            setHorizontalAlignment( JLabel.CENTER );
        }
    }

    private void handleCellSelected(boolean isSelected)
    {
        if(isSelected)
        {
            setForeground(Colors.color219__255_42);
        }
        else
        {
            setForeground(Color.green);
        }
    }
}

