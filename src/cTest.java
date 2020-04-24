import java.awt.*;

public class cTest {

    private static cSample sample1;
    private static cSample sample2;

    private static cAlgorithmGreedyCycle greedy1;
    private static cAlgorithmGreedyCycle greedy2;

    public static void main(String[] args) {

        sample1 = new cSample("kroA200.tsp");
        sample2 = new cSample("kroB200.tsp");

        //cAlgorithmGreedyCycle greedyCycleSmaple1 = new cAlgorithmGreedyCycle(sample1, 0.5f);
        //cAlgorithmGreedyCycle greedyCycleSmaple2 = new cAlgorithmGreedyCycle(sample2, 0.5f);
        //cAlgorithmRegretHeuristics regretHeuristicsSample1 = new cAlgorithmRegretHeuristics(sample1, 0.5f);
        //cAlgorithmRegretHeuristics regretHeuristicsSample2 = new cAlgorithmRegretHeuristics(sample2, 0.5f);

        //cAlgorithmLocalSearchGreedyPointsChanging localSearchGreedyPointsSample1 = new cAlgorithmLocalSearchGreedyPointsChanging(sample1, 0.5f);
        //cAlgorithmLocalSearchGreedyPointsChanging localSearchGreedyPointsSample2 = new cAlgorithmLocalSearchGreedyPointsChanging(sample2, 0.5f);
        //cAlgorithmLocalSearchGreedyCornersChanging localSearchGreedyCornersSample1 = new cAlgorithmLocalSearchGreedyCornersChanging(sample1, 0.5f);
        //cAlgorithmLocalSearchGreedyCornersChanging localSearchGreedyCornersSample2 = new cAlgorithmLocalSearchGreedyCornersChanging(sample2, 0.5f);
        //cAlgorithmLocalSearchSteepestPointsChanging localSearchSteepestPointsSample1 = new cAlgorithmLocalSearchSteepestPointsChanging(sample1, 0.5f);
        //cAlgorithmLocalSearchSteepestPointsChanging localSearchSteepestPointsSample2 = new cAlgorithmLocalSearchSteepestPointsChanging(sample2, 0.5f);
        //cAlgorithmLocalSearchSteepestCornersChanging localSearchSteepestCornersSample1 = new cAlgorithmLocalSearchSteepestCornersChanging(sample1, 0.5f);
        //cAlgorithmLocalSearchSteepestCornersChanging localSearchSteepestCornersSample2 = new cAlgorithmLocalSearchSteepestCornersChanging(sample2, 0.5f);

        cAlgorithmLocalSearchSteepestCornersChangingListMoves localSearchSteepestCornersListMovesSample1 = new cAlgorithmLocalSearchSteepestCornersChangingListMoves(sample1, 0.5f);
        cAlgorithmLocalSearchSteepestCornersChangingListMoves localSearchSteepestCornersListMovesSample2 = new cAlgorithmLocalSearchSteepestCornersChangingListMoves(sample2, 0.5f);

        //greedyCycleSmaple1.make();
        //greedyCycleSmaple2.make();
        //regretHeuristicsSample1.make();
        //regretHeuristicsSample2.make();

        //localSearchGreedyPointsSample1.make();
        //localSearchGreedyPointsSample2.make();
        //localSearchGreedyCornersSample1.make();
        //localSearchGreedyCornersSample2.make();
        //localSearchSteepestPointsSample1.make();
        //localSearchSteepestPointsSample2.make();
        //localSearchSteepestCornersSample1.testAlgorithm(100, "Making Local Search Steepest (changing with corners) Algorithm for :"+sample1.name);
        //localSearchSteepestCornersSample2.testAlgorithm(100, "Making Local Search Steepest (changing with corners) Algorithm for :"+sample1.name);

        localSearchSteepestCornersListMovesSample1.testAlgorithm(100, "Making Local Search Steepest (changing with corners) Algorithm using List Moves from previous step for :"+sample1.name);
        localSearchSteepestCornersListMovesSample2.testAlgorithm(100, "Making Local Search Steepest (changing with corners) Algorithm using List Moves from previous step for :"+sample2.name);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new cMyFrame(sample1, greedyCycleSmaple1.listOfResults.get(greedyCycleSmaple1.minDistanceIndex-1), greedyCycleSmaple1.getClass().getName());
                //new cMyFrame(sample2,  greedyCycleSmaple2.listOfResults.get(greedyCycleSmaple2.minDistanceIndex-1), greedyCycleSmaple2.getClass().getName());
                //new cMyFrame(sample1,  regretHeuristicsSample1.listOfResults.get(regretHeuristicsSample1.minDistanceIndex-1), regretHeuristicsSample1.getClass().getName());
                //new cMyFrame(sample2,  regretHeuristicsSample2.listOfResults.get(regretHeuristicsSample2.minDistanceIndex-1), regretHeuristicsSample2.getClass().getName());
                //new cMyFrame(sample1,  localSearchGreedyPointsSample1.listOfResults.get(localSearchGreedyPointsSample1.minDistanceIndex-1), localSearchGreedyPointsSample1.getClass().getName());
                //new cMyFrame(sample2,  localSearchGreedyPointsSample2.listOfResults.get(localSearchGreedyPointsSample2.minDistanceIndex-1), localSearchGreedyPointsSample2.getClass().getName());
                //new cMyFrame(sample1,  localSearchGreedyCornersSample1.listOfResults.get(localSearchGreedyCornersSample1.minDistanceIndex-1), localSearchGreedyCornersSample1.getClass().getName());
                //new cMyFrame(sample2,  localSearchGreedyCornersSample2.listOfResults.get(localSearchGreedyCornersSample2.minDistanceIndex-1), localSearchGreedyCornersSample2.getClass().getName());
                //new cMyFrame(sample1,  localSearchSteepestPointsSample1.listOfResults.get(localSearchSteepestPointsSample1.minDistanceIndex-1), localSearchSteepestPointsSample1.getClass().getName());
                //new cMyFrame(sample2,  localSearchSteepestPointsSample2.listOfResults.get(localSearchSteepestPointsSample2.minDistanceIndex-1), localSearchSteepestPointsSample2.getClass().getName());new cMyFrame(sample1,  localSearchSteepestCornersSample1.listOfResults.get(localSearchSteepestCornersSample1.minDistanceIndex-1), localSearchSteepestCornersSample1.getClass().getName());
                //new cMyFrame(sample1,  localSearchSteepestCornersSample1.listOfResults.get(localSearchSteepestCornersSample1.minDistanceIndex-1), localSearchSteepestCornersSample1.getClass().getName());
                //new cMyFrame(sample2,  localSearchSteepestCornersSample2.listOfResults.get(localSearchSteepestCornersSample2.minDistanceIndex-1), localSearchSteepestCornersSample2.getClass().getName());

                new cMyFrame(sample1,  localSearchSteepestCornersListMovesSample1.listOfResults.get(localSearchSteepestCornersListMovesSample1.minDistanceIndex), localSearchSteepestCornersListMovesSample1.getClass().getName());
                new cMyFrame(sample2,  localSearchSteepestCornersListMovesSample2.listOfResults.get(localSearchSteepestCornersListMovesSample2.minDistanceIndex), localSearchSteepestCornersListMovesSample2.getClass().getName());
            }
        });
    }
}