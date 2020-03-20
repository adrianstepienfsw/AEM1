import java.util.ArrayList;
import java.util.List;

public class cAlgorithmGreedyCycle {
    cSample sample;
    List<cAlgorithmResult> listOfResults = new ArrayList<cAlgorithmResult>();
    double averageDistance = -1;
    double maxDistance = -1;
    int maxDistanceIndex = -1;
    double minDistance = -1;
    int minDistanceIndex = -1;

    class cAlgorithmResult{
        List<Integer> coordsOnPath = new ArrayList<Integer>();         //list of indexes points
        double distance = 0;

        public void add(int point){
            coordsOnPath.add(point);
            calculateDistance(this);
        }
        public void removeLast(){
            coordsOnPath.remove(coordsOnPath.size()-1);
            calculateDistance(this);
        }
    }
    cAlgorithmGreedyCycle(cSample _sample){
        sample = _sample;
    }
    void makeGreedyCycle(){
        System.out.println("Making Greedy Cycle Algorithm for :"+sample.name);
        for(int point1=0; point1<sample.coordList.size(); point1++){
            cAlgorithmResult result = new cAlgorithmResult();
            result.add(point1); //adding first point
            double bestDistance = -1;
            int bestPoint2 = -1;
            for (int point2=0; point2<sample.coordList.size(); point2++) {
                if(point1 != point2){
                    result.add(point2);
                    if(bestDistance==-1 || bestDistance>result.distance){
                        bestPoint2 = point2;
                        bestDistance = result.distance;
                    }
                    result.removeLast();
                }
            }
            result.add(bestPoint2);         //adding best second point
            while(result.coordsOnPath.size() < Math.ceil((float)sample.coordList.size()/2)){
                double bestDistanceNext = -1;
                int bestPointNext = -1;
                for (int pointNext=0; pointNext<sample.coordList.size(); pointNext++) {
                    for(int point: result.coordsOnPath){            //checking if next point is on resultlist already
                        if(point == pointNext)  continue;
                    }
                    result.add(pointNext);
                    if(bestDistanceNext==-1 || bestDistanceNext>result.distance){
                        bestPointNext = pointNext;
                        bestDistanceNext = result.distance;
                    }
                    result.removeLast();
                }
                result.add(bestPointNext);
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
    public void calculateDistance(cAlgorithmResult result){
        result.distance = 0;
        for(int point: result.coordsOnPath){
            if(result.coordsOnPath.indexOf(point)==0)    continue;
            else{
                result.distance += sample.distanceMartix[result.coordsOnPath.get(result.coordsOnPath.indexOf(point)-1)][point];
            }
        }
        result.distance += sample.distanceMartix[result.coordsOnPath.get(result.coordsOnPath.size()-1)][result.coordsOnPath.get(0)];
    }
}
