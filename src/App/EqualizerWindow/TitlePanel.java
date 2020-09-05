package App.EqualizerWindow;

import App.Colors;
import App.JMediaPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;

class TitlePanel extends JPanel {
    private JMediaPlayer mediaPlayer;
    private JFrame frame;
    private JLabel quitButton;
    private int xMouse, yMouse;
    TitlePanel(JMediaPlayer mediaPlayer, JFrame frame)
    {
        this.mediaPlayer = mediaPlayer;
        this.frame = frame;
        setBounds(0,0,588, 40);
        setBackground(Color.black);
        setBorder(new LineBorder(Colors.color46_255_0, 1));
        setLayout(null);
        addTitleLabel();
        addQuitButton();
        addListeners();
    }

    private void addTitleLabel()
    {
        JLabel titleLabel = new JLabel("Equalizer");
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        titleLabel.setBounds(250, 6, 400, 26);
        titleLabel.setLayout(null);
        titleLabel.setForeground(Colors.color46_255_0);
        add(titleLabel);
    }

    private void addQuitButton()
    {
        quitButton = new JLabel();
        quitButton.setBounds(550, 4, 32, 32);
        quitButton.setIcon(new ImageIcon(("images/quit2.png")));
        quitButton.setLayout(null);
        addListenerToQuitButton();
        add(quitButton);
    }

    private void addListenerToQuitButton()
    {
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                mediaPlayer.setEqualizerActive(false);
            }
        });
    }

    private void addListeners()
    {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x=e.getXOnScreen();
                int y=e.getYOnScreen();
                frame.setLocation(x-xMouse, y-yMouse);
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
}
