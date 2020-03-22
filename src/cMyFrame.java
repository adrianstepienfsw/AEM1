import javax.swing.JFrame;
import javax.swing.JPanel;

public class cMyFrame extends JFrame {
    public cMyFrame(cSample sample, cAlgorithm algorithm) {
        super(sample.name+" "+algorithm.getClass().getName());

        algorithm.make();

        JPanel panel = new cMyPanel(sample, algorithm.listOfResults.get(algorithm.minDistanceIndex-1));

        add(panel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}