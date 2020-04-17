import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class cAlgorithmLocalSearchSteepestCornersChangingListMoves extends cAlgorithm {

    public cAlgorithmLocalSearchSteepestCornersChangingListMoves(cSample _sample, float _percentSmaplesToFinish){
        super(_sample, _percentSmaplesToFinish);
    }

    enum enumMoveType {
        cornerChange, pointOuterInnerChange, pointInnerChange;
    }


    class cMove{
        List<Integer> object1 = new ArrayList<Integer>();
        List<Integer> object2 = new ArrayList<Integer>();

        enumMoveType moveType;

        public cMove(List<Integer> _object1, List<Integer> _object2, enumMoveType _moveType){
            object1 = _object1;
            object2 = _object2;
            moveType = _moveType;
        }
    }

    public void make(){

    }

    public cAlgorithmResult makeStep(){

        cAlgorithmResult randomResult = makeRandomResult((int)Math.floor(sample.coordList.size()*percentSmaplesToFinish));

        boolean wasBetter = true;


        try {
            while (wasBetter) {
                wasBetter = false;

                int bestDelta = 0;
                int bestPoint1ToChange = 0;
                int bestPoint2ToChange = 0;
                int bestMode = 0;
                //changing points which are out of result
                List<Integer> order = makeRandomSeries(randomResult.coordsOnPath.size(), randomResult.coordsOnPath.size());
                for (int point1ToChange : order) {
                    List<Integer> order2 = makeRandomSeriesExcludingList(sample.coordList.size()-randomResult.coordsOnPath.size(), sample.coordList.size(), randomResult.coordsOnPath);
                    double dist1 = randomResult.distance;
                    for (int point2ToChange : order2) {
                        if(point2ToChange == randomResult.coordsOnPath.get(point1ToChange))
                            continue;
                        int delta = calculateDelta(point1ToChange, point2ToChange, randomResult);
                        if (delta < bestDelta) {
                            bestDelta = delta;
                            bestPoint1ToChange = point1ToChange;
                            bestPoint2ToChange = point2ToChange;
                            bestMode = 0;
                            wasBetter = true;
                        }
                    }
                }

                //changing corners
                order = makeRandomSeries(randomResult.coordsOnPath.size(), randomResult.coordsOnPath.size());
                for (int corner1ToChange : order) {
                    List<Integer> order2 = makeRandomSeries(randomResult.coordsOnPath.size(), randomResult.coordsOnPath.size());
                    double dist1 = randomResult.distance;
                    for (int corner2ToChange : order2) {
                        if((corner1ToChange == corner2ToChange) || (Math.abs(corner1ToChange - corner2ToChange) == 1 || (Math.abs(corner1ToChange - corner2ToChange) == 49)))
                            continue;
                        int delta = calculateDeltaToCorners(corner1ToChange, corner2ToChange, randomResult);
                        if (delta < bestDelta) {
                            bestDelta = delta;
                            bestPoint1ToChange = corner1ToChange;
                            bestPoint2ToChange = corner2ToChange;
                            bestMode = 1;
                            wasBetter = true;
                        }
                    }
                }
                if(bestDelta<0) {
                    if (bestMode == 0)
                        randomResult = changePoints(bestPoint1ToChange, bestPoint2ToChange, randomResult);
                    else if (bestMode == 1)
                        randomResult = changeCorners(bestPoint1ToChange, bestPoint2ToChange, randomResult);
                }

            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return randomResult;
    }

}
