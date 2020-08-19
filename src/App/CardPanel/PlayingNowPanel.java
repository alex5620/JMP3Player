package App.CardPanel;

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
    private AudioInfo audioInfo = null;
    private Timer timer = new Timer(100, this);
    private double increment=0;
    private int channelsNumber;
    private Color [] colors = {Color.green, new Color(68, 62, 62), Color.yellow, Color.gray, Color.CYAN, Color.WHITE};
    private int currentColor;

    public PlayingNowPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBackground(new Color(7,63,86));
        setLayout(null);
        currentColor = mediaPlayer.getSettingsDatabaseDatabase().getSettingsInformation("color_index");
        initWaveformPanel();
    }

    private void initWaveformPanel()
    {
//        waveformPanel = new JPanel()
//        {
//            @Override
//            public void paint(Graphics g)
//            {
//                g.clearRect(0,0,getWidth(), getHeight());
//                g.setColor(new Color(7,63,86, 240));
//                g.fillRect(0, 0, getWidth(), getHeight());
//
//                g.setColor(new Color(34,202,237));
//                g.drawRect(0, 0, getWidth()-1, getHeight()-1);
//
//                if(checkIfSongPlaying()==false)
//                {
//                    String str="No song playing";
//                    g.drawString(str, getWidth()/2-g.getFontMetrics().stringWidth(str)/2,
//                            getHeight()/2);
//                    return;
//                }
//
//                int oldY = (getHeight() / 2);
//                g.setColor(colors[currentColor]);
//                for(int i = 0; i < getWidth();i+=1)
//                {
//                    double scaleFactor = 1.0/200;
//                    double scaledSample=0;
//                    for(int j=0;j<channelsNumber;++j)
//                    {
//                        scaledSample = audioInfo.getChannelsWithSamples()[j][(int)((mediaPlayer.getPlayer().getCurrentTime()
//                                .toMillis()/100)*increment)+i] * scaleFactor;
//                    }
//                    scaledSample/=channelsNumber;
//                    int y = (int) ((getHeight() / 2) - (scaledSample));
//                    g.drawLine(i, oldY, i+1, y);
//                    oldY = y;
//                }
//            }
//        };
        waveformPanel = new JPanel()
        {
            @Override
            public void paint(Graphics g)
            {
                g.clearRect(0,0,getWidth(), getHeight());
                g.setColor(new Color(7,63,86, 240));
                g.fillRect(0, 0, getWidth(), getHeight());

                if(checkIfSongPlaying()==false)
                {
                    g.setColor(new Color(34,202,237));
                    g.drawRect(0, 0, getWidth()-1, getHeight()-1);

                    String str="No song playing.";
                    g.drawString(str, getWidth()/2-g.getFontMetrics().stringWidth(str)/2,
                            getHeight()/2);
                    return;
                }
                if(audioInfo==null)
                {
                    g.setColor(new Color(34,202,237));
                    g.drawRect(0, 0, getWidth()-1, getHeight()-1);
                    String str="No information available.";
                    g.drawString(str, getWidth()/2-g.getFontMetrics().stringWidth(str)/2,
                            getHeight()/2);
                    return;
                }
                int oldY = (getHeight() / 2);
                g.setColor(colors[currentColor]);
                for(int i = 0; i < getWidth()-33 ;i+=33)
                {
                    int sum = 0;
                    double scaleFactor = 1.0/200;
                    double scaledSample=0;
//                    if(i<getWidth()-33) {
                        for (int j = 0; j < channelsNumber; ++j) {
                            for (int k = 0; k < 33; ++k) {
                                scaledSample = audioInfo.getChannelsWithSamples()[j][(int) ((mediaPlayer.getPlayer().getCurrentTime()
                                        .toMillis() / 100) * increment) + i + k] * scaleFactor;
                            }
                            sum += scaledSample;
                        }
                        sum /= channelsNumber;
                        int y = ((getHeight() / 2) - (sum));
//
//                        g.setColor(colors[currentColor]);
//                        g.fillRect(i+2, getHeight() - y, 33, y);
//                        g.setColor(Color.black);
//                        g.drawRect(i+2, getHeight() - y, 33, y);
//                        oldY = y;

                        for(int j=0; j<y ; ++j)
                        {
                            if(getHeight()-j < (9*getHeight())/10)
                            {
                                Color color=colors[currentColor];
                                int red = color.getRed()+((9*getHeight())/10-(getHeight()-j));
                                if(red > 255)
                                {
                                    red=255;
                                }
                                int green = color.getGreen()+(-(9*getHeight()/10)+(getHeight()-j));
                                if(green < 0)
                                {
                                    green=0;
                                }
                                g.setColor(new Color(red, green, color.getBlue()));
                            }
                            else {
                                g.setColor(colors[currentColor]);
                            }
                            g.fillRect(i+2, getHeight() -j, 33, 1);
                        }
                        g.setColor(Color.black);
                        g.drawRect(i+2, getHeight() - y, 33, y);
                        g.setColor(new Color(34,202,237));
                        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
                        g.drawString("Click to change style", 5,15);
//                    }
                }
            }
        };
        waveformPanel.setBounds(25,25,500,325);
        waveformPanel.setLayout(null);
        addMouseListenerToWaveformPanel();
        add(waveformPanel);
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

    public void setAudioToDisplay(AudioInputStream audioInputStream){
        audioInfo = new AudioInfo(audioInputStream, mediaPlayer);
        increment = audioInfo.getChannelsWithSamples()[0].length/(PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.getCurrentSongIndex()).getSongMillis()/100);
        channelsNumber = audioInfo.getChannelsWithSamples().length;
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(mediaPlayer.getPlayer().getCurrentTime().toMillis() < mediaPlayer.getPlayer().getTotalDuration().toMillis()-100) {
            waveformPanel.repaint();
        }
    }

    public void initWaveform()
    {
        try
        {
        SongInformation song = PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.
                getCurrentSongIndex());
            File file = new File(song.getPath()+"\\"+song.getName());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
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

    public boolean checkIfSongPlaying()
    {
        if(mediaPlayer.getPlayer().getCurrentTime().toMillis()==0)
        {
            return false;
        }
        return true;
    }

    public int getCurrentColor()
    {
        return currentColor;
    }
}
