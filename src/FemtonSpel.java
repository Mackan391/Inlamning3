import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FemtonSpel extends JFrame implements ActionListener {

    final int STORLEK = 4;
    final JButton[] knappar = new JButton[16];
    int tomRuta = 15;
    final JButton nyttSpelKnapp = new JButton("Nytt spel");
    final Timer timer = new Timer();

    public FemtonSpel() {
        super("Välkommen till Femtonspelet!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nyttSpelKnapp.addActionListener(this);
        topp.add(nyttSpelKnapp);
        topp.add(timer.getLabel()); // lägg till timer
        add(topp, BorderLayout.NORTH);

        JPanel mitten = new JPanel(new GridLayout(STORLEK, STORLEK));
        for (int i = 0; i < 16; i++) {
            JButton b = new JButton();
            b.addActionListener(this);
            knappar[i] = b;
            mitten.add(b);
        }
        add(mitten, BorderLayout.CENTER);
        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        nyttSpel();
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nyttSpelKnapp){
            nyttSpel();
            return;
        }
        for (int i = 0; i < 16; i++) {
            if (e.getSource() == knappar[i]) {
                klick(i);
                break;
            }
        }
    }

    private void klick(int klickIndex){
        if (bredvid(klickIndex, tomRuta)) {
            byt(klickIndex, tomRuta);
            tomRuta = klickIndex;
            if (löst()){
                JOptionPane.showMessageDialog(this, "Du vann! Grattis!");
                timer.stop(); // stoppa timer
            }
        }
    }

    private boolean bredvid(int a, int b) { // räknar ut plasten med division och modulos
        int radA = a / STORLEK;
        int radB = b / STORLEK;
        int kolA = a % STORLEK;
        int kolB = b % STORLEK;

        boolean sammaRad = radA == radB && Math.abs(kolA - kolB) == 1;
        boolean sammaKolumn = kolA == kolB && Math.abs(radA - radB) == 1;

        return sammaRad || sammaKolumn;
    }
    private void byt (int a, int b){
        String textA = knappar[a].getText();
        String textB = knappar[b].getText();
        knappar[a].setText(textB);
        knappar[b].setText(textA);
        uppdateraUtseende();

}
    private void nyttSpel() { // skapa brädet med 16 slumpade siffror
        List<Integer> lista = new ArrayList<>();
        for (int i = 0; i < 16; i++) lista.add(i);
        Collections.shuffle(lista);

        for (int i = 0; i < 16; i++) {
           int v = lista.get(i);
           if (v == 0){
               knappar[i].setText("");
               tomRuta = i;
           } else {
               knappar[i].setText(String.valueOf(v));
           }
        }
        uppdateraUtseende();
        pack();
        timer.start();
}
private void uppdateraUtseende(){
    for (int i = 0; i < 16; i++) {
        boolean tom = knappar[i].getText().isEmpty();
        knappar[i].setEnabled(!tom);
        knappar[i].setBackground(tom ? Color.LIGHT_GRAY : null);
}

}
private boolean löst (){ // kontroll om rutorna ligger i ordning
    for (int i = 0; i < 15; i++) {
        String t = knappar[i].getText();
        if (t.equals(String.valueOf(i + 1))) {
        } else {
            return false;
        }
    }
    return knappar[15].getText().isEmpty();

    }
    void main() {
    }
}