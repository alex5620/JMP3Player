package App.SongNamePanel;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class MovingPanel extends JPanel implements ActionListener{
    private static int RATE = 16;
    private Timer timer = new Timer(1000/RATE, this);
    private String songText;
    private int index;

    public MovingPanel(String startingSongName)
    {
        songText = startingSongName;
        index = 255 - (songText.length()*6) / 2;
        // jumatatea panelului
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(34,202,237));
        g2.drawString(songText, index, 22);
        index+=5;
        if(index >= getWidth())
        {
            index=10;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }

    public void setSongName(String text)
    {
        songText=text;
    }

    public void start()
    {
        timer.start();
    }

    public void stop()
    {
        timer.stop();
    }

    public void textToCenter()
    {
        try {
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            int textwidth = (int) (getGraphics().getFont().getStringBounds(songText, frc).getWidth());
            index = getWidth() / 2 - textwidth / 2;
            repaint();
            stop();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void textToBeginning()
    {
        index=10;
    }
}
