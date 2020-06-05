import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class cAlgorithmLocalSearchGreedyCornersChanging extends cAlgorithm {

    String sampleName = "";

    public cAlgorithmLocalSearchGreedyCornersChanging(cSample _sample, float _percentSmaplesToFinish){
        super(_sample, _percentSmaplesToFinish);
    }


    @Override
    public void make(){
        System.out.println("Making Local Search Greedy (changing with corners) Algorithm for :"+sample.name);

        List<Long> times = new ArrayList<>();

        for(int x = 0; x<100; x++) {
            long startTime = System.currentTimeMillis();
            cAlgorithmResult randomResult = makeRandomResult((int)Math.floor(sample.coordList.size()*percentSmaplesToFinish));

            boolean wasBetter = true;

            try {
                while (wasBetter) {
                    wasBetter = false;

                    Random random = new Random();
                    if(random.nextBoolean()){       //changing points which are out of result
                        List<Integer> order = makeRandomSeries(100, 100);
                        for (int point1ToChange : order) {
                            List<Integer> order2 = makeRandomSeriesExcludingList(100, 200, randomResult.coordsOnPath);
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
                        List<Integer> order = makeRandomSeries(100, 100);
                        for (int corner1ToChange : order) {
                            List<Integer> order2 = makeRandomSeries(100, 100);
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
            long endTime = System.currentTimeMillis();
            times.add(endTime-startTime);
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

    public cAlgorithmResult makeStep(){

        cAlgorithmResult randomResult = makeRandomResult((int)Math.floor(sample.coordList.size()*percentSmaplesToFinish));

        boolean wasBetter = true;

        try {
            while (wasBetter) {
                wasBetter = false;

                Random random = new Random();
                if(random.nextBoolean()){       //changing points which are out of result
                    List<Integer> order = makeRandomSeries(100, 100);
                    for (int point1ToChange : order) {
                        List<Integer> order2 = makeRandomSeriesExcludingList(100, 200, randomResult.coordsOnPath);
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
                    List<Integer> order = makeRandomSeries(100, 100);
                    for (int corner1ToChange : order) {
                        List<Integer> order2 = makeRandomSeries(100, 100);
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

        return randomResult;
    }

    void test(){
        List<cAlgorithmResult> listResult = new ArrayList<>(1000);

        System.out.println("\nGenerating 1000 local optimums");

        for(int i = 0; i<1000; i++){
            listResult.add(makeStep());
            if(i%10==0)
                System.out.print(".");
        }

        listResult.sort(Comparator.comparing(cAlgorithmResult::getDistance));

        System.out.println("\nChecking point similarity to the best");
        List<Integer> similarityPointBest = new ArrayList<Integer>(1000);
        for (cAlgorithmResult result : listResult) {
            similarityPointBest.add(checkPointSimilarity(result, listResult.get(0)));
        }

        System.out.println("Checking point similarity to the all");
        List<Integer> similarityPointAverage = new ArrayList<Integer>(1000);
        for (cAlgorithmResult result1 : listResult) {
            int similaritySum = 0;
            for (cAlgorithmResult result2 : listResult) {
                similaritySum += checkPointSimilarity(result1, result2);
            }
            similarityPointAverage.add(similaritySum/1000);
        }

        System.out.println("Checking corner average similarity to the best");
        List<Integer> similarityCornerBest = new ArrayList<Integer>(1000);
        for (cAlgorithmResult result : listResult) {
            similarityCornerBest.add(checkCornerSimilarity(result, listResult.get(0)));
        }

        System.out.println("Checking corner average similarity to the all");
        List<Integer> similarityCornerAverage = new ArrayList<Integer>(1000);
        for (cAlgorithmResult result1 : listResult) {
            int similaritySum = 0;
            for (cAlgorithmResult result2 : listResult) {
                similaritySum += checkCornerSimilarity(result1, result2);
            }
            similarityCornerAverage.add(similaritySum/1000);
        }
        System.out.println("Saving logs");
        saveToCSV("similarityPointBest"+sampleName+".csv", listResult, similarityPointBest);
        saveToCSV("similarityPointAverage"+sampleName+".csv", listResult, similarityPointAverage);
        saveToCSV("similarityCornerBest"+sampleName+".csv", listResult, similarityCornerBest);
        saveToCSV("similarityCornerAverage"+sampleName+".csv", listResult, similarityCornerAverage);
    }

    int checkPointSimilarity(cAlgorithmResult res1, cAlgorithmResult res2){
        int similarity = 0;
        for (int point1 : res1.coordsOnPath) {
            for (int point2 : res2.coordsOnPath) {
                if (point1==point2)
                    similarity++;
            }
        }
        return similarity;
    }

    int checkCornerSimilarity(cAlgorithmResult res1, cAlgorithmResult res2){
        int similarity = 0;
        for (int point1 : res1.coordsOnPath) {
            int point1corner1 = point1;
            int point2corner1 = res1.coordsOnPath.get((res1.coordsOnPath.indexOf(point1)+1)%res1.coordsOnPath.size());
            for (int point2 : res2.coordsOnPath) {
                if (((point1corner1==point2) && (point2corner1==res2.coordsOnPath.get((res2.coordsOnPath.indexOf(point2)+1)%res2.coordsOnPath.size()))) ||
                    ((point2corner1==point2) && (point1corner1==res2.coordsOnPath.get((res2.coordsOnPath.indexOf(point2)+1)%res2.coordsOnPath.size()))))
                        similarity++;
            }
        }
        return similarity;
    }

    void saveToCSV(String fileName, List<cAlgorithmResult> resultList, List<Integer> similarityList){
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {

            StringBuilder sb1 = new StringBuilder();
            sb1.append("Funkcja celu");
            sb1.append(',');
            sb1.append("Podobienstwo");
            sb1.append('\n');
            writer.write(sb1.toString());

            for (int similarity : similarityList) {
                StringBuilder sb = new StringBuilder();

                sb.append(resultList.get(similarityList.indexOf(similarity)).distance);
                sb.append(',');
                sb.append(similarity);
                sb.append('\n');

                writer.write(sb.toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

}
