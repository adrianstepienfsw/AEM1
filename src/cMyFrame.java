import javax.swing.JFrame;
import javax.swing.JPanel;

public class cMyFrame extends JFrame {
    public cMyFrame(cSample sample, cAlgorithm.cAlgorithmResult _result, String name) {
        super(sample.name+" "+name);



        JPanel panel = new cMyPanel(sample, _result);

        add(panel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}