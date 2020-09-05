package App.EqualizerWindow;

import javax.swing.*;
import java.awt.*;

public class RotatedIcon implements Icon
{
    private Icon icon;
    private double degrees;
    private boolean circularIcon;

    public RotatedIcon(Icon icon, double degrees, boolean circularIcon)
    {
        this.icon = icon;
        setDegrees( degrees );
        setCircularIcon( circularIcon );
    }

    private void setDegrees(double degrees)
    {
        this.degrees = degrees;
    }

    private void setCircularIcon(boolean circularIcon)
    {
        this.circularIcon = circularIcon;
    }

    @Override
    public int getIconWidth()
    {
        if (circularIcon)
            return icon.getIconWidth();
        else
        {
            double radians = Math.toRadians( degrees );
            double sin = Math.abs( Math.sin( radians ) );
            double cos = Math.abs( Math.cos( radians ) );
            return (int)Math.floor(icon.getIconWidth() * cos + icon.getIconHeight() * sin);
        }
    }

    @Override
    public int getIconHeight()
    {
            if (circularIcon)
                return icon.getIconHeight();
            else
            {
                double radians = Math.toRadians( degrees );
                double sin = Math.abs( Math.sin( radians ) );
                double cos = Math.abs( Math.cos( radians ) );
                return (int)Math.floor(icon.getIconHeight() * cos + icon.getIconWidth() * sin);
            }
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
        Graphics2D g2 = (Graphics2D)g.create();

        int cWidth = icon.getIconWidth() / 2;
        int cHeight = icon.getIconHeight() / 2;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(x, y, getIconWidth(), getIconHeight());
        g2.translate((getIconWidth() - icon.getIconWidth()) / 2, (getIconHeight() - icon.getIconHeight()) / 2);
        g2.rotate(Math.toRadians(degrees), x + cWidth, y + cHeight);
        icon.paintIcon(c, g2, x, y);

        g2.dispose();
    }
}
