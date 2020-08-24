package App.HeaderPanel;

import App.CardPanel.PlaylistHandler;
import App.JMediaPlayer;
import App.SettingsWindow.SettingsWindow;
import javafx.collections.ObservableList;
import javafx.scene.media.EqualizerBand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class HeaderPanel extends JPanel{
    private JMediaPlayer mediaPlayer;
    private SettingsWindow settingsWindow;
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
                ObservableList<EqualizerBand> bands = mediaPlayer.getPlayer().getAudioEqualizer().getBands();
                bands.clear();
                double min = EqualizerBand.MIN_GAIN;
                double max = EqualizerBand.MAX_GAIN;
                double mid = (max - min)/2;
                double freq = 250;
                for(int j=0;j<7;++j)
                {
                    double theta = (double)j / (double)(6) *(2*Math.PI);
                    double scale = 0.4*(1+Math.cos(theta));
                    double gain = min + mid + (mid*scale);
                    bands.add(new EqualizerBand(freq, freq/2, gain));
                    freq *= 2;
                }
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
                        appNameLabel.setText("Playing now..." + PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.getCurrentSongIndex()).getName());
                    }
                    else
                    {
                        windowCollapsed = false;
                        mediaPlayer.getFrame().setSize(new Dimension((int) mediaPlayer.getFrame().getSize().getWidth(), 650));
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
                mediaPlayer.getPlaylistDatabase().resetDatabase();
                PlaylistHandler.getInstance().saveSongs(mediaPlayer);
                int volume = mediaPlayer.getButtonsPanel().getVolumeValue();
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
                mediaPlayer.setEqualizerActive(true);
                settingsWindow = new SettingsWindow(mediaPlayer);
                settingsWindow.addFrequenciesSliders();
            }
        });
    }

    public void updateFrequenciesSliders()
    {
        settingsWindow.addFrequenciesSliders();
    }
}
