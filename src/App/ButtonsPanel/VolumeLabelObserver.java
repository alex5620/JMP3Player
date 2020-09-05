package App.ButtonsPanel;

import App.Colors;
import App.VolumeObserver;

import javax.swing.*;

public class VolumeLabelObserver extends JLabel implements VolumeObserver {
    private VolumeSubject observable;
    VolumeLabelObserver(VolumeSubject observable, String text)
    {
        this.observable = observable;
        setBounds(523, 13, 30, 60);
        setForeground(Colors.color34_202_237);
        setText(text);
    }

    @Override
    public void update() {
        setText(Integer.toString(observable.getValue()));
    }
}
