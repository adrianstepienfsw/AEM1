import java.awt.*;

public class cTest {

    private static cSample sample1;
    private static cSample sample2;

    private static cAlgorithmGreedyCycle greedy1;
    private static cAlgorithmGreedyCycle greedy2;

    public static void main(String[] args) {

        sample1 = new cSample("kroA200.tsp");
        sample2 = new cSample("kroB200.tsp");

        //Creating Objects
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
        //cAlgorithmLocalSearchSteepestCornersChanging2 localSearchSteepestCornersSample1 = new cAlgorithmLocalSearchSteepestCornersChanging2(sample1, 0.5f);
        //cAlgorithmLocalSearchSteepestCornersChanging2 localSearchSteepestCornersSample2 = new cAlgorithmLocalSearchSteepestCornersChanging2(sample2, 0.5f);

        //cAlgorithmLocalSearchSteepestCornersChangingListMoves localSearchSteepestCornersListMovesSample1 = new cAlgorithmLocalSearchSteepestCornersChangingListMoves(sample1, 0.5f);
        //cAlgorithmLocalSearchSteepestCornersChangingListMoves localSearchSteepestCornersListMovesSample2 = new cAlgorithmLocalSearchSteepestCornersChangingListMoves(sample2, 0.5f);
        //cAlgorithmLocalSearchSteepestCornersChangingCandidatMove localSearchSteepestCornersCandidatMovesSample1 = new cAlgorithmLocalSearchSteepestCornersChangingCandidatMove(sample1, 0.5f);
        //cAlgorithmLocalSearchSteepestCornersChangingCandidatMove localSearchSteepestCornersCandidatMovesSample2 = new cAlgorithmLocalSearchSteepestCornersChangingCandidatMove(sample2, 0.5f);

        /*cAlgorithmMultipleStartLocalSearch MSLSsample1 = new cAlgorithmMultipleStartLocalSearch(sample1, 0.5f);
        cAlgorithmMultipleStartLocalSearch MSLSsample2 = new cAlgorithmMultipleStartLocalSearch(sample2, 0.5f);*/


        //Making
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
        //localSearchSteepestCornersSample2.testAlgorithm(100, "Making Local Search Steepest (changing with corners) Algorithm for :"+sample2.name);

        //localSearchSteepestCornersListMovesSample1.testAlgorithm(100, "Making Local Search Steepest (changing with corners) Algorithm using List Moves from previous step for :"+sample1.name);
        //localSearchSteepestCornersListMovesSample2.testAlgorithm(100, "Making Local Search Steepest (changing with corners) Algorithm using List Moves from previous step for :"+sample2.name);
        //localSearchSteepestCornersCandidatMovesSample1.testAlgorithm(100, "Making Local Search Steepest (changing with corners) Algorithm using Candidat Moves from previous step for :"+sample1.name);
        //localSearchSteepestCornersCandidatMovesSample2.testAlgorithm(100, "Making Local Search Steepest (changing with corners) Algorithm using Candidat Moves from previous step for :"+sample2.name);

        /*
        MSLSsample1.testAlgorithm(10, "MSLS :"+sample1.name);
        MSLSsample2.testAlgorithm(10, "MSLS :"+sample2.name);


        cAlgorithmIteratedLocalSearch1 ILS1sample1 = new cAlgorithmIteratedLocalSearch1(sample1, 0.5f, MSLSsample1.averageTime);
        cAlgorithmIteratedLocalSearch1 ILS1sample2 = new cAlgorithmIteratedLocalSearch1(sample2, 0.5f, MSLSsample2.averageTime);
        ILS1sample1.testAlgorithm(10, "ILS1 :"+sample1.name);
        ILS1sample2.testAlgorithm(10, "ILS1 :"+sample2.name);
        cAlgorithmIteratedLocalSearch2 ILS2sample1 = new cAlgorithmIteratedLocalSearch2(sample1, 0.5f, MSLSsample1.averageTime);
        cAlgorithmIteratedLocalSearch2 ILS2sample2 = new cAlgorithmIteratedLocalSearch2(sample2, 0.5f, MSLSsample2.averageTime);
        ILS2sample1.testAlgorithm(10, "ILS2 :"+sample1.name);
        ILS2sample2.testAlgorithm(10, "ILS2 :"+sample2.name);*/

        cAlgorithmSteadyState steadyState1 = new cAlgorithmSteadyState(sample1, 0.5f, 20, 126000);
        cAlgorithmSteadyState steadyState2 = new cAlgorithmSteadyState(sample2, 0.5f, 20, 126000);

        steadyState1.testAlgorithm(100, "SteadyState :"+sample1.name);
        steadyState2.testAlgorithm(100, "SteadyState :"+sample1.name);


        EventQueue.invokeLater(() -> {
            //Painting
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

            //new cMyFrame(sample1,  localSearchSteepestCornersListMovesSample1.listOfResults.get(localSearchSteepestCornersListMovesSample1.minDistanceIndex), localSearchSteepestCornersListMovesSample1.getClass().getName());
            //new cMyFrame(sample2,  localSearchSteepestCornersListMovesSample2.listOfResults.get(localSearchSteepestCornersListMovesSample2.minDistanceIndex), localSearchSteepestCornersListMovesSample2.getClass().getName());
            //new cMyFrame(sample1,  localSearchSteepestCornersCandidatMovesSample1.listOfResults.get(localSearchSteepestCornersCandidatMovesSample1.minDistanceIndex), localSearchSteepestCornersCandidatMovesSample1.getClass().getName());
            //new cMyFrame(sample2,  localSearchSteepestCornersCandidatMovesSample2.listOfResults.get(localSearchSteepestCornersCandidatMovesSample2.minDistanceIndex), localSearchSteepestCornersCandidatMovesSample2.getClass().getName());

           /* new cMyFrame(sample1,  MSLSsample1.listOfResults.get(MSLSsample1.minDistanceIndex), MSLSsample1.getClass().getName());
            new cMyFrame(sample2,  MSLSsample2.listOfResults.get(MSLSsample2.minDistanceIndex), MSLSsample2.getClass().getName());
            new cMyFrame(sample1,  ILS1sample1.listOfResults.get(ILS1sample1.minDistanceIndex), ILS1sample1.getClass().getName());
            new cMyFrame(sample2,  ILS1sample2.listOfResults.get(ILS1sample2.minDistanceIndex), ILS1sample2.getClass().getName());
            new cMyFrame(sample1,  ILS2sample1.listOfResults.get(ILS2sample1.minDistanceIndex), ILS2sample1.getClass().getName());
            new cMyFrame(sample2,  ILS2sample2.listOfResults.get(ILS2sample2.minDistanceIndex), ILS2sample2.getClass().getName());*/

            new cMyFrame(sample1,  steadyState1.listOfResults.get(steadyState1.minDistanceIndex), steadyState1.getClass().getName());
            new cMyFrame(sample2,  steadyState2.listOfResults.get(steadyState2.minDistanceIndex), steadyState2.getClass().getName());
        });
    }
}