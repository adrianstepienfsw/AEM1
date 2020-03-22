import javax.swing.JFrame;
import javax.swing.JPanel;

public class cMyFrame extends JFrame {
    public cMyFrame(cSample sample) {
        super(sample.name);

        cAlgorithmRegretHeuristics greedyAlgorithm = new cAlgorithmRegretHeuristics(sample);
        greedyAlgorithm.make();

        JPanel panel = new cMyPanel(sample, greedyAlgorithm.listOfResults.get(greedyAlgorithm.minDistanceIndex));

        add(panel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}