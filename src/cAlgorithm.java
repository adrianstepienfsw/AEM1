import java.util.ArrayList;
import java.util.List;

public class cAlgorithm {
    cSample sample;
    List<cAlgorithmGreedyCycle.cAlgorithmResult> listOfResults = new ArrayList<cAlgorithmGreedyCycle.cAlgorithmResult>();
    double averageDistance = -1;
    double maxDistance = -1;
    int maxDistanceIndex = -1;
    double minDistance = -1;
    int minDistanceIndex = -1;

    cAlgorithm(cSample _sample){
        sample = _sample;
    }

    class cAlgorithmResult{
        List<Integer> coordsOnPath = new ArrayList<Integer>();         //list of indexes points
        double distance = 0;

        public void add(int point, int index){
            coordsOnPath.add(index, point);
            calculateDistance(this);
        }
        public void remove(int index){
            coordsOnPath.remove(index);
            calculateDistance(this);
        }
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
    public void paintPath(){

    }
}
