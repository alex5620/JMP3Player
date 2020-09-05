package App.HeaderPanel;

import App.CardPanel.PlaylistHandler;
import App.Colors;
import App.EqualizerWindow.EqualizerWindow;
import App.JMediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class HeaderPanel extends JPanel{
    private JMediaPlayer mediaPlayer;
    private EqualizerWindow settingsWindow;
    private JLabel quitLabel;
    private JLabel settingsLabel;
    private int xMouse, yMouse;
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
        JLabel appNameLabel = new JLabel();
        appNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        appNameLabel.setBounds(22, 12, 400, 24);
        appNameLabel.setForeground(Colors.color34_202_237);
        appNameLabel.setText(appName);
        add(appNameLabel);
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
                mediaPlayer.getPlaylistDatabase().resetDatabase();
                PlaylistHandler.getInstance().saveSongs(mediaPlayer);
                int volume = mediaPlayer.getButtonsPanel().getVolumeValueLabel();
                int color = mediaPlayer.getCardPanel().getPlayingNowPanel().getCurrentColor();
                mediaPlayer.getSettingsDatabaseDatabase().resetDatabase();
                mediaPlayer.getSettingsDatabaseDatabase().saveSettings(volume, color, mediaPlayer.getCurrentDirectory());
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
                if (!mediaPlayer.isEqualizerActive()) {
                    mediaPlayer.setEqualizerActive(true);
                    settingsWindow = new EqualizerWindow(mediaPlayer);
                    settingsWindow.addFrequenciesSliders();
                }
            }
        });
    }

    public void updateFrequenciesSliders()
    {
        settingsWindow.addFrequenciesSliders();
    }

    public void updateFrequencies() { settingsWindow.updateFrequencies(); }
}
