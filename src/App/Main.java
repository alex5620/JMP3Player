package App;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JMediaPlayer window = new JMediaPlayer("JMP3Player");
                    window.makeVisible();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
