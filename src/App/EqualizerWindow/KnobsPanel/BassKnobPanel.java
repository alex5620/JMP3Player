package App.EqualizerWindow.KnobsPanel;

import App.Colors;
import App.EqualizerWindow.RotatedIcon;
import javafx.collections.ObservableList;
import javafx.scene.media.EqualizerBand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class BassKnobPanel extends JPanel {
    private KnobsPanel parentPanel;
    private JLabel bassButton;
    private int bassButtonDegrees=50;

    BassKnobPanel(KnobsPanel panel)
    {
        this.parentPanel = panel;
        setBounds(380,0,210, 190);
        setLayout(null);
        setBackground(Color.black);
        initBassButton();
        initBassLabel();
        initMinLabel();
        initMediumLabel();
        initDeepLabel();
    }

    private void initBassButton()
    {
        bassButton = new JLabel();
        bassButton.setBounds(38, 25, 108, 108);
        bassButton.setIcon(new RotatedIcon(parentPanel.getSmallKnobIcon(), bassButtonDegrees, true));
        addActionListenerToBassButton();
        addMouseMotionListenerToBassButton();
        add(bassButton);
    }

    private void addActionListenerToBassButton()
    {
        bassButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (parentPanel.getMediaPlayer().getPlayer() != null) {
                    ObservableList<EqualizerBand> bands = parentPanel.getMediaPlayer().getPlayer().getAudioEqualizer().getBands();
                    bands.get(0).setGain(((bassButtonDegrees - 50) / 180.0) * 6);
                    bands.get(1).setGain(((bassButtonDegrees - 50) / 180.0) * 10);
                    bands.get(2).setGain((-(bassButtonDegrees - 50) / 180.0) * 10);
                    bands.get(3).setGain((-(bassButtonDegrees - 50) / 180.0) * 6);
                    parentPanel.getMediaPlayer().updateFrequencies();
                }
            }
        });
    }

    private void addMouseMotionListenerToBassButton()
    {
        bassButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if((e.getX() > parentPanel.getXCoord()) && (e.getY() < bassButton.getHeight()/2)   || // L->R
                        (e.getX() < parentPanel.getXCoord()) && (e.getY() > bassButton.getHeight()/2) || // R->L
                        (e.getY() < parentPanel.getYCoord()) && (e.getX() < bassButton.getWidth()/2)  || // D->U
                        (e.getY() > parentPanel.getYCoord()) && (e.getX() >  bassButton.getWidth()/2))   // U-> D
                {
                    bassButtonDegrees += parentPanel.getIndexValue(bassButton.getWidth() / 2);
                    if(bassButtonDegrees > 230)
                    {
                        bassButtonDegrees = 230;
                    }
                    bassButton.setIcon(new RotatedIcon(parentPanel.getSmallKnobIcon(), bassButtonDegrees,true));
                }
                else if((e.getX() < parentPanel.getXCoord()) && (e.getY() < bassButton.getHeight()/2)   || // R->L
                        (e.getX() > parentPanel.getXCoord()) && (e.getY() > bassButton.getHeight()/2) || // L->R
                        (e.getY() > parentPanel.getYCoord()) && (e.getX() < bassButton.getWidth()/2)  || // U->D
                        (e.getY() < parentPanel.getYCoord()) && (e.getX() >  bassButton.getWidth()/2))   // D->U
                {
                    bassButtonDegrees -= parentPanel.getIndexValue(bassButton.getWidth() / 2);
                    if(bassButtonDegrees < 50)
                    {
                        bassButtonDegrees = 50;
                    }
                    bassButton.setIcon(new RotatedIcon(parentPanel.getSmallKnobIcon(), bassButtonDegrees,true));
                }
                parentPanel.setxCoord(e.getX());
                parentPanel.setyCoord(e.getY());
            }
        });
    }

    private void initBassLabel()
    {
        JLabel bassLabel = new JLabel("BASS");
        bassLabel.setBounds(69, 135, 90,30);
        bassLabel.setForeground(Colors.color46_255_0);
        bassLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(bassLabel);
    }

    private void initMinLabel()
    {
        JLabel minBassLabel = new JLabel("MINI");
        minBassLabel.setBounds(138, 19, 90,30);
        minBassLabel.setForeground(Colors.color46_255_0);
        minBassLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(minBassLabel);
    }

    private void initMediumLabel()
    {
        JLabel mediumBassLabel = new JLabel("MEDIUM");
        mediumBassLabel.setBounds(138, 103, 90,30);
        mediumBassLabel.setForeground(Colors.color46_255_0);
        mediumBassLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 16));
        add(mediumBassLabel);
    }

    private void initDeepLabel()
    {
        JLabel deepBassLabel = new JLabel("DEEP");
        deepBassLabel.setBounds(0, 109, 90,30);
        deepBassLabel.setForeground(Colors.color46_255_0);
        deepBassLabel.setFont(new Font(Font.DIALOG,Font.BOLD, 18));
        add(deepBassLabel);
    }

    void resetBassKnob()
    {
        if(bassButtonDegrees!=50) {
            bassButtonDegrees = 50;
            bassButton.setIcon(new RotatedIcon(parentPanel.getSmallKnobIcon(), bassButtonDegrees, true));
        }
    }
}
