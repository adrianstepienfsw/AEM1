import java.util.ArrayList;
import java.util.List;

public class cAlgorithmGreedyCycle extends cAlgorithm {

    cAlgorithmGreedyCycle(cSample _sample){
        super(_sample);
    }

    void makeGreedyCycle(){
        System.out.println("Making Greedy Cycle Algorithm for :"+sample.name);
        for(int point1=0; point1<sample.coordList.size(); point1++){
            cAlgorithmResult result = new cAlgorithmResult();
            result.add(point1, 0); //adding first point
            double bestDistance = -1;
            int bestPoint2 = -1;
            for (int point2=0; point2<sample.coordList.size(); point2++) {
                if(point1 != point2){
                    result.add(point2,1);
                    if(bestDistance==-1 || bestDistance>result.distance){
                        bestPoint2 = point2;
                        bestDistance = result.distance;
                    }
                    result.remove(1);
                }
            }
            result.add(bestPoint2, 1);         //adding best second point
            while(result.coordsOnPath.size() < Math.ceil((float)sample.coordList.size()/2)){
                double bestDistanceNext = -1;
                int bestPointNext = -1;
                int bestIndexForPointNext = -1;
                outerloop:
                for (int pointNext=0; pointNext<sample.coordList.size(); pointNext++) {
                    for(int point: result.coordsOnPath){            //checking if next point is on resultlist already
                        if(point == pointNext)  continue outerloop;
                    }
                    for(int indexForPointNext = 0; indexForPointNext<=result.coordsOnPath.size(); indexForPointNext++) {
                        result.add(pointNext, indexForPointNext);
                        if (bestDistanceNext == -1 || bestDistanceNext > result.distance) {
                            bestPointNext = pointNext;
                            bestDistanceNext = result.distance;
                            bestIndexForPointNext = indexForPointNext;
                        }
                        result.remove(indexForPointNext);
                    }
                }
                result.add(bestPointNext, bestIndexForPointNext);
            }
            listOfResults.add(result);
        }
        double sum = 0;
        for(cAlgorithmGreedyCycle.cAlgorithmResult result : listOfResults){         //create statistics
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
