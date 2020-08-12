package App.HeaderPanel;

import App.CardPanel.PlaylistHandler;
import App.JMediaPlayer;
import jaco.mp3.a.A;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import App.ButtonsPanel.AbstractButton;

public class HeaderPanel extends JPanel{
    private JMediaPlayer mediaPlayer;
    private JLabel appNameLabel;
    private JLabel quitLabel;
    private JLabel settingsLabel;
    private String appName;
    private int xMouse, yMouse;
    private JLabel someButton;
    private boolean windowCollapsed;
    public HeaderPanel(JMediaPlayer mediaPlayer, String appName)
    {
        this.mediaPlayer = mediaPlayer;
        setBounds(0, 0, 550, 46);
        setBackground(new Color(36,93,116));
        setLayout(null);
        addListeners();
        initAppNameLabel(appName);
        initQuitButton();
        initSettingsButton();
        someButton = new JLabel();
        someButton.setIcon(new ImageIcon("images/seek.png"));
        someButton.setBounds(400, 3, 60, 40);
        someButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Yea");;
            }
        });
        add(someButton);
    }

    private void addListeners()
    {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x=e.getXOnScreen();
                int y=e.getYOnScreen();
                mediaPlayer.getFrame().setLocation(x-xMouse, y-yMouse);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                xMouse=e.getX();
                yMouse=e.getY();
            }
        });
    }

    private void initAppNameLabel(String appName)
    {
        appNameLabel = new JLabel();
        appNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        appNameLabel.setBounds(22, 15, 400, 18);
        appNameLabel.setForeground(new Color(34,202,237));
        appNameLabel.setText(appName);
        this.appName = appName;
        addSongNameListeners();
        add(appNameLabel);
    }

    private void addSongNameListeners()
    {
        appNameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2)
                {
                    if(windowCollapsed == false)
                    {
                        windowCollapsed = true;
                        mediaPlayer.getFrame().setSize(new Dimension((int) mediaPlayer.getFrame().getSize().getWidth(), 48));
                        appNameLabel.setFont(new Font("Nirmala UI", 0, 12));
                        appNameLabel.setText("Playing now..." + mediaPlayer.getSongName());
                    }
                    else
                    {
                        windowCollapsed = false;
                        mediaPlayer.getFrame().setSize(new Dimension((int) mediaPlayer.getFrame().getSize().getWidth(), 216));
                        appNameLabel.setFont(new Font("Nirmala UI", 0, 18));
                        appNameLabel.setText(appName);
                    }
                }
            }
        });
    }

    private void initQuitButton()
    {
        quitLabel = new JLabel();
        quitLabel.setBounds(500, 4, 40, 40);
        quitLabel.setIcon(new ImageIcon(("images/quit.png")));
        addListenerToQuitButton();
        add(quitLabel);
    }

    private void addListenerToQuitButton()
    {

        quitLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mediaPlayer.getDatabase().resetDatabase();
                PlaylistHandler.getInstance().saveSongs(mediaPlayer);
                System.exit(0);
            }
        });
    }

    private void initSettingsButton()
    {
        settingsLabel = new JLabel();
        settingsLabel.setIcon(new ImageIcon("images/settings.png"));
        settingsLabel.setBounds(450, 4, 40, 40);
        addListenerToSettingsButton();
        add(settingsLabel);
    }
    private void addListenerToSettingsButton()
    {

        settingsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(mediaPlayer.getFrame(), "Message");
            }
        });
    }
}
