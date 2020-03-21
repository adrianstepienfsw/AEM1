import java.util.ArrayList;
import java.util.List;

public class cAlgorithmRegretHeuristics extends cAlgorithm {

    public cAlgorithmRegretHeuristics(cSample _sample){
        super(_sample);
    }

    class cProporsalsWithRegret extends cProposals{
        int regret;

        public cProporsalsWithRegret(int _point){
            super(_point);
        }
        public void add(int _indexOfEdge, int _costInsertion){
            super.add(_indexOfEdge, _costInsertion);
            regret = listOfProposals.get(listOfProposals.size()-1).costInsertion-listOfProposals.get(0).costInsertion;
        }
    }

    public void make(){
        System.out.println("Making Regret Heuristics Algorithm for :"+sample.name);
        for(int point1=0; point1<sample.coordList.size(); point1++) {
            cAlgorithmResult result = new cAlgorithmResult();
            result.add(point1, 0); //adding first point
            double bestDistance = -1;
            int bestPoint2 = -1;
            for (int point2 = 0; point2 < sample.coordList.size(); point2++) {
                if (point1 != point2) {
                    result.add(point2, 1);
                    if (bestDistance == -1 || bestDistance > result.distance) {
                        bestPoint2 = point2;
                        bestDistance = result.distance;
                    }
                    result.remove(1);
                }
            }
            result.add(bestPoint2, 1);         //adding best second point

            while (result.coordsOnPath.size() < Math.ceil((float) sample.coordList.size() / 2)) {

                //calculating lists of proporsals for each point which isn't on path yet
                List<cProporsalsWithRegret> listOfProporsals = new ArrayList<cProporsalsWithRegret>();
                outerloop:
                for (int pointNext = 0; pointNext < sample.coordList.size(); pointNext++) {
                    for (int point : result.coordsOnPath) {            //checking if next point is on resultlist already
                        if (point == pointNext) continue outerloop;
                    }

                    int firstIndexToProporsal = -1;
                    int firstCost = -1;
                    int secondIndexToProporsal = -1;
                    int secondCost = -1;
                    for (int point : result.coordsOnPath) {
                        int edge = result.coordsOnPath.indexOf(point);
                        int cost = calculateCost(pointNext, edge, result);
                        calculateCost(pointNext, edge, result);
                        if(secondIndexToProporsal==-1){
                            if (firstIndexToProporsal==-1){
                                firstCost = cost;
                                firstIndexToProporsal = edge;
                            }else{
                                if(firstCost>cost){
                                    secondCost = firstCost;
                                    secondIndexToProporsal = firstIndexToProporsal;
                                    firstCost = cost;
                                    firstIndexToProporsal = edge;
                                }else{
                                    secondCost = cost;
                                    secondIndexToProporsal = edge;
                                }
                            }
                        }else{
                            if(cost<firstCost){
                                secondCost = firstCost;
                                secondIndexToProporsal = firstIndexToProporsal;
                                firstCost = cost;
                                firstIndexToProporsal = edge;
                            }else if(cost<secondCost){
                                secondCost = cost;
                                secondIndexToProporsal = edge;
                            }
                        }
                    }
                    listOfProporsals.add(new cProporsalsWithRegret(pointNext));
                    listOfProporsals.get(listOfProporsals.size()-1).add(firstIndexToProporsal, firstCost);
                    listOfProporsals.get(listOfProporsals.size()-1).add(secondIndexToProporsal, secondCost);
                }

                //looking for proporsal with the biggest regret
                int indexOfNextPoint = -1;
                int indexOfEdge = -1;
                int theBiggestRegret = -1;
                for(cProporsalsWithRegret proporsal: listOfProporsals){
                    if(indexOfNextPoint == -1){
                        indexOfNextPoint = proporsal.point;
                        theBiggestRegret = proporsal.regret;
                        indexOfEdge = proporsal.listOfProposals.get(0).indexOfEdge;
                    }else if(theBiggestRegret<proporsal.regret){
                        indexOfNextPoint = proporsal.point;
                        theBiggestRegret = proporsal.regret;
                        indexOfEdge = proporsal.listOfProposals.get(0).indexOfEdge;
                    }
                }
                result.add(indexOfNextPoint, indexOfEdge);         //adding best second point
            }

            listOfResults.add(result);
            System.out.print(".");
        }
        System.out.println(".");
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
