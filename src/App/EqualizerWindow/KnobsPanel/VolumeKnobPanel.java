package App.EqualizerWindow.KnobsPanel;

import App.Colors;
import App.EqualizerWindow.RotatedIcon;
import App.VolumeObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class VolumeKnobPanel extends JPanel implements VolumeObserver {
    private KnobsPanel parentPanel;
    private JLabel volumeButton;
    private int volumeButtonDegrees;

    VolumeKnobPanel(KnobsPanel panel)
    {
        this.parentPanel = panel;
        setBounds(200,0,180, 190);
        setLayout(null);
        setBackground(Color.black);
        initVolumeDegrees();
        initVolumeButton();
        initVolumeLabel();
        initMinLabel();
        initMaxVolume();
    }

    private void initVolumeButton()
    {
        volumeButton = new JLabel();
        volumeButton.setBounds(20, 5, 180, 180);
        volumeButton.setIcon(new RotatedIcon(parentPanel.getBigKnobIcon(), volumeButtonDegrees, true));
        addActionListenerToVolumeButton();
        addMouseMotionListenerToVolumeButton();
        add(volumeButton);
    }

    private void addActionListenerToVolumeButton()
    {
        volumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (parentPanel.getMediaPlayer().getPlayer() != null) {
                    parentPanel.getMediaPlayer().getPlayer().setVolume((volumeButtonDegrees - 35) / 290.0);
                    parentPanel.getMediaPlayer().getButtonsPanel().setSliderValue(((volumeButtonDegrees - 35) / 290.0));
                }
            }
        });
    }

    private void addMouseMotionListenerToVolumeButton()
    {
        volumeButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if((e.getX() > parentPanel.getXCoord()) && (e.getY() < volumeButton.getHeight()/2)   || // L->R
                        (e.getX() < parentPanel.getXCoord()) && (e.getY() > volumeButton.getHeight()/2) || // R->L
                        (e.getY() < parentPanel.getYCoord()) && (e.getX() < volumeButton.getWidth()/2)  || // D->U
                        (e.getY() > parentPanel.getYCoord()) && (e.getX() >  volumeButton.getWidth()/2))   // U-> D
                {
                    volumeButtonDegrees += parentPanel.getIndexValue(volumeButton.getWidth() / 2);
                    if(volumeButtonDegrees > 325)
                    {
                        volumeButtonDegrees = 325;
                    }
                    volumeButton.setIcon(new RotatedIcon(parentPanel.getBigKnobIcon(), volumeButtonDegrees,true));
                }
                else if((e.getX() < parentPanel.getXCoord()) && (e.getY() < volumeButton.getHeight()/2)   || // R->L
                        (e.getX() > parentPanel.getXCoord()) && (e.getY() > volumeButton.getHeight()/2) || // L->R
                        (e.getY() > parentPanel.getYCoord()) && (e.getX() < volumeButton.getWidth()/2)  || // U->D
                        (e.getY() < parentPanel.getYCoord()) && (e.getX() >  volumeButton.getWidth()/2))   // D->U
                {
                    volumeButtonDegrees -= parentPanel.getIndexValue(volumeButton.getWidth() / 2);
                    if(volumeButtonDegrees < 35)
                    {
                        volumeButtonDegrees = 35;
                    }
                    volumeButton.setIcon(new RotatedIcon(parentPanel.getBigKnobIcon(), volumeButtonDegrees,true));
                }
                parentPanel.setxCoord(e.getX());
                parentPanel.setyCoord(e.getY());
            }
        });
    }

    private void initVolumeDegrees()
    {
        if(parentPanel.getMediaPlayer().getPlayer()!=null) {
            volumeButtonDegrees = (int) (parentPanel.getMediaPlayer().getPlayer().getVolume() * 290) + 35;
        }
        else
        {
            volumeButtonDegrees = 35;
        }
    }

    private void initVolumeLabel()
    {
        JLabel volumeLabel = new JLabel("VOLUME");
        volumeLabel.setBounds(40, 158, 90,30);
        volumeLabel.setForeground(Colors.color46_255_0);
        volumeLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(volumeLabel);
    }

    private void initMinLabel()
    {
        JLabel minVolLabel = new JLabel("MIN");
        minVolLabel.setBounds(120, 19, 90,30);
        minVolLabel.setForeground(Colors.color46_255_0);
        minVolLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(minVolLabel);
    }

    private void initMaxVolume()
    {
        JLabel maxVolLabel = new JLabel("MAX");
        maxVolLabel.setBounds(0, 19, 90,30);
        maxVolLabel.setForeground(Colors.color46_255_0);
        maxVolLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(maxVolLabel);
    }

    @Override
    public void update() {
        volumeButtonDegrees = (int)(parentPanel.getMediaPlayer().getButtonsPanel().getVolumeValueLabel()*2.9)+35;
        volumeButton.setIcon(new RotatedIcon(parentPanel.getBigKnobIcon(), volumeButtonDegrees,true));
    }

    void resetVolumeKnob()
    {
        volumeButtonDegrees=180;
        parentPanel.getMediaPlayer().getPlayer().setVolume((volumeButtonDegrees-35)/290.0);
        parentPanel.getMediaPlayer().getButtonsPanel().setSliderValue(((volumeButtonDegrees-35)/290.0));
    }
}
