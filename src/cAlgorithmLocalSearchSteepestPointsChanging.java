import java.util.List;

public class cAlgorithmLocalSearchSteepestPointsChanging extends cAlgorithm {

    public cAlgorithmLocalSearchSteepestPointsChanging(cSample _sample, float _percentSmaplesToFinish){
        super(_sample, _percentSmaplesToFinish);
    }

    @Override
    public void make(){
        System.out.println("Making Local Search Steepest (changing with points) Algorithm for :"+sample.name);

        long startTime = System.currentTimeMillis();
        for(int x = 0; x<100; x++) {
            cAlgorithmResult randomResult = makeRandomResult((int)Math.floor(sample.coordList.size()*percentSmaplesToFinish));


            boolean wasBetter = true;


            try {
                while (wasBetter) {
                    wasBetter = false;

                    List<Integer> order = makeRandomSeries(50, 50);
                    int bestDelta = 0;
                    int bestPoint1ToChange = 0;
                    int bestPoint2ToChange = 0;
                    for (int point1ToChange : order) {
                        List<Integer> order2 = makeRandomSeries(100, 100);
                        double dist1 = randomResult.distance;
                        for (int point2ToChange : order2) {
                            if(point2ToChange == randomResult.coordsOnPath.get(point1ToChange))
                                continue;
                            int delta = calculateDelta(point1ToChange, point2ToChange, randomResult);
                            if (delta < bestDelta) {
                                bestDelta = delta;
                                bestPoint1ToChange = point1ToChange;
                                bestPoint2ToChange = point2ToChange;
                                wasBetter = true;
                            }
                        }
                    }
                    if(bestDelta<0){
                        randomResult = changePoints(bestPoint1ToChange, bestPoint2ToChange, randomResult);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            listOfResults.add(randomResult);
            System.out.print(".");
        }
        long endTime = System.currentTimeMillis();
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
        System.out.println("Average time executing one loop :"+(endTime-startTime)/100+" ms");
        System.out.println();
    }
}
