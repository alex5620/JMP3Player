package App.CardPanel;

import App.JMediaPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayingNowPanel extends JPanel implements ActionListener {
    private JMediaPlayer mediaPlayer;
    private JPanel subPanel;
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
        subPanel = new JPanel()
        {
            @Override
            public void paint(Graphics g)
            {
                g.clearRect(0,0,getWidth(), getHeight());
                g.setColor(new Color(7,63,86, 240));
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setColor(new Color(7, 48, 65, 150));
                g.drawRect(0, 0, getWidth()-1, getHeight()-1);

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
        subPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentColor=(int)(Math.random()*5);
            }
        });
        subPanel.setBounds(25,25,500,325);
        subPanel.setLayout(null);
        add(subPanel);
    }

    public void setAudioToDisplay(AudioInputStream audioInputStream){
        audioInfo = new AudioInfo(audioInputStream, mediaPlayer);
        increment = audioInfo.getChannelsWithSamples()[0].length/(mediaPlayer.getPlayer().getTotalDuration().toMillis()/100);
        channelsNumber = audioInfo.getChannelsWithSamples().length;
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        subPanel.repaint();
    }
}
