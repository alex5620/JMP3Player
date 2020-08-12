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
    private Color [] colors = {Color.YELLOW,Color.green, Color.red, Color.CYAN, Color.MAGENTA};
    private int currentColor=0;

    public PlayingNowPanel(JMediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        setBackground(new Color(7,63,86));
        setLayout(null);
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
                g.setColor(new Color(7,63,86, 240));
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setColor(new Color(34,202,237));
                g.drawRect(0, 0, getWidth()-1, getHeight()-1);

                if(checkIfSongPlaying()==false)
                {
                    String str="No song playing";
                    g.drawString(str, getWidth()/2-g.getFontMetrics().stringWidth(str)/2,
                            getHeight()/2);
                    return;
                }

                int oldY = (getHeight() / 2);
                g.setColor(colors[currentColor]);

                for(int i = 0; i < getWidth();i+=1)
                {
                    double scaleFactor = 1.0/200;
                    double scaledSample=0;
                    for(int j=0;j<channelsNumber;++j)
                    {
                        scaledSample = audioInfo.getChannelsWithSamples()[j][(int)((mediaPlayer.getPlayer().getCurrentTime()
                                .toMillis()/100)*increment)+i] * scaleFactor;
                    }
                    scaledSample/=channelsNumber;
                    int y = (int) ((getHeight() / 2) - (scaledSample));
                    g.drawLine(i, oldY, i+1, y);
                    oldY = y;
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
                currentColor=(int)(Math.random()*5);
            }
        });
    }

    public void setAudioToDisplay(AudioInputStream audioInputStream){
        audioInfo = new AudioInfo(audioInputStream, mediaPlayer);
        increment = audioInfo.getChannelsWithSamples()[0].length/(mediaPlayer.getPlayer().getTotalDuration().toMillis()/100);
        channelsNumber = audioInfo.getChannelsWithSamples().length;
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        waveformPanel.repaint();
    }

    public void initWaveform()
    {
        try
        {
        SongInformation song = PlaylistHandler.getInstance().getSongsInfo().get(mediaPlayer.
                getCardPanel().getRowSelected());
            File file = new File(song.getPath()+"\\"+song.getName());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            mediaPlayer.getCardPanel().play(audioInputStream);
        }catch (Exception er)
        {
            er.printStackTrace();
        }

    }

    public boolean checkIfSongPlaying()
    {
        if(mediaPlayer.getPlayer().getCurrentTime().toMillis()==0)
        {
            return false;
        }
        return true;
    }
}
