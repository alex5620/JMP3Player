package App.SongNamePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SongNamePanel extends JPanel{
    private JPanel borderPanel;
    private MovingPanel textPanel;
    public SongNamePanel()
    {
        setBounds(0, 44, 550, 50);
        setBackground(new Color(7,63,86));
        setLayout(null);
        initTextPanel();
        initBorderPanel();
        textPanel.start();
    }

    private void initBorderPanel()
    {
        borderPanel = new JPanel();
        borderPanel.setBounds(10, 8, 530, 34);
        borderPanel.setBackground(new Color(7,63,86));
        borderPanel.setBorder(new LineBorder(new Color(34, 202, 237)));
        add(borderPanel);
    }

    private void initTextPanel()
    {
        textPanel = new MovingPanel();
        textPanel.setBackground(new Color(7,63,86));
        textPanel.setBounds(15, 8, 510, 34);
        textPanel.setOpaque(false);
        textPanel.setLayout(null);
        add(textPanel);
    }

    public void setSongName(String songName)
    {
        textPanel.setSongName(songName);
        textPanel.start();
    }

    public void stopMovingText()
    {
        textPanel.stop();
    }
}
