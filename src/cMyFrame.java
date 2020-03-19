import javax.swing.JFrame;
import javax.swing.JPanel;

public class cMyFrame extends JFrame {
    public cMyFrame(cSample sample) {
        super(sample.name);
        JPanel panel = new cMyPanel(sample);

        add(panel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}