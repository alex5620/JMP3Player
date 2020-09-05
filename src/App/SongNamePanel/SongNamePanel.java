package App.SongNamePanel;

import App.CardPanel.PlaylistHandler;
import App.Colors;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.File;
public class SongNamePanel extends JPanel{
    private MovingPanel textPanel;
    public SongNamePanel(File songFile)
    {
        setBounds(0, 44, 550, 50);
        setBackground(Colors.color7_63_86);
        setLayout(null);
        setInitialText(songFile);
        initBorderPanel();
    }

    private void setInitialText(File songFile)
    {
        if(songFile!=null) {
            initTextPanel(PlaylistHandler.getInstance().getSongsInfo().get(0).getName());
        }
        else
        {
            initTextPanel("No song available.");
        }
    }

    private void initBorderPanel()
    {
        JPanel borderPanel = new JPanel();
        borderPanel.setBounds(10, 8, 530, 34);
        borderPanel.setBackground(Colors.color7_63_86);
        borderPanel.setBorder(new LineBorder(Colors.color34_202_237));
        add(borderPanel);
    }

    private void initTextPanel(String startingSongName)
    {
        textPanel = new MovingPanel(startingSongName);
        textPanel.setBackground(Colors.color7_63_86);
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

    public void startMovingText()
    {
        textPanel.start();
    }

    public void redraw()
    {
        textPanel.repaint();
    }

    public void songNameToCenter()
    {
        textPanel.textToCenter();
    }

    public void songNameToBeginning()
    {
        textPanel.textToBeginning();
    }
}
