package App.SettingsWindow;

import javafx.scene.media.EqualizerBand;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class CustomizedSliderUI extends BasicSliderUI {
    public CustomizedSliderUI(JSlider b) {
        super(b);
    }

    @Override
    protected void calculateThumbSize() {
        super.calculateThumbSize();
        thumbRect.setSize(thumbRect.width, thumbRect.height);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(20,20);
    }

    @Override
    protected Color getFocusColor(){
        return Color.black;
    }

    @Override
    public void paintTrack(Graphics g) {
        g.setColor(new Color(65, 65, 65));
        g.fillRect(trackRect.x+(int)trackRect.getWidth()/2-(int)trackRect.getWidth()/12, trackRect.y, (int)trackRect.getWidth()/2, (int)trackRect.getHeight());
        g.setColor(new Color(46, 255, 0));
        g.fillRect(trackRect.x+(int)trackRect.getWidth()/2-(int)trackRect.getWidth()/12, thumbRect.y+(int)thumbRect.getHeight()/2, (int)trackRect.getWidth()/2, (int)trackRect.getHeight()-thumbRect.y);
        int value = (slider.getValue()+24)/6;
        int index=0;
        g.setColor(new Color(46, 255, 0));
        for(int i=(int)trackRect.getHeight();i >= 0;i-=trackRect.getHeight()/6)
        {
            g.drawLine(40, i+10,50,i+10);
            if(index==value)
            {
                g.setColor(new Color(65, 65, 65));
            }
            index++;
        }
    }

    @Override
    public void paintThumb(Graphics g) {
    }
}