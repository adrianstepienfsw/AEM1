import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class cAlgorithmSteadyState extends cAlgorithm {

    int populationCount;
    long timeToFinishMs;
    List<Integer> localSearchCount = new ArrayList<>();

    public cAlgorithmSteadyState(cSample _sample, float _percentSmaplesToFinish, int _populationCount, long _timeToFinishMs) {
        super(_sample, _percentSmaplesToFinish);
        populationCount = _populationCount;
        timeToFinishMs = _timeToFinishMs;
    }

    public void make() {
    }

    enum enumMoveType {
        cornerChange, pointOuterInnerChange;
    }

    class cPair {
        List<cMove> LM;
        cAlgorithmResult result;
        cMove move;

        public cPair(List<cMove> _lm, cAlgorithmResult _result, cMove _move) {
            LM = _lm;
            result = _result;
            move = _move;
        }
    }

    class cMove {
        List<Integer> object1 = new ArrayList<Integer>();
        List<Integer> object2 = new ArrayList<Integer>();

        int delta;
        String source;
        enumMoveType moveType;

        public cMove(List<Integer> _object1, List<Integer> _object2, enumMoveType _moveType, int _delta, String _source) {
            object1 = _object1;
            object2 = _object2;
            moveType = _moveType;
            delta = _delta;
            source = _source;

        }

        public int getDelta() {
            return this.delta;
        }
    }

    public void testAlgorithm(int countStep, String description) {
        super.testAlgorithm(countStep, description);
        int minLSC = -1;
        int maxLSC = -1;
        double averageLSC = -1;
        double sum = 0;
        for (int lSC : localSearchCount) {         //create statistics
            if (minLSC == -1 || minLSC > lSC) {
                minLSC = lSC;
            }
            if (maxLSC < lSC) {
                maxLSC = lSC;
            }
            sum += lSC;
        }
        averageLSC = sum / localSearchCount.size();
        System.out.println("Average number of LocalSearch Iterations " + averageLSC + " for :" + sample.name);
        System.out.println("Minimum number of LocalSearch Iterations " + minLSC + " for :" + sample.name);
        System.out.println("Maximum number of LocalSearch Iterations " + maxLSC + " for :" + sample.name);
    }

    public List<cMove> generateAllPosibleMoves(cAlgorithmResult actualResult, List<enumMoveType> typesOfMove) {
        List<cMove> listOfMoves = new ArrayList<>();

        List<Integer> pointsOutOfResult = makeRandomSeriesExcludingList(sample.coordList.size() - actualResult.coordsOnPath.size(), sample.coordList.size(), actualResult.coordsOnPath);

        int resultSize = actualResult.coordsOnPath.size();
        int outsideResultSize = pointsOutOfResult.size();

        for (enumMoveType moveType : typesOfMove) {
            switch (moveType) {
                case pointOuterInnerChange:
                    for (int pointInResult : actualResult.coordsOnPath) {
                        for (int pointOutOfResult : pointsOutOfResult) {
                            int delta = calculateDelta(actualResult.coordsOnPath.indexOf(pointInResult), pointOutOfResult, actualResult);
                            //if(delta<0){
                            listOfMoves.add(new cMove(Arrays.asList(actualResult.coordsOnPath.get((actualResult.coordsOnPath.indexOf(pointInResult) - 1 + resultSize) % resultSize),
                                    pointInResult, actualResult.coordsOnPath.get((actualResult.coordsOnPath.indexOf(pointInResult) + 1 + resultSize) % resultSize)),
                                    Arrays.asList(pointOutOfResult), enumMoveType.pointOuterInnerChange, delta, "pointOuterInnerChangeall"));
                            //}
                        }
                    }
                    break;
                case cornerChange:
                    for (int pointInResult1 : actualResult.coordsOnPath.subList(0, resultSize - 2)) {
                        for (int pointInResult2 : actualResult.coordsOnPath.subList(actualResult.coordsOnPath.indexOf(pointInResult1) + 2, resultSize)) {
                            int delta = calculateDeltaToCorners(actualResult.coordsOnPath.indexOf(pointInResult1), actualResult.coordsOnPath.indexOf(pointInResult2), actualResult);
                            //if(delta<0){
                            listOfMoves.add(new cMove(Arrays.asList(pointInResult1,
                                    actualResult.coordsOnPath.get((actualResult.coordsOnPath.indexOf(pointInResult1) + 1) % resultSize)),
                                    Arrays.asList(pointInResult2,
                                            actualResult.coordsOnPath.get((actualResult.coordsOnPath.indexOf(pointInResult2) + 1) % resultSize)), enumMoveType.cornerChange, delta, "cornerChangenewsall"));
                            //}
                        }
                    }
                    break;
            }
        }

        listOfMoves.sort(Comparator.comparing(cMove::getDelta));

        return listOfMoves;
    }



    public cAlgorithmResult makeBestMove(List<cMove> listOfMoves, cAlgorithmResult randomResult) {
        if (listOfMoves.size() > 0) {
            if (listOfMoves.get(0).delta < 0) {
                if (listOfMoves.get(0).moveType == enumMoveType.pointOuterInnerChange) {
                    randomResult = changePoints(randomResult.coordsOnPath.indexOf(listOfMoves.get(0).object1.get(1)), listOfMoves.get(0).object2.get(0), randomResult);
                    return randomResult;
                } else if (listOfMoves.get(0).moveType == enumMoveType.cornerChange) {
                    randomResult = changeCorners(randomResult.coordsOnPath.indexOf(listOfMoves.get(0).object1.get(0)), randomResult.coordsOnPath.indexOf(listOfMoves.get(0).object2.get(0)), randomResult);
                    return randomResult;
                }
            }
        }
        return null;
    }

    public cAlgorithmResult recombination(cAlgorithmResult result1, cAlgorithmResult result2) {
        cAlgorithmResult recombinationResult = new cAlgorithmResult();
        for (int cord1 : result1.coordsOnPath) {
            loop:
            for (int cord2 : result2.coordsOnPath) {
                if(cord1 == cord2){
                    recombinationResult.addWithoutCalculating(cord1);
                    break loop;
                }
            }
        }
        //if recombinationResult is full
        if(recombinationResult.coordsOnPath.size()==result1.coordsOnPath.size()){
            int lastCord = recombinationResult.coordsOnPath.get(recombinationResult.coordsOnPath.size()-1);
            recombinationResult.remove(recombinationResult.coordsOnPath.size()-1);
            recombinationResult.add(lastCord, recombinationResult.coordsOnPath.size());
        }else{
            int numberToAdd = result1.coordsOnPath.size() - recombinationResult.coordsOnPath.size();
            List<Integer> listToAdd = makeDisjunctiveList(result1.coordsOnPath, result2.coordsOnPath);
            List<Integer> randomListToAdd = makeRandomSeriesOnlyFromList(numberToAdd, listToAdd);
            for (Integer cordToAdd : randomListToAdd) {
                recombinationResult.add(cordToAdd, recombinationResult.coordsOnPath.size());
            }
        }
        return recombinationResult;
    }

    public cAlgorithmResult makeStep() {

        long startTime = System.currentTimeMillis();
        List<cAlgorithmResult> populationList = new ArrayList<>();

        for(int i=0; i<populationCount; i++){
            populationList.add(makeRandomResult((int) Math.floor(sample.coordList.size() * percentSmaplesToFinish)));
        }

        int localSearchCountTemp = 1;

        while(System.currentTimeMillis() - startTime < timeToFinishMs) {
            List<Integer> randomPair = makeRandomSeries(2, 20);
            cAlgorithmResult randomResult1 = populationList.get(randomPair.get(0));
            cAlgorithmResult randomResult2 = populationList.get(randomPair.get(1));
            cAlgorithmResult recombinationResult = recombination(randomResult1, randomResult2);

            cAlgorithmLocalSearchSteepestCornersChanging2 localSearch = new cAlgorithmLocalSearchSteepestCornersChanging2(sample, percentSmaplesToFinish);

            recombinationResult = localSearch.makeStep(recombinationResult);
            localSearchCountTemp++;

            populationList.sort(Comparator.comparing(cAlgorithmResult::getDistance));

            if(populationList.get(populationList.size()-1).distance > recombinationResult.distance){
                boolean is = false;
                for (cAlgorithmResult x : populationList) {
                    if(x.distance == recombinationResult.distance){
                        is = true;
                    }
                }
                if(!is)
                    populationList.set(populationList.size()-1, recombinationResult);
            }

        }

        localSearchCount.add(localSearchCountTemp);

        return populationList.get(0);
    }
}
