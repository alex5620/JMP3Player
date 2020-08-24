package App.SettingsWindow;

import App.JMediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class KnobsPanel extends JPanel {
    private JMediaPlayer mediaPlayer;
    private JLabel balanceButton;
    private JLabel balanceLabel;
    private JLabel LLabel,RLabel;
    private ImageIcon smallKnobIcon = new ImageIcon("images/knob.png");
    private int x, y;
    private int degrees=50;
    private JLabel volumeButton;
    private ImageIcon bigKnobIcon = new ImageIcon("images/bknob.png");
    private JLabel volumeLabel;
    private JLabel minLabel;
    private JLabel maxLabel;
    private int volumeButtonDegrees=35;
    private JLabel bassButton;

    public KnobsPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(0,40,700, 200);
        setLayout(null);
        setBackground(Color.black);
        initStereoButton();
        initVolumeButton();
        initBassButton();
        balanceLabel = new JLabel("BALANCE");
        balanceLabel.setBounds(48, 150, 90,30);
        balanceLabel.setForeground(new Color(46, 255, 0));
        balanceLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(balanceLabel);
        LLabel = new JLabel("L");
        LLabel.setBounds(37, 34, 90,30);
        LLabel.setForeground(new Color(46, 255, 0));
        LLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(LLabel);
        RLabel = new JLabel("R");
        RLabel.setBounds(137, 34, 90,30);
        RLabel.setForeground(new Color(46, 255, 0));
        RLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(RLabel);
        volumeLabel = new JLabel("VOLUME");
        volumeLabel.setBounds(240, 173, 90,30);
        volumeLabel.setForeground(new Color(46, 255, 0));
        volumeLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(volumeLabel);
        minLabel = new JLabel("MIN");
        minLabel.setBounds(320, 34, 90,30);
        minLabel.setForeground(new Color(46, 255, 0));
        minLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(minLabel);
        maxLabel = new JLabel("MAX");
        maxLabel.setBounds(200, 34, 90,30);
        maxLabel.setForeground(new Color(46, 255, 0));
        maxLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(maxLabel);
    }

    private void initStereoButton()
    {
        balanceButton = new JLabel();
        balanceButton.setBounds(40, 40, 108, 108);
        balanceButton.setIcon(new RotatedIcon(smallKnobIcon, 0));
        addActionListenerToStereoButton();
        addMouseMotionListenerToStereoButton();
        add(balanceButton);
    }

    private void addActionListenerToStereoButton()
    {
        balanceButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (degrees < 50) {
                    mediaPlayer.getPlayer().setBalance(-1+degrees / 100.0);
                }
                else
                {
                    mediaPlayer.getPlayer().setBalance( degrees / 100.0);
                }
            }
        });
    }

    private void addMouseMotionListenerToStereoButton()
    {
        balanceButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if((e.getX() > x) && (e.getY() < balanceButton.getHeight()/2)   || // L->R
                        (e.getX() < x ) && (e.getY() > balanceButton.getHeight()/2) || // R->L
                        (e.getY() < y ) && (e.getX() < balanceButton.getWidth()/2)  || // D->U
                        (e.getY() > y) && (e.getX() >  balanceButton.getWidth()/2))   // U-> D
                {
                    degrees += 1*getIndexValue();
                    if(degrees > 100)
                    {
                        degrees = 100;
                    }
                    balanceButton.setIcon(new RotatedIcon(smallKnobIcon, degrees-50,true));
                }
                else if((e.getX() < x ) && (e.getY() < balanceButton.getHeight()/2)   || // R->L
                        (e.getX() > x ) && (e.getY() > balanceButton.getHeight()/2) || // L->R
                        (e.getY() > y ) && (e.getX() < balanceButton.getWidth()/2)  || // U->D
                        (e.getY() < y ) && (e.getX() >  balanceButton.getWidth()/2))   // D->U
                {
                    degrees -= 1*getIndexValue();
                    if(degrees < 0)
                    {
                        degrees = 0;
                    }
                    balanceButton.setIcon(new RotatedIcon(smallKnobIcon, 310+degrees,true));
                }
                x = e.getX();
                y = e.getY();
            }
        });
    }

    private void initVolumeButton()
    {
        volumeButton = new JLabel();
        volumeButton.setBounds(220, 20, 180, 180);
        volumeButton.setIcon(new RotatedIcon(bigKnobIcon, 35, true));
        addActionListenerToVolumeButton();
        addMouseMotionListenerToVolumeButton();
        add(volumeButton);
    }

    private void addActionListenerToVolumeButton()
    {
        volumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mediaPlayer.getButtonsPanel().setjSliderValue((volumeButtonDegrees-35)/290.0);
                mediaPlayer.getPlayer().setVolume((volumeButtonDegrees-35)/290.0);
            }
        });
    }

    private void addMouseMotionListenerToVolumeButton()
    {
        volumeButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if((e.getX() > x) && (e.getY() < volumeButton.getHeight()/2)   || // L->R
                        (e.getX() < x ) && (e.getY() > volumeButton.getHeight()/2) || // R->L
                        (e.getY() < y ) && (e.getX() < volumeButton.getWidth()/2)  || // D->U
                        (e.getY() > y) && (e.getX() >  volumeButton.getWidth()/2))   // U-> D
                {
                    volumeButtonDegrees += 2;//*getIndexValue();
                    if(volumeButtonDegrees > 325)
                    {
                        volumeButtonDegrees = 325;
                    }
                    volumeButton.setIcon(new RotatedIcon(bigKnobIcon, volumeButtonDegrees,true));
                }
                else if((e.getX() < x ) && (e.getY() < volumeButton.getHeight()/2)   || // R->L
                        (e.getX() > x ) && (e.getY() > volumeButton.getHeight()/2) || // L->R
                        (e.getY() > y ) && (e.getX() < volumeButton.getWidth()/2)  || // U->D
                        (e.getY() < y ) && (e.getX() >  volumeButton.getWidth()/2))   // D->U
                {
                    volumeButtonDegrees -= 2;//*getIndexValue();
                    if(volumeButtonDegrees < 35)
                    {
                        volumeButtonDegrees = 35;
                    }
                    volumeButton.setIcon(new RotatedIcon(bigKnobIcon, volumeButtonDegrees,true));
                }
                x = e.getX();
                y = e.getY();
            }
        });
    }

    private void initBassButton()
    {
        bassButton = new JLabel();
        bassButton.setBounds(412, 40, 108, 108);
        bassButton.setIcon(new RotatedIcon(smallKnobIcon, 0));
        addActionListenerToStereoButton();
        addMouseMotionListenerToStereoButton();
        add(bassButton);
    }

    private int getIndexValue()
    {
        int index=0;
        int radius = balanceButton.getWidth()/2;
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
        int xCircleCenter= balanceButton.getWidth()/2;
        int yCircleCenter= balanceButton.getHeight()/2;
        int pointerCircleCenterDistance = (x - xCircleCenter)*(x - xCircleCenter) + (y - yCircleCenter)*(y-yCircleCenter);
        if(pointerCircleCenterDistance < radius*radius)
        {
            return true;
        }
        return false;
    }
}
