import java.util.List;
import java.util.Random;

public class cAlgorithmLocalSearchSteepestCornersChanging extends cAlgorithm {

    public cAlgorithmLocalSearchSteepestCornersChanging(cSample _sample, float _percentSmaplesToFinish){
        super(_sample, _percentSmaplesToFinish);
    }

    @Override
    public void make(){
        System.out.println("Making Local Search Steepest (changing with corners) Algorithm for :"+sample.name);

        for(int x = 0; x<100; x++) {
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
                    List<Integer> order = makeRandomSeries(50, 50);
                    /*for (int point1ToChange : order) {
                        List<Integer> order2 = makeRandomSeriesExcludingList(50, 100, randomResult.coordsOnPath);
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
                        if (wasBetter)
                            break;
                    }*/
                             //changing corners
                    order = makeRandomSeries(50, 50);
                    for (int corner1ToChange : order) {
                        List<Integer> order2 = makeRandomSeries(50, 50);
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
                        if (wasBetter)
                            break;
                    }
                    if(bestMode == 0)
                        randomResult = changePoints(bestPoint1ToChange, bestPoint2ToChange, randomResult);
                    else if (bestMode == 1)
                        randomResult = changeCorners(bestPoint1ToChange, bestPoint2ToChange, randomResult);

                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            listOfResults.add(randomResult);
            System.out.print(".");
        }
        System.out.println(".");
        double sum = 0;
        for(cAlgorithm.cAlgorithmResult result : listOfResults){         //create statistics
            if(minDistance == -1 || minDistance > result.distance){
                minDistance = result.distance;
                minDistanceIndex = listOfResults.indexOf(result);
            }
            if(maxDistanceIndex < result.distance){
                maxDistance = result.distance;
                maxDistanceIndex = listOfResults.indexOf(result);
            }
            sum += result.distance;
        }
        averageDistance = sum/listOfResults.size();
        System.out.println("Average of results is "+averageDistance+" for :"+sample.name);
        System.out.println("Minimum distance is "+minDistance+" for :"+sample.name);
        System.out.println("Maximum distance is "+maxDistance+" for :"+sample.name);
    }
}
