package App.ButtonsPanel;

import App.VolumeObserver;

import javax.swing.*;

public class VolumeImageObserver extends JLabel implements VolumeObserver {
    private ImageIcon volumeUpImage;
    private ImageIcon volumeDownImage;
    private ImageIcon muteImage;
    private ImageIcon fullVolumeImage;
    private VolumeSubject volumeSubject;
    private int volumeLevel;
    VolumeImageObserver(VolumeSubject volumeSubject)
    {
        this.volumeSubject = volumeSubject;
        volumeUpImage = new ImageIcon("images/volume_up.png");
        volumeDownImage = new ImageIcon("images/volume_down.png");
        muteImage = new ImageIcon("images/mute.png");
        fullVolumeImage = new ImageIcon("images/volume_full.png");
        volumeLevel= -1;
        setBounds(390, 15, 33, 60);
        update();
    }

    public void update()
    {
        int value = volumeSubject.getValue();
        if(value==0)
        {
            updateVolumeZero();
        }
        else if (value >=1 && value<50)
        {
            updateVolumeUnder50();
        }else if (value >= 50 && value <= 99)
        {
            updateVolumeOver50();
        }else if(value == 100)
        {
            updateVolumeIsFull();
        }
    }

    private void updateVolumeZero()
    {
        if(volumeLevel!=0)
        {
            volumeLevel=0;
            setIcon(muteImage);
        }
    }

    private void updateVolumeUnder50()
    {
        if(volumeLevel!=1)
        {
            volumeLevel=1;
            setIcon(volumeDownImage);
        }
    }

    private void updateVolumeOver50()
    {
        if(volumeLevel!=2)
        {
            volumeLevel=2;
            setIcon(volumeUpImage);
        }
    }

    private void updateVolumeIsFull()
    {
        if(volumeLevel!=3)
        {
            volumeLevel=3;
            setIcon(fullVolumeImage);
        }
    }
}
