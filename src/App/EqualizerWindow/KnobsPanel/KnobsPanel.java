package App.EqualizerWindow.KnobsPanel;

import App.JMediaPlayer;

import javax.swing.*;

public class KnobsPanel extends JPanel {
    private JMediaPlayer mediaPlayer;
    private BalanceKnobPanel balanceKnobPanel;
    private VolumeKnobPanel volumeKnobPanel;
    private BassKnobPanel bassKnobPanel;
    private ImageIcon smallKnobIcon = new ImageIcon("images/knob.png");
    private ImageIcon bigKnobIcon = new ImageIcon("images/bknob.png");
    private int xCoord, yCoord;

    public KnobsPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(0,40,600, 190);
        setLayout(null);
        balanceKnobPanel = new BalanceKnobPanel(this);
        add(balanceKnobPanel);
        volumeKnobPanel = new VolumeKnobPanel(this);
        mediaPlayer.getButtonsPanel().getVolumeSubject().attach(volumeKnobPanel);
        bassKnobPanel = new BassKnobPanel(this);
        add(bassKnobPanel);
        add(volumeKnobPanel);
    }

    int getIndexValue(int radius)
    {
        int index=0;
        if(checkIfPointIsInsideOfCircle(radius) && !checkIfPointIsInsideOfCircle((radius/2)))
        {
            index=2;
        }
        else if((checkIfPointIsInsideOfCircle(radius/2)))
        {
            index=1;
        }
        return index;
    }

    private boolean checkIfPointIsInsideOfCircle(int radius)
    {
        int xCircleCenter= radius;
        int yCircleCenter= radius;
        int pointerCircleCenterDistance = (xCoord - xCircleCenter)*(xCoord - xCircleCenter) + (yCoord - yCircleCenter)*(yCoord -yCircleCenter);
        return pointerCircleCenterDistance < radius * radius;
    }

    public void resetKnobs() {
        balanceKnobPanel.resetBalanceKnob();
        volumeKnobPanel.resetVolumeKnob();
        bassKnobPanel.resetBassKnob();
    }

    public JMediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }
    
    ImageIcon getSmallKnobIcon()
    {
        return smallKnobIcon;
    }

    ImageIcon getBigKnobIcon()
    {
        return bigKnobIcon;
    }

    int getXCoord()
    {
        return xCoord;
    }
    
    int getYCoord()
    {
        return yCoord;
    }
    
    void setxCoord(int newX)
    {
        xCoord = newX;
    }
    
    void setyCoord(int newY)
    {
        yCoord = newY;
    }
}
