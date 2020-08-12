package App.CardPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class MyCellRenderer extends DefaultTableCellRenderer {
    private boolean centered;
    public MyCellRenderer(boolean centered)
    {
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
        if (isSelected) {
            setForeground(new Color(255, 201, 0));
        } else {
            setForeground(new Color(34,202,237));
        }
        setBorder(noFocusBorder);
        return this;
    }
}

