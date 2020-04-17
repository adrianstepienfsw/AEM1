import java.lang.reflect.Array;
import java.util.*;


public class cAlgorithmLocalSearchSteepestCornersChangingListMoves extends cAlgorithm {

    public cAlgorithmLocalSearchSteepestCornersChangingListMoves(cSample _sample, float _percentSmaplesToFinish){
        super(_sample, _percentSmaplesToFinish);
    }

    enum enumMoveType {
        cornerChange, pointOuterInnerChange;
    }

    class cPair{
        List<cMove> LM;
        cAlgorithmResult result;
        cMove move;
        public cPair(List<cMove> _lm, cAlgorithmResult _result, cMove _move){
            LM = _lm;
            result = _result;
            move = _move;
        }

    }


    class cMove{
        List<Integer> object1 = new ArrayList<Integer>();
        List<Integer> object2 = new ArrayList<Integer>();

        int delta;
        String source;
        enumMoveType moveType;

        public cMove(List<Integer> _object1, List<Integer> _object2, enumMoveType _moveType, int _delta, String _source){
            object1 = _object1;
            object2 = _object2;
            moveType = _moveType;
            delta = _delta;
            source = _source;

        }

        public int getDelta(){
            return this.delta;
        }
    }

    public void make(){

    }

    public List<cMove> generateAllPosibleMoves(cAlgorithmResult actualResult, List<enumMoveType> typesOfMove){
        List<cMove> listOfMoves = new ArrayList<>();

        List<Integer> pointsOutOfResult = makeRandomSeriesExcludingList(sample.coordList.size()-actualResult.coordsOnPath.size(), sample.coordList.size(), actualResult.coordsOnPath);

        int resultSize = actualResult.coordsOnPath.size();
        int outsideResultSize = pointsOutOfResult.size();

        for (enumMoveType moveType : typesOfMove){

            switch(moveType){
                case pointOuterInnerChange:
                    for(int pointInResult: actualResult.coordsOnPath){
                        for(int pointOutOfResult: pointsOutOfResult){
                            int delta = calculateDelta(actualResult.coordsOnPath.indexOf(pointInResult), pointOutOfResult, actualResult);
                            if(delta<0){
                                listOfMoves.add(new cMove(Arrays.asList(actualResult.coordsOnPath.get((actualResult.coordsOnPath.indexOf(pointInResult)-1+resultSize)%resultSize),
                                        pointInResult, actualResult.coordsOnPath.get((actualResult.coordsOnPath.indexOf(pointInResult)+1+resultSize)%resultSize)),
                                        Arrays.asList(pointOutOfResult),enumMoveType.pointOuterInnerChange, delta, "pointOuterInnerChangeall"));
                            }
                        }
                    }
                    break;
                case cornerChange:
                    for(int pointInResult1: actualResult.coordsOnPath.subList(0, resultSize - 3)){
                        for(int pointInResult2: actualResult.coordsOnPath.subList(actualResult.coordsOnPath.indexOf(pointInResult1)+2, resultSize-1)){
                            int delta = calculateDeltaToCorners(actualResult.coordsOnPath.indexOf(pointInResult1), actualResult.coordsOnPath.indexOf(pointInResult2), actualResult);
                            if(delta<0){
                                listOfMoves.add(new cMove(Arrays.asList(pointInResult1,
                                        actualResult.coordsOnPath.get((actualResult.coordsOnPath.indexOf(pointInResult1)+1)%resultSize)),
                                        Arrays.asList(pointInResult2,
                                                actualResult.coordsOnPath.get((actualResult.coordsOnPath.indexOf(pointInResult2)+1)%resultSize)),enumMoveType.cornerChange, delta, "cornerChangenewsall"));
                            }
                        }
                    }
                    break;
            }

        }

        listOfMoves.sort(Comparator.comparing(cMove::getDelta));

        return listOfMoves;
    }

    public cPair checkMovesAndMove(cAlgorithmResult result, List<cMove> listOfMoves){
        List<Integer> indexesToRemove = new ArrayList<>();
        int resultSize = result.coordsOnPath.size();
        cMove executedMove = null;

        breakloops:
        for(cMove move: listOfMoves){
            switch (move.moveType){
                case pointOuterInnerChange:
                    int indexFirstOfThree = result.coordsOnPath.indexOf(move.object1.get(0));
                    int indexSecondOfThree = result.coordsOnPath.indexOf(move.object1.get(1));
                    int indexThirdOfThree = result.coordsOnPath.indexOf(move.object1.get(2));
                    if((indexSecondOfThree!=-1) && (indexFirstOfThree!=-1) && (indexThirdOfThree!=-1) && (result.coordsOnPath.indexOf(move.object2.get(0)) == -1)){
                        if(((Math.abs(indexSecondOfThree - indexFirstOfThree) ==1) || (Math.abs(indexSecondOfThree - indexFirstOfThree) == resultSize-1))
                                && ((Math.abs(indexSecondOfThree - indexThirdOfThree) ==1) || (Math.abs(indexSecondOfThree - indexThirdOfThree) == resultSize-1))){
                            double dist1 = result.distance;
                            List<Integer> res = new ArrayList<>();
                            for(int p : result.coordsOnPath) {
                                res.add(p);
                            }
                            result = changePoints(result.coordsOnPath.indexOf(move.object1.get(1)), move.object2.get(0), result);
                            double dist2 = result.distance;
                            if((dist2 - dist1)!= move.delta){
                                System.out.println("Błąd pointOuterInnerChange");
                            }
                            executedMove = move;
                            indexesToRemove.add(listOfMoves.indexOf(move));
                            break breakloops;
                        }else
                            indexesToRemove.add(listOfMoves.indexOf(move));
                    }else
                        indexesToRemove.add(listOfMoves.indexOf(move));
                    break;
                case cornerChange:
                    int indexFirstOfFirstTwo = result.coordsOnPath.indexOf(move.object1.get(0));
                    int indexFirstOfSecondTwo = result.coordsOnPath.indexOf(move.object2.get(0));
                    int indexSecondOfFirstTwo = result.coordsOnPath.indexOf(move.object1.get(1));
                    int indexSecondOfSecondTwo = result.coordsOnPath.indexOf(move.object2.get(1));
                    if(indexFirstOfFirstTwo>indexSecondOfFirstTwo){
                        int temp = indexFirstOfFirstTwo;
                        indexFirstOfFirstTwo = indexSecondOfFirstTwo;
                        indexSecondOfFirstTwo = temp;
                    }
                    if(indexFirstOfSecondTwo>indexSecondOfSecondTwo){
                        int temp = indexFirstOfSecondTwo;
                        indexFirstOfSecondTwo = indexSecondOfSecondTwo;
                        indexSecondOfSecondTwo = temp;
                    }
                    if((indexFirstOfFirstTwo != -1) && (indexFirstOfSecondTwo != -1)){
                        if((result.coordsOnPath.get((indexFirstOfFirstTwo+1)%resultSize) == move.object1.get(1) )
                                && (result.coordsOnPath.get((indexFirstOfSecondTwo+1)%resultSize) == move.object2.get(1))){
                            double dist1 = result.distance;
                            List<Integer> res = new ArrayList<>();
                            for(int p : result.coordsOnPath) {
                                res.add(p);
                            }
                            result = changeCorners(indexFirstOfFirstTwo, indexFirstOfSecondTwo, result);
                            double dist2 = result.distance;
                            if((dist2 - dist1)!= move.delta){
                                System.out.println("Błąd cornerChange");
                            }
                            executedMove = move;
                            indexesToRemove.add(listOfMoves.indexOf(move));
                            break breakloops;
                        }else
                            indexesToRemove.add(listOfMoves.indexOf(move));
                    }else
                        indexesToRemove.add(listOfMoves.indexOf(move));
                    break;
            }
        }
        Collections.reverse(indexesToRemove);
        for(int indexToRemove: indexesToRemove){
            listOfMoves.remove(indexToRemove);
        }
        if(executedMove != null)
            listOfMoves = addNewMoves(listOfMoves, result, executedMove);
        return new cPair(listOfMoves, result, executedMove);
    }

    public List<cMove> addNewMoves(List<cMove> listOfMoves, cAlgorithmResult result, cMove lastMove){
        List<Integer> pointsOutOfResult = makeRandomSeriesExcludingList(sample.coordList.size()-result.coordsOnPath.size(), sample.coordList.size(), result.coordsOnPath);
        int resultSize = result.coordsOnPath.size();

        switch (lastMove.moveType) {
            case pointOuterInnerChange:
                //adding pointOuterInnerChange moves
                for (int newPoint : Arrays.asList(result.coordsOnPath.get((result.coordsOnPath.indexOf(lastMove.object2.get(0)) - 1 + resultSize) % resultSize),
                        lastMove.object2.get(0), result.coordsOnPath.get((result.coordsOnPath.indexOf(lastMove.object2.get(0)) + 1) % resultSize))) {
                    for (int point : pointsOutOfResult) {
                        int delta = calculateDelta(result.coordsOnPath.indexOf(newPoint), point, result);
                        if (delta < 0) {
                            listOfMoves.add(new cMove(Arrays.asList(result.coordsOnPath.get((result.coordsOnPath.indexOf(newPoint) - 1 + resultSize) % resultSize),
                                    newPoint, result.coordsOnPath.get((result.coordsOnPath.indexOf(newPoint) + 1 + resultSize) % resultSize)),
                                    Arrays.asList(point), enumMoveType.pointOuterInnerChange, delta, "1"));
                        }
                    }
                }
                //adding cornerChange moves
                for(int newPoint : Arrays.asList(lastMove.object2.get(0), result.coordsOnPath.get((result.coordsOnPath.indexOf(lastMove.object2.get(0))-1+resultSize)%resultSize))) {
                    for (int point : result.coordsOnPath) {
                        if ((Math.abs(result.coordsOnPath.indexOf(point) - result.coordsOnPath.indexOf(newPoint)) > 1)
                                && (Math.abs(result.coordsOnPath.indexOf(point) - result.coordsOnPath.indexOf(newPoint)) != (resultSize - 1))) {
                            int delta = calculateDeltaToCorners(result.coordsOnPath.indexOf(newPoint), result.coordsOnPath.indexOf(point), result);
                            if (delta < 0) {
                                listOfMoves.add(new cMove(Arrays.asList(newPoint, result.coordsOnPath.get((result.coordsOnPath.indexOf(newPoint) + 1 + resultSize) % resultSize)),
                                        Arrays.asList(point, result.coordsOnPath.get((result.coordsOnPath.indexOf(point) + 1 + resultSize) % resultSize)), enumMoveType.cornerChange, delta, "2"));
                            }
                        }
                    }
                }
                break;

            case cornerChange:
                //adding pointOuterInnerChange moves
                for(int newPoint: Arrays.asList(lastMove.object1.get(0), lastMove.object1.get(1), lastMove.object2.get(0), lastMove.object2.get(1))) {
                    for (int point : pointsOutOfResult) {
                        int delta = calculateDelta(result.coordsOnPath.indexOf(newPoint), point, result);
                        if (delta < 0) {
                            listOfMoves.add(new cMove(Arrays.asList(result.coordsOnPath.get((result.coordsOnPath.indexOf(newPoint) - 1 + resultSize) % resultSize),
                                    newPoint, result.coordsOnPath.get((result.coordsOnPath.indexOf(newPoint) + 1 + resultSize) % resultSize)),
                                    Arrays.asList(point), enumMoveType.pointOuterInnerChange, delta, "3"));
                        }
                    }
                }
                //adding cornerChange moves
                for(int newPoint : lastMove.object2) {
                    for (int point : result.coordsOnPath) {
                        if ((Math.abs(result.coordsOnPath.indexOf(point) - result.coordsOnPath.indexOf(newPoint)) != (resultSize - 1))
                                && (Math.abs(result.coordsOnPath.indexOf(point) - result.coordsOnPath.indexOf(newPoint)) > 1)) {
                            int delta = calculateDeltaToCorners(result.coordsOnPath.indexOf(newPoint), result.coordsOnPath.indexOf(point), result);
                            if (delta < 0) {
                                listOfMoves.add(new cMove(Arrays.asList(newPoint, result.coordsOnPath.get((result.coordsOnPath.indexOf(newPoint) + 1 + resultSize) % resultSize)),
                                        Arrays.asList(point, result.coordsOnPath.get((result.coordsOnPath.indexOf(point) + 1 + resultSize) % resultSize)), enumMoveType.cornerChange, delta, "4"));
                            }
                        }
                    }
                }
                break;
        }
        listOfMoves.sort(Comparator.comparing(cMove::getDelta));
        return listOfMoves;
    }


    public cAlgorithmResult makeStep(){

        cAlgorithmResult randomResult = makeRandomResult((int)Math.floor(sample.coordList.size()*percentSmaplesToFinish));


        List<cMove> listOfMoves = new ArrayList<>();

        try {

            listOfMoves = generateAllPosibleMoves(randomResult, Arrays.asList(enumMoveType.pointOuterInnerChange, enumMoveType.cornerChange));

            List<cMove> prevMove = new ArrayList<>();

            while (true) {

                /*List<cMove> list = generateAllPosibleMoves(randomResult, Arrays.asList(enumMoveType.cornerChange, enumMoveType.pointOuterInnerChange));

                for (cMove move : list){
                    boolean is = false;
                    for (cMove move1 : listOfMoves){
                        if(move.moveType == move1.moveType){
                            if(move.moveType==enumMoveType.cornerChange){
                                if(move1.object1.contains(move.object1.get(0))
                                        && move1.object1.contains(move.object1.get(1))
                                        && move1.delta==move.delta){
                                    is = true;
                                    break;
                                }
                                if(move1.object1.contains(move.object2.get(0))
                                        && move1.object1.contains(move.object2.get(1))
                                        && move1.delta==move.delta){
                                    is = true;
                                    break;
                                }
                            }else{
                                is = true;
                                break;
                            }
                        }
                    }
                    if(!is){
                        //System.out.println("Błąd!");
                        //System.out.println(list.indexOf(move));
                    }
                }*/
                cPair ret = checkMovesAndMove(randomResult, listOfMoves);
                randomResult = ret.result;
                listOfMoves = ret.LM;
                prevMove.add(ret.move);

                if(listOfMoves.size()==0)
                    break;
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return randomResult;
    }

}
