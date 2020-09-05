package App.CardPanel;

import App.Colors;
import App.JMediaPlayer;
import App.SongInformation;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class PlayingNowPanel extends JPanel implements ActionListener {
    private JMediaPlayer mediaPlayer;
    private JPanel waveformPanel;
    private AudioInfo audioInfo;
    private Timer timer;
    private double increment;
    private int channelsNumber;
    private Color [] colors = {Color.green, Colors.color68_62_62, Color.yellow, Color.gray, Color.CYAN, Color.WHITE};
    private int currentColor;

    PlayingNowPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBackground(Colors.color7_63_86);
        setLayout(null);
        currentColor = mediaPlayer.getSettingsDatabaseDatabase().getSettingsInformation("color_index");
        audioInfo = null;
        timer = new Timer(100, this);
        increment=0;
        initWaveformPanel();
    }

    private void initWaveformPanel()
    {
        waveformPanel = new JPanel()
        {
            @Override
            public void paint(Graphics g)
            {
                g.clearRect(0,0,getWidth(), getHeight());
                g.setColor(Colors.color7_63_86_240);
                g.fillRect(0, 0, getWidth(), getHeight());

                if(!checkIfSongPlaying())
                {
                    g.setColor(Colors.color34_202_237);
                    g.drawRect(0, 0, getWidth()-1, getHeight()-1);
                    String str="No song playing.";
                    g.drawString(str, getWidth()/2-g.getFontMetrics().stringWidth(str)/2,
                            getHeight()/2);
                    return;
                }
                if(audioInfo==null)
                {
                    g.setColor(Colors.color34_202_237);
                    g.drawRect(0, 0, getWidth()-1, getHeight()-1);
                    String str="No information available.";
                    g.drawString(str, getWidth()/2-g.getFontMetrics().stringWidth(str)/2,
                            getHeight()/2);
                    return;
                }
                g.setColor(colors[currentColor]);
                for(int i = 0; i < getWidth()-33 ;i+=33)
                {
                    int sum = 0;
                    double scaleFactor = 1.0/200;
                    double scaledSample=0;
                        for (int j = 0; j < channelsNumber; ++j) {
                            for (int k = 0; k < 33; ++k) {
                                scaledSample = audioInfo.getChannelsWithSamples()[j][(int) ((mediaPlayer.getPlayer().getCurrentTime()
                                        .toMillis() / 100) * increment) + i + k] * scaleFactor;
                            }
                            sum += scaledSample;
                        }
                        sum /= channelsNumber;
                        int y = ((getHeight() / 2) - (sum));

                        for(int j=0; j<y ; ++j)
                        {
                            if(getHeight()-j < (9*getHeight())/10)
                            {
                                g.setColor(getNewColor(getHeight(), j));
                            }
                            else {
                                g.setColor(colors[currentColor]);
                            }
                            g.fillRect(i+2, getHeight() -j, 33, 1);
                        }
                        g.setColor(Color.black);
                        g.drawRect(i+2, getHeight() - y, 33, y);
                        g.setColor(Colors.color34_202_237);
                        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
                        g.drawString("Click to change style", 5,15);
                }
            }
        };
        waveformPanel.setBounds(25,25,500,325);
        waveformPanel.setLayout(null);
        addMouseListenerToWaveformPanel();
        add(waveformPanel);
    }

    private Color getNewColor(int height, int j)
    {
        Color color=colors[currentColor];
        int red = color.getRed()+((9*height)/10-(height-j));
        if(red > 255)
        {
            red=255;
        }
        int green = color.getGreen()+(-(9*height/10)+(height-j));
        if(green < 0)
        {
            green=0;
        }
        return new Color(red, green, color.getBlue());
    }

    private void addMouseListenerToWaveformPanel()
    {
        waveformPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkIfSongPlaying() && audioInfo!=null) {
                    currentColor++;
                    currentColor %= 6;
                }
            }
        });
    }

    private void setAudioToDisplay(AudioInputStream audioInputStream){
        audioInfo = new AudioInfo(audioInputStream, mediaPlayer);
        int len = audioInfo.getChannelsWithSamples()[0].length;
        int milliHundreds = (PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.getCurrentSongIndex()).getSongMillis()/100);
        increment = len/milliHundreds;
        channelsNumber = audioInfo.getChannelsWithSamples().length;
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(mediaPlayer.getPlayer().getCurrentTime().toMillis() < mediaPlayer.getPlayer().getTotalDuration().toMillis()-100) {
            waveformPanel.repaint();
        }
    }

    void initWaveform()
    {
        SongInformation song = PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.
                getCurrentSongIndex());
        File file = new File(song.getPath()+"\\"+song.getName());
        try(AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file))
        {
            setAudioToDisplay(audioInputStream);
        }catch (Exception er)
        {
            resetAudioInfo();
            er.printStackTrace();
        }

    }

    public void resetAudioInfo()
    {
        audioInfo=null;
    }

    private boolean checkIfSongPlaying()
    {
        return mediaPlayer.getPlayer() == null || mediaPlayer.getPlayer().getCurrentTime().toMillis() != 0;
    }

    public int getCurrentColor()
    {
        return currentColor;
    }
}
