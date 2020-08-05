package App.SongNamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovingPanel extends JPanel implements ActionListener{
    private static int RATE = 16;
    private Timer timer = new Timer(1000/RATE, (ActionListener)this);
    private String songText;
    private int index=0;

    public MovingPanel()
    {
        songText = "someText";
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
}
