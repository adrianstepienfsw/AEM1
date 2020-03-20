import java.awt.*;

public class cTest {

    private static cSample sample1;
    private static cSample sample2;

    private static cAlgorithmGreedyCycle greedy1;
    private static cAlgorithmGreedyCycle greedy2;

    public static void main(String[] args) {

        sample1 = new cSample("kroA100.tsp");
        sample2 = new cSample("kroB100.tsp");

        greedy1 = new cAlgorithmGreedyCycle(sample1);
        greedy2 = new cAlgorithmGreedyCycle(sample2);

        greedy1.makeGreedyCycle();
        greedy2.makeGreedyCycle();


        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new cMyFrame(sample1);
                new cMyFrame(sample2);
            }
        });
    }
}