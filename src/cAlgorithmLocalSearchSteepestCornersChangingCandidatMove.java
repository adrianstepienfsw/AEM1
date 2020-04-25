import java.util.*;


public class cAlgorithmLocalSearchSteepestCornersChangingCandidatMove extends cAlgorithm {

    public cAlgorithmLocalSearchSteepestCornersChangingCandidatMove(cSample _sample, float _percentSmaplesToFinish){
        super(_sample, _percentSmaplesToFinish);
    }

    enum enumMoveType {
        cornerChange, pointOuterInnerChange;
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

    public List<cMove> generateAllPosibleCandidatMoves(cAlgorithmResult actualResult, List<enumMoveType> typesOfMove){
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
                                        Arrays.asList(pointOutOfResult), enumMoveType.pointOuterInnerChange, delta, "pointOuterInnerChangeall"));
                            }
                        }
                    }
                    break;
                case cornerChange:
                    for(int pointInResult1: actualResult.coordsOnPath){
                        int i = 0;
                        for(int pointInResult2: sample.nearestPointsList.get(pointInResult1)){
                            if(i>=5)
                                break;
                            if(!(actualResult.coordsOnPath.contains(pointInResult2)))
                                continue;
                            int delta = calculateDeltaToCorners(actualResult.coordsOnPath.indexOf(pointInResult1), actualResult.coordsOnPath.indexOf(pointInResult2), actualResult);
                            if(delta<0){
                                listOfMoves.add(new cMove(Arrays.asList(pointInResult1,
                                        actualResult.coordsOnPath.get((actualResult.coordsOnPath.indexOf(pointInResult1)+1)%resultSize)),
                                        Arrays.asList(pointInResult2,
                                                actualResult.coordsOnPath.get((actualResult.coordsOnPath.indexOf(pointInResult2)+1)%resultSize)), enumMoveType.cornerChange, delta, "cornerChangenewsall"));
                            }
                            i++;
                        }
                    }
                    break;
            }

        }

        listOfMoves.sort(Comparator.comparing(cMove::getDelta));

        return listOfMoves;
    }



    public cAlgorithmResult makeStep(){

        cAlgorithmResult randomResult = makeRandomResult((int)Math.floor(sample.coordList.size()*percentSmaplesToFinish));

        try {


            while (true) {

                List<cMove> listOfMoves = generateAllPosibleCandidatMoves(randomResult, Arrays.asList(enumMoveType.cornerChange, enumMoveType.pointOuterInnerChange));

                if(listOfMoves.size()>0) {
                    if (listOfMoves.get(0).delta < 0) {
                        if (listOfMoves.get(0).moveType == enumMoveType.pointOuterInnerChange) {
                            randomResult = changePoints(randomResult.coordsOnPath.indexOf(listOfMoves.get(0).object1.get(1)), listOfMoves.get(0).object2.get(0), randomResult);
                        } else if (listOfMoves.get(0).moveType == enumMoveType.cornerChange) {
                            randomResult = changeCorners(randomResult.coordsOnPath.indexOf(listOfMoves.get(0).object1.get(0)), randomResult.coordsOnPath.indexOf(listOfMoves.get(0).object2.get(0)), randomResult);
                        }
                    } else
                        break;
                }else
                    break;
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return randomResult;
    }

}
