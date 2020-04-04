import java.util.*;

public abstract class cAlgorithm {

    public abstract void make();
    cSample sample;
    List<cAlgorithm.cAlgorithmResult> listOfResults = new ArrayList<cAlgorithmGreedyCycle.cAlgorithmResult>();
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

        //adding point on index
        public void add(int point, int index){
            coordsOnPath.add(index, point);
            calculatePathDistance(this);
        }

        //deleting point from index
        public void remove(int index){
            coordsOnPath.remove(index);
            calculatePathDistance(this);
        }
    }

    //calculating cost of inserting point to "result". Point which index in sample is "pointInSample". "edgeInResult" is
    //index of corner where will be insert point.
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

    public int calculateDelta(int point1InResult, int point2InSample, cAlgorithmResult result){
        boolean point2IsInResult = false;
        int point2InResult = -1;
        int delta = 0;

        //checking if point 2 is in result
        for (int i : result.coordsOnPath){
            if(point2InSample==i){
                point2IsInResult = true;
                point2InResult = result.coordsOnPath.indexOf(i);
                break;
            }
        }

        if(point2IsInResult){   //two points in result
            if((Math.abs(point1InResult - point2InResult) == 1)||Math.abs(point1InResult - point2InResult) == (result.coordsOnPath.size()-1)){     //two points next to each oder
                //adding cost second point
                if (point1InResult == 0){
                    long temp = sample.distanceMartix[point2InSample][result.coordsOnPath.get(result.coordsOnPath.size()-1)];
                    if(temp==0)
                        temp = sample.distanceMartix[point2InSample][result.coordsOnPath.get(point1InResult)];
                    delta += temp;
                }
                else{
                    long temp = sample.distanceMartix[point2InSample][result.coordsOnPath.get(point1InResult-1)];
                    if(temp==0)
                        temp = sample.distanceMartix[point2InSample][result.coordsOnPath.get(point1InResult)];
                    delta += temp;
                }
                if (point1InResult == result.coordsOnPath.size()-1){
                    long temp = sample.distanceMartix[point2InSample][result.coordsOnPath.get(0)];
                    if(temp==0)
                        temp = sample.distanceMartix[point2InSample][result.coordsOnPath.get(point1InResult)];
                    delta += temp;
                }
                else{
                    long temp = sample.distanceMartix[point2InSample][result.coordsOnPath.get(point1InResult+1)];
                    if(temp==0)
                        temp = sample.distanceMartix[point2InSample][result.coordsOnPath.get(point1InResult)];
                    delta += temp;
                }
                //adding cost first point
                if (point2InResult == 0){
                    long temp = sample.distanceMartix[result.coordsOnPath.get(result.coordsOnPath.size()-1)][result.coordsOnPath.get(point1InResult)];
                    if(temp==0)
                        temp = sample.distanceMartix[result.coordsOnPath.get(point2InResult)][result.coordsOnPath.get(point1InResult)];
                    delta += temp;
                }
                else{
                    long temp = sample.distanceMartix[result.coordsOnPath.get(point2InResult-1)][result.coordsOnPath.get(point1InResult)];
                    if(temp==0)
                        temp = sample.distanceMartix[result.coordsOnPath.get(point2InResult)][result.coordsOnPath.get(point1InResult)];
                    delta += temp;
                }
                if (point2InResult == result.coordsOnPath.size()-1){
                    long temp = sample.distanceMartix[result.coordsOnPath.get(0)][result.coordsOnPath.get(point1InResult)];
                    if(temp==0)
                        temp = sample.distanceMartix[result.coordsOnPath.get(point2InResult)][result.coordsOnPath.get(point1InResult)];
                    delta += temp;
                }
                else{
                    long temp = sample.distanceMartix[result.coordsOnPath.get(point2InResult+1)][result.coordsOnPath.get(point1InResult)];
                    if(temp==0)
                        temp = sample.distanceMartix[result.coordsOnPath.get(point2InResult)][result.coordsOnPath.get(point1InResult)];
                    delta += temp;
                }
            }else{
                //adding cost second point
                if (point1InResult == 0)
                    delta += sample.distanceMartix[point2InSample][result.coordsOnPath.get(result.coordsOnPath.size()-1)];
                else
                    delta += sample.distanceMartix[point2InSample][result.coordsOnPath.get(point1InResult-1)];
                if (point1InResult == result.coordsOnPath.size()-1)
                    delta += sample.distanceMartix[point2InSample][result.coordsOnPath.get(0)];
                else
                    delta += sample.distanceMartix[point2InSample][result.coordsOnPath.get(point1InResult+1)];
                //adding cost first point
                if (point2InResult == 0)
                    delta += sample.distanceMartix[result.coordsOnPath.get(result.coordsOnPath.size()-1)][result.coordsOnPath.get(point1InResult)];
                else
                    delta += sample.distanceMartix[result.coordsOnPath.get(point2InResult-1)][result.coordsOnPath.get(point1InResult)];
                if (point2InResult == result.coordsOnPath.size()-1)
                    delta += sample.distanceMartix[result.coordsOnPath.get(0)][result.coordsOnPath.get(point1InResult)];
                else
                    delta += sample.distanceMartix[result.coordsOnPath.get(point2InResult+1)][result.coordsOnPath.get(point1InResult)];
            }


            //delete old cost second point
            if (point2InResult == 0)
                delta -= sample.distanceMartix[point2InSample][result.coordsOnPath.get(result.coordsOnPath.size()-1)];
            else
                delta -= sample.distanceMartix[point2InSample][result.coordsOnPath.get(point2InResult-1)];
            if (point2InResult == result.coordsOnPath.size()-1)
                delta -= sample.distanceMartix[point2InSample][result.coordsOnPath.get(0)];
            else
                delta -= sample.distanceMartix[point2InSample][result.coordsOnPath.get(point2InResult+1)];
            //delete old cost first point
            if (point1InResult == 0)
                delta -= sample.distanceMartix[result.coordsOnPath.get(result.coordsOnPath.size()-1)][result.coordsOnPath.get(point1InResult)];
            else
                delta -= sample.distanceMartix[result.coordsOnPath.get(point1InResult-1)][result.coordsOnPath.get(point1InResult)];
            if (point1InResult == result.coordsOnPath.size()-1)
                delta -= sample.distanceMartix[result.coordsOnPath.get(0)][result.coordsOnPath.get(point1InResult)];
            else
                delta -= sample.distanceMartix[result.coordsOnPath.get(point1InResult+1)][result.coordsOnPath.get(point1InResult)];
        }else{  //only first in result
            if (point1InResult == 0)
                delta += sample.distanceMartix[point2InSample][result.coordsOnPath.get(result.coordsOnPath.size()-1)];
            else
                delta += sample.distanceMartix[point2InSample][result.coordsOnPath.get(point1InResult-1)];
            if (point1InResult == result.coordsOnPath.size()-1)
                delta += sample.distanceMartix[point2InSample][result.coordsOnPath.get(0)];
            else
                delta += sample.distanceMartix[point2InSample][result.coordsOnPath.get(point1InResult+1)];
            if (point1InResult == 0)
                delta -= sample.distanceMartix[result.coordsOnPath.get(point1InResult)][result.coordsOnPath.get(result.coordsOnPath.size()-1)];
            else
                delta -= sample.distanceMartix[result.coordsOnPath.get(point1InResult)][result.coordsOnPath.get(point1InResult-1)];
            if (point1InResult == result.coordsOnPath.size()-1)
                delta -= sample.distanceMartix[result.coordsOnPath.get(point1InResult)][result.coordsOnPath.get(0)];
            else
                delta -= sample.distanceMartix[result.coordsOnPath.get(point1InResult)][result.coordsOnPath.get(point1InResult+1)];
        }

        return delta;
    }

    public cAlgorithmResult changePoints(int point1InResult, int point2InSample, cAlgorithmResult result){
        boolean point2IsInResult = false;
        int point2InResult = -1;

        //checking if point 2 is in result
        for (int i : result.coordsOnPath){
            if(point2InSample==i){
                point2IsInResult = true;
                point2InResult = result.coordsOnPath.indexOf(i);
                break;
            }
        }

        if(point2IsInResult){
            int point1InSample = result.coordsOnPath.get(point1InResult);
            result.remove(point1InResult);
            result.add(point2InSample ,point1InResult);
            result.remove(point2InResult);
            result.add(point1InSample ,point2InResult);
        }else{
            result.remove(point1InResult);
            result.add(point2InSample ,point1InResult);
        }
        return result;
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

    public cAlgorithmResult makeRandomResult(int count) {
        cAlgorithmResult result = new cAlgorithmResult();
        Random randomGenerator = new Random();

        while (result.coordsOnPath.size() < count) {
            int random = (int) Math.floor(randomGenerator.nextFloat() * (sample.coordList.size() - result.coordsOnPath.size()));
            for(int i=0; i<=random; i++){
                for(int cord : result.coordsOnPath){
                    if(cord==i){
                        random++;
                        break;
                    }
                }
            }

            result.add(random, result.coordsOnPath.size());
        }
        return result;
    }

    //generate list of "count" Integers in range 0-("threshold"-1)
    public List<Integer> makeRandomSeries(int count, int threshold) {
        List<Integer> result = new ArrayList<>();
        Random randomGenerator = new Random();

        while (result.size() < count) {
            int random = (int) Math.floor(randomGenerator.nextFloat() * (threshold - result.size()));
            for(int i=0; i<=random; i++){
                for(int cord : result){
                    if(cord==i){
                        random++;
                        break;
                    }
                }
            }

            result.add(random);
        }
        return result;
    }
}
