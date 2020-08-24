package App.SettingsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class TitlePanel extends JPanel {
    private JFrame frame;
    private JLabel titleLabel;
    private int xMouse, yMouse;
    public TitlePanel(JFrame frame)
    {
        this.frame = frame;
        setBounds(0,0,700, 40);
        setBackground(new Color(25, 25, 25));
        titleLabel = new JLabel("Equalizer");
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        titleLabel.setBounds(200, 0, 400, 18);
        titleLabel.setLayout(null);
        titleLabel.setForeground(new Color(46, 255, 0));//new Color(34,202,237)
        add(titleLabel);
        addListeners();
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
