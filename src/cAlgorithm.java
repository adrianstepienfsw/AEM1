import java.util.*;

public abstract class cAlgorithm {

    public abstract void make();
    cSample sample;
    List<cAlgorithmGreedyCycle.cAlgorithmResult> listOfResults = new ArrayList<cAlgorithmGreedyCycle.cAlgorithmResult>();
    double averageDistance = -1;
    double maxDistance = -1;
    int maxDistanceIndex = -1;
    double minDistance = -1;
    int minDistanceIndex = -1;
    float percentSmaplesToFinish = 1;

    public cAlgorithm(cSample _sample){
        sample = _sample;
    }

    public cAlgorithm(cSample _sample, float _percentSmaplesToFinish){
        sample = _sample;
        percentSmaplesToFinish = _percentSmaplesToFinish;
    }

    class cProposals{
        List<cProposal> listOfProposals = new ArrayList<cProposal>();
        int point;

        public cProposals(int _point) {
            point = _point;
        }

        class cProposal{
            int indexOfEdge;
            int costInsertion;

            public cProposal(int _indexOfEdge, int _costInsertion){
                indexOfEdge = _indexOfEdge;
                costInsertion = _costInsertion;
            }
        }

        public void add(int _indexOfEdge, int _costInsertion){
            listOfProposals.add(new cProposal(_indexOfEdge, _costInsertion));
        }
    }

    class cAlgorithmResult{
        List<Integer> coordsOnPath = new ArrayList<Integer>();         //list of indexes points
        double distance = 0;

        public void add(int point, int index){
            coordsOnPath.add(index, point);
            calculatePathDistance(this);
        }
        public void remove(int index){
            coordsOnPath.remove(index);
            calculatePathDistance(this);
        }
    }

    public int calculateCost(int pointInSample, int edgeInResult, cAlgorithmResult result){
        int cost = 0;
        if(edgeInResult+1<result.coordsOnPath.size()){
            cost += sample.distanceMartix[pointInSample][result.coordsOnPath.get(edgeInResult)];
            cost += sample.distanceMartix[pointInSample][result.coordsOnPath.get(edgeInResult+1)];
            cost -= sample.distanceMartix[result.coordsOnPath.get(edgeInResult)][result.coordsOnPath.get(edgeInResult+1)];
        }else{  //if its returning edge (last edge)
            cost += sample.distanceMartix[pointInSample][result.coordsOnPath.get(edgeInResult)];
            cost += sample.distanceMartix[pointInSample][result.coordsOnPath.get(0)];
            cost -= sample.distanceMartix[result.coordsOnPath.get(edgeInResult)][0];
        }

        return cost;
    }

    public void calculatePathDistance(cAlgorithmResult result){
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
