import java.util.ArrayList;
import java.util.List;

public class cAlgorithmLocalSearchGreedyPointsChanging extends cAlgorithm {

    public cAlgorithmLocalSearchGreedyPointsChanging(cSample _sample, float _percentSmaplesToFinish){
        super(_sample, _percentSmaplesToFinish);
    }

    public cAlgorithmResult makeStep(){
        return new cAlgorithmResult();
    }

    @Override
    public void make(){
        System.out.println("Making Local Search Greedy (changing with points) Algorithm for :"+sample.name);

        List<Long> times = new ArrayList<>();
        for(int x = 0; x<100; x++) {
            cAlgorithmResult randomResult = makeRandomResult((int)Math.floor(sample.coordList.size()*percentSmaplesToFinish));
            long startTime = System.currentTimeMillis();

            boolean wasBetter = true;


            try {
                while (wasBetter) {
                    wasBetter = false;

                    List<Integer> order = makeRandomSeries(50, 50);
                    for (int point1ToChange : order) {
                        List<Integer> order2 = makeRandomSeries(100, 100);
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

                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            listOfResults.add(randomResult);
            System.out.print(".");
            long endTime = System.currentTimeMillis();
            times.add(endTime-startTime);
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

        long timeSum = 0;
        long minTime = times.get(0);
        long maxTime = times.get(0);
        for (long time:times){
            timeSum += time;
            if(time<minTime)
                minTime = time;
            if(time>maxTime)
                maxTime = time;
        }

        averageDistance = sum/listOfResults.size();
        System.out.println("Average of results is "+averageDistance+" for :"+sample.name);
        System.out.println("Minimum distance is "+minDistance+" for :"+sample.name);
        System.out.println("Maximum distance is "+maxDistance+" for :"+sample.name);
        System.out.println("Average time executing one loop :"+(timeSum)/100+" ms");
        System.out.println("Minimum time executing one loop :"+minTime+" ms");
        System.out.println("Maximum time executing one loop :"+maxTime+" ms");
        System.out.println();
    }
}
