package App.SongNamePanel;

import App.Colors;

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

    MovingPanel(String startingSongName)
    {
        songText = startingSongName;
        index = 255 - (songText.length()*6) / 2;
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Colors.color34_202_237);
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

    void setSongName(String text)
    {
        songText=text;
        index = 255 - (songText.length()*6) / 2;
    }

    void start()
    {
        timer.start();
    }

    void stop()
    {
        timer.stop();
    }

    void textToCenter()
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

    void textToBeginning()
    {
        index=10;
    }
}
