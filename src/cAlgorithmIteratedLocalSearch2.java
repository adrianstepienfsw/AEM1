import java.util.*;

public class cAlgorithmIteratedLocalSearch2 extends cAlgorithm {
    long timeToFinishMs = 0;
    List<Integer> localSearchCount = new ArrayList<>();

    public cAlgorithmIteratedLocalSearch2(cSample _sample, float _percentSmaplesToFinish, long _timeToFinishMs) {
        super(_sample, _percentSmaplesToFinish);
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

    public cAlgorithmResult perturbate(cAlgorithmResult result) {
        cAlgorithmResult returnResult = result.clone();

        Random randomGenerator = new Random();
        int random = (int) Math.floor(randomGenerator.nextFloat() * returnResult.coordsOnPath.size());

        List<Integer> pointsToDelete = makeRandomSeriesOnlyFromList((int) (Math.round(returnResult.coordsOnPath.size() * 0.2)), returnResult.coordsOnPath);

        for (int i = 0; i < pointsToDelete.size() - 1; i++) {
            returnResult.coordsOnPath.remove(pointsToDelete.get(i));
        }
        returnResult.remove(returnResult.coordsOnPath.indexOf(pointsToDelete.get(pointsToDelete.size() - 1)));

        cAlgorithmGreedyCycle greedyCycle = new cAlgorithmGreedyCycle(sample, 0.5f);
        returnResult = greedyCycle.makeStepUsing(returnResult);

        return returnResult;
    }


    public cAlgorithmResult makeStep() {
        long startTime = System.currentTimeMillis();

        cAlgorithmResult result = makeRandomResult((int) Math.floor(sample.coordList.size() * percentSmaplesToFinish));

        //LocalSearch
        while (true) {
            List<cMove> listOfMoves = generateAllPosibleMoves(result, Arrays.asList(enumMoveType.cornerChange, enumMoveType.pointOuterInnerChange));

            cAlgorithmResult res = makeBestMove(listOfMoves, result);
            if (res != null)
                result = res;
            else
                break;
        }

        int localSearchCountTemp = 1;

        while (System.currentTimeMillis() - startTime < timeToFinishMs) {
            cAlgorithmResult perturbatedResult = perturbate(result);
            localSearchCountTemp++;

            //LocalSearch on perturbatedResult
            loop:
            while (true) {
                List<cMove> listOfMoves = generateAllPosibleMoves(perturbatedResult, Arrays.asList(enumMoveType.cornerChange, enumMoveType.pointOuterInnerChange));

                cAlgorithmResult res = makeBestMove(listOfMoves, perturbatedResult);
                if (res != null)
                    perturbatedResult = res;
                else
                    break loop;
            }

            if (perturbatedResult.distance < result.distance)
                result = perturbatedResult;
        }
        localSearchCount.add(localSearchCountTemp);

        return result;
    }
}
