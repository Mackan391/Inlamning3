import javax.swing.*;

public final class FuskLäge {

    private FuskLäge() {}

    public static int init(JButton[] knappar) {
        for (int i = 0; i < 15; i++) {
            knappar[i].setText(String.valueOf(i + 1));
        }
        knappar[15].setText("");
        return 15;
    }
}
