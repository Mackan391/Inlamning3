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
    final JCheckBox fuskLäge = new JCheckBox("Fuskläge - sorterat");

    final Timer timer = new Timer();

    public FemtonSpel() {
        super("Välkommen till Femtonspelet!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topp = new JPanel(new BorderLayout(10,0));

        JPanel vänster = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nyttSpelKnapp.addActionListener(this);
        vänster.add(nyttSpelKnapp);
        vänster.add(fuskLäge);

        JPanel höger = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        höger.add(timer.getLabel());

        topp.add(vänster, BorderLayout.WEST);
        topp.add(höger, BorderLayout.EAST);
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

    private void nyttSpel() { // skapa brädet med slump eller fusk
        if (fuskLäge.isSelected()) {
            tomRuta = FuskLäge.init(knappar);
        } else {
            List<Integer> lista = new ArrayList<>(16);
            for (int i = 0; i < 16; i++) lista.add(i);
            Collections.shuffle(lista);

            for (int i = 0; i < 16; i++) {
                int v = lista.get(i);
                if (v == 0) {
                    knappar[i].setText("");
                    tomRuta = i;
                } else {
                    knappar[i].setText(String.valueOf(v));
                }

            }
        }
        uppdateraUtseende();
        timer.start();
        pack();

    }

    private void klick(int klickIndex){
        if (bredvid(klickIndex, tomRuta)) {
            byt(klickIndex, tomRuta);
            tomRuta = klickIndex;
            if (löst()){
                timer.stop(); // stoppa timer
                JOptionPane.showMessageDialog(this, "Du vann! Grattis!");

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
    private void byt (int a, int b){ // byter plats på två knappar
        String textA = knappar[a].getText();
        String textB = knappar[b].getText();
        knappar[a].setText(textB);
        knappar[b].setText(textA);
        uppdateraUtseende();

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

    }
    void main() {
    new FemtonSpel();

    }
