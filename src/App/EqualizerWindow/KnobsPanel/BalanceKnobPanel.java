package App.EqualizerWindow.KnobsPanel;

import App.CardPanel.PlaylistHandler;
import App.Colors;
import App.EqualizerWindow.RotatedIcon;
import App.JMediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class BalanceKnobPanel extends JPanel{
    private KnobsPanel parentPanel;
    private JLabel balanceButton;
    private int degrees;
    BalanceKnobPanel(KnobsPanel panel)
    {
        this.parentPanel = panel;
        setBounds(0,0,200, 190);
        setLayout(null);
        setBackground(Color.black);
        initBalanceButton();
        addBalanceLabel();
        addLLabel();
        addRLabel();
    }

    private void initBalanceButton()
    {
        double balance;
        if(PlaylistHandler.getInstance().getSongsInfo().size()>0) {
            balance = parentPanel.getMediaPlayer().getPlayer().getBalance();
        }
        else
        {
            balance=0;
        }
        degrees = (int) (balance * 50) + 50;
        balanceButton = new JLabel();
        balanceButton.setBounds(40, 25, 108, 108);
        setInitialIconToBalanceButton();
        addActionListenerToBalanceButton();
        addMouseMotionListenerToStereoButton();
        add(balanceButton);
    }

    private void setInitialIconToBalanceButton()
    {
        if(degrees<50) {
            balanceButton.setIcon(new RotatedIcon(parentPanel.getSmallKnobIcon(), 310+degrees, true));
        }
        else
        {
            balanceButton.setIcon(new RotatedIcon(parentPanel.getSmallKnobIcon(), degrees-50, true));
        }
    }

    private void addActionListenerToBalanceButton()
    {
        balanceButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(parentPanel.getMediaPlayer().getPlayer()!=null) {
                    if (degrees < 50) {
                        parentPanel.getMediaPlayer().getPlayer().setBalance(-(50 - degrees) * 2 / 100.0);
                    } else {
                        parentPanel.getMediaPlayer().getPlayer().setBalance((degrees - 50) * 2 / 100.0);
                    }
                }
            }
        });
    }

    private void addMouseMotionListenerToStereoButton()
    {
        balanceButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if((e.getX() > parentPanel.getXCoord()) && (e.getY() < balanceButton.getHeight()/2)   || // L->R
                        (e.getX() < parentPanel.getXCoord() ) && (e.getY() > balanceButton.getHeight()/2) || // R->L
                        (e.getY() < parentPanel.getYCoord() ) && (e.getX() < balanceButton.getWidth()/2)  || // D->U
                        (e.getY() > parentPanel.getYCoord()) && (e.getX() >  balanceButton.getWidth()/2))   // U-> D
                {
                    degrees += parentPanel.getIndexValue(balanceButton.getWidth() / 2);
                    if(degrees > 100)
                    {
                        degrees = 100;
                    }
                    balanceButton.setIcon(new RotatedIcon(parentPanel.getSmallKnobIcon(), degrees-50,true));
                }
                else if((e.getX() < parentPanel.getXCoord() ) && (e.getY() < balanceButton.getHeight()/2)   || // R->L
                        (e.getX() > parentPanel.getXCoord() ) && (e.getY() > balanceButton.getHeight()/2) || // L->R
                        (e.getY() > parentPanel.getYCoord() ) && (e.getX() < balanceButton.getWidth()/2)  || // U->D
                        (e.getY() < parentPanel.getYCoord() ) && (e.getX() >  balanceButton.getWidth()/2))   // D->U
                {
                    degrees -= parentPanel.getIndexValue(balanceButton.getWidth() / 2);
                    if(degrees < 0)
                    {
                        degrees = 0;
                    }
                    balanceButton.setIcon(new RotatedIcon(parentPanel.getSmallKnobIcon(), 310+degrees,true));
                }
                parentPanel.setxCoord(e.getX());
                parentPanel.setyCoord(e.getY());
            }
        });
    }

    private void addBalanceLabel()
    {
        JLabel balanceLabel = new JLabel("BALANCE");
        balanceLabel.setBounds(48, 135, 90,30);
        balanceLabel.setForeground(Colors.color46_255_0);
        balanceLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(balanceLabel);
    }

    private void addLLabel()
    {
        JLabel LLabel = new JLabel("L");
        LLabel.setBounds(37, 19, 90,30);
        LLabel.setForeground(Colors.color46_255_0);
        LLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(LLabel);
    }

    private void addRLabel()
    {
        JLabel RLabel = new JLabel("R");
        RLabel.setBounds(137, 19, 90,30);
        RLabel.setForeground(Colors.color46_255_0);
        RLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(RLabel);
    }

    void resetBalanceKnob()
    {
        if(parentPanel.getMediaPlayer().getPlayer().getBalance()!=0) {
            degrees = 50;
            balanceButton.setIcon(new RotatedIcon(parentPanel.getSmallKnobIcon(), degrees - 50, true));
            parentPanel.getMediaPlayer().getPlayer().setBalance(0);
        }
    }
}
