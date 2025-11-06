import javax.swing.*;

public class Timer {
    private final JLabel label = new JLabel("Tid: 0s");
    private javax.swing.Timer timer;
    private int sekunder = 0;

    public JLabel getLabel() {
        return label;
    }

    public void start(){
        stop();
        sekunder = 0;
        label.setText("Tid: 0s");
        timer = new javax.swing.Timer(1000, e -> {
            sekunder++;
            label.setText("Tid: " + sekunder + "s");
        });
        timer.start();
    }
    public void stop(){
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }



}
