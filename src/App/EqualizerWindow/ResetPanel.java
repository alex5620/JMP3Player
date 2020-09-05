package App.EqualizerWindow;

import App.Colors;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ResetPanel extends JPanel {
    private EqualizerWindow equalizerWindow;
    private JLabel resetButton;
    ResetPanel(EqualizerWindow window)
    {
        equalizerWindow = window;
        setBounds(0, 415, 590, 60);
        setLayout(null);
        setBackground(Color.black);
        addResetButton();
    }

    private void addResetButton()
    {
        resetButton = new JLabel("RESET", SwingConstants.CENTER);
        resetButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        resetButton.setBounds(250, 10, 80, 28);
        resetButton.setForeground(Colors.color46_255_0);
        resetButton.setBorder(new LineBorder(Colors.color46_255_0, 1));
        addMouseListenerToResetButton();
        add(resetButton);
    }

    private void addMouseListenerToResetButton()
    {
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(equalizerWindow.getKnobsPanel().getMediaPlayer().getPlayer() != null)
                {
                    equalizerWindow.getKnobsPanel().resetKnobs();
                    equalizerWindow.getFrequenciesPanel().resetFrequencies();
                }
            }
        });
    }
}
