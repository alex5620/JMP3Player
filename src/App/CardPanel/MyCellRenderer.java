package App.CardPanel;

import App.JMediaPlayer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class MyCellRenderer extends DefaultTableCellRenderer {
    private JMediaPlayer player;
    private boolean centered;
    public MyCellRenderer(JMediaPlayer player, boolean centered)
    {
        this.player = player;
        this.centered=centered;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setOpaque(false);
        if(centered)
        {
            setHorizontalAlignment( JLabel.CENTER );
        }
        if (Integer.parseInt((String)table.getValueAt(row, 0))-1==player.getCurrentSongIndex() && player.getPlayer().getCurrentTime().toMillis()!=0) {
            if(isSelected)
            {
                setForeground(new Color(219, 255, 42));
            }
            else
            {
                setForeground(Color.green);
            }
        } else if (isSelected)
        {
            setForeground(new Color(255, 201, 0));
        }
        else {
            setForeground(new Color(34,202,237));
        }
        setBorder(noFocusBorder);
        return this;
    }
}

