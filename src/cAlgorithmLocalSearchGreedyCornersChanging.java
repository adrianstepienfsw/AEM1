import java.util.List;
import java.util.Random;

public class cAlgorithmLocalSearchGreedyCornersChanging extends cAlgorithm {

    public cAlgorithmLocalSearchGreedyCornersChanging(cSample _sample, float _percentSmaplesToFinish){
        super(_sample, _percentSmaplesToFinish);
    }

    @Override
    public void make(){
        System.out.println("Making Local Search Greedy Algorithm for :"+sample.name);

        for(int x = 0; x<100; x++) {
            cAlgorithmResult randomResult = makeRandomResult((int)Math.floor(sample.coordList.size()*percentSmaplesToFinish));

            boolean wasBetter = true;


            try {
                while (wasBetter) {
                    wasBetter = false;

                    Random random = new Random();
                    if(random.nextBoolean()){       //changing points which are out of result
                        List<Integer> order = makeRandomSeries(50, 50);
                        for (int point1ToChange : order) {
                            List<Integer> order2 = makeRandomSeriesExcludingList(50, 100, randomResult.coordsOnPath);
                            double dist1 = randomResult.distance;
                            for (int point2ToChange : order2) {
                                if(point2ToChange == randomResult.coordsOnPath.get(point1ToChange))
                                    continue;
                                int delta = calculateDelta(point1ToChange, point2ToChange, randomResult);
                                if (delta < 0) {
                                    randomResult = changePoints(point1ToChange, point2ToChange, randomResult);
                                    if (delta != (randomResult.distance - dist1))
                                        throw new Throwable("Wyliczona delta się nie zgadza");
                                    wasBetter = true;
                                    break;
                                }
                            }
                            if (wasBetter)
                                break;
                        }
                    }else{              //changing corners
                        List<Integer> order = makeRandomSeries(50, 50);
                        for (int corner1ToChange : order) {
                            List<Integer> order2 = makeRandomSeries(50, 50);
                            double dist1 = randomResult.distance;
                            for (int corner2ToChange : order2) {
                                if((corner1ToChange == corner2ToChange) || (Math.abs(corner1ToChange - corner2ToChange) == 1 || (Math.abs(corner1ToChange - corner2ToChange) == 49)))
                                    continue;
                                int delta = calculateDeltaToCorners(corner1ToChange, corner2ToChange, randomResult);
                                if (delta < 0) {
                                    randomResult = changeCorners(corner1ToChange, corner2ToChange, randomResult);
                                    if (delta != (randomResult.distance - dist1))
                                        throw new Throwable("Wyliczona delta się nie zgadza");
                                    wasBetter = true;
                                    break;
                                }
                            }
                            if (wasBetter)
                                break;
                        }
                    }

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
