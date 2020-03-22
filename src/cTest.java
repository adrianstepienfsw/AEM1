import java.awt.*;

public class cTest {

    private static cSample sample1;
    private static cSample sample2;

    private static cAlgorithmGreedyCycle greedy1;
    private static cAlgorithmGreedyCycle greedy2;

    public static void main(String[] args) {

        sample1 = new cSample("kroA100.tsp");
        sample2 = new cSample("kroB100.tsp");

        cAlgorithmGreedyCycle greedyCycleSmaple1 = new cAlgorithmGreedyCycle(sample1, 0.5f);
        cAlgorithmGreedyCycle greedyCycleSmaple2 = new cAlgorithmGreedyCycle(sample2, 0.5f);
        cAlgorithmRegretHeuristics regretHeuristicsSample1 = new cAlgorithmRegretHeuristics(sample1, 0.5f);
        cAlgorithmRegretHeuristics regretHeuristicsSample2 = new cAlgorithmRegretHeuristics(sample2, 0.5f);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new cMyFrame(sample1, greedyCycleSmaple1);
                new cMyFrame(sample2, greedyCycleSmaple2);
                new cMyFrame(sample1, regretHeuristicsSample1);
                new cMyFrame(sample2, regretHeuristicsSample2);
            }
        });
    }
}