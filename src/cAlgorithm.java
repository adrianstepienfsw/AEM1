import java.util.*;

public abstract class cAlgorithm {

    public abstract void make();
    public abstract cAlgorithmResult makeStep();
    cSample sample;
    List<cAlgorithm.cAlgorithmResult> listOfResults = new ArrayList<cAlgorithmGreedyCycle.cAlgorithmResult>();
    double averageDistance = -1;
    double maxDistance = -1;
    int maxDistanceIndex = -1;
    double minDistance = -1;
    int minDistanceIndex = -1;
    float percentSmaplesToFinish = 1;
    long minTime = -1;
    long maxTime = -1;
    long averageTime = -1;

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

        //adding point whitout calculating
        public void addWithoutCalculating(int point){
            coordsOnPath.add(point);
        }

        double getDistance(){
            return this.distance;
        }

        public cAlgorithmResult clone(){
            cAlgorithmResult returnResult = new cAlgorithmResult();
            returnResult.distance = this.distance;
            for (int cord :this.coordsOnPath) {
                returnResult.coordsOnPath.add(cord);
            }
            return returnResult;
        }
    }


    public void testAlgorithm(int countStep, String description){
        System.out.println();
        System.out.println();
        System.out.println(description);

        List<Long> times = new ArrayList<>();

        for(int x = 0; x<countStep; x++) {
            long startTime = System.currentTimeMillis();

            cAlgorithmResult resultStep = makeStep();

            long endTime = System.currentTimeMillis();
            times.add(endTime-startTime);

            listOfResults.add(resultStep);
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
            if(maxDistance < result.distance){
                maxDistance = result.distance;
                maxDistanceIndex = listOfResults.indexOf(result);
            }
            sum += result.distance;
        }

        long timeSum = 0;
        minTime = times.get(0);
        maxTime = times.get(0);
        for (long time:times){
            timeSum += time;
            if(time<minTime)
                minTime = time;
            if(time>maxTime)
                maxTime = time;
        }

        averageTime = timeSum/listOfResults.size();
        averageDistance = sum/listOfResults.size();
        System.out.println("Average of results is "+averageDistance+" for :"+sample.name);
        System.out.println("Minimum distance is "+minDistance+" for :"+sample.name);
        System.out.println("Maximum distance is "+maxDistance+" for :"+sample.name);
        System.out.println("Average time executing one loop :"+averageTime+" ms");
        System.out.println("Minimum time executing one loop :"+minTime+" ms");
        System.out.println("Maximum time executing one loop :"+maxTime+" ms");

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

    public int calculateDeltaToCorners(int corner1InResult, int corner2InResult, cAlgorithmResult result){
        int corner1point1 = result.coordsOnPath.get(corner1InResult);
        int corner2point1 = result.coordsOnPath.get(corner2InResult);
        int corner1point2;
        int corner2point2;
        if(corner1InResult==result.coordsOnPath.size()-1)
            corner1point2 = result.coordsOnPath.get(0);
        else
            corner1point2 = result.coordsOnPath.get(corner1InResult+1);
        if(corner2InResult==result.coordsOnPath.size()-1)
            corner2point2 = result.coordsOnPath.get(0);
        else
            corner2point2 = result.coordsOnPath.get(corner2InResult+1);

        int delta = 0;

        delta += sample.distanceMartix[corner1point1][corner2point1];
        delta += sample.distanceMartix[corner1point2][corner2point2];
        delta -= sample.distanceMartix[corner1point1][corner1point2];
        delta -= sample.distanceMartix[corner2point1][corner2point2];

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

    public cAlgorithmResult changeCorners(int corner1InResult, int corner2InResult, cAlgorithmResult result){
        int corner1point1 = corner1InResult;
        int corner2point1 = corner2InResult;
        int corner1point2;
        int corner2point2;
        if(corner1InResult==result.coordsOnPath.size()-1)
            corner1point2 = 0;
        else
            corner1point2 = corner1InResult+1;
        if(corner2InResult==result.coordsOnPath.size()-1)
            corner2point2 = 0;
        else
            corner2point2 = corner2InResult+1;

        cAlgorithmResult returnResult = new cAlgorithmResult();
        int index = 1;
        int pom = 0;

        returnResult.addWithoutCalculating(result.coordsOnPath.get(corner1point2));
        if((corner1point2+index) > result.coordsOnPath.size()-1)
            pom = -result.coordsOnPath.size();
        while((corner1point2+index+pom) != (corner2point1)){
            returnResult.addWithoutCalculating(result.coordsOnPath.get(corner1point2+index+pom));
            index++;
            if((corner1point2+index) > result.coordsOnPath.size()-1)
                pom = -result.coordsOnPath.size();
        }
        returnResult.addWithoutCalculating(result.coordsOnPath.get(corner1point2+index+pom));
        index++;
        if((corner1point2+index) > result.coordsOnPath.size()-1)
            pom = -result.coordsOnPath.size();
        returnResult.addWithoutCalculating(result.coordsOnPath.get(corner1point1));
        int index1 = -1;
        pom = 0;
        if((corner1point1+index1) < 0)
            pom = result.coordsOnPath.size();
        while(result.coordsOnPath.get(corner1point1+index1+pom) != result.coordsOnPath.get(corner2point2)){
            returnResult.addWithoutCalculating(result.coordsOnPath.get(corner1point1+index1+pom));
            index1--;
            if((corner1point1+index1) < 0)
                pom = result.coordsOnPath.size();
        }
        returnResult.add(result.coordsOnPath.get(corner2point2), result.coordsOnPath.size()-1);

        return returnResult;
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
        //randomGenerator.setSeed(10);

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
        //randomGenerator.setSeed(10);

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

    //generate list of "count" Integers in range 0-("threshold"-1), final list dont have elements from list
    public List<Integer> makeRandomSeriesExcludingList(int count, int threshold, List<Integer> list) {
        List<Integer> result = new ArrayList<>();
        Random randomGenerator = new Random();
        //randomGenerator.setSeed(10);

        while (result.size() < count) {
            int random = (int) Math.floor(randomGenerator.nextFloat() * (threshold - result.size() - list.size()));
            outLoop:
            for(int i=0; i<=random; i++){
                for(int cord : result){
                    if(cord==i){
                        random++;
                        continue outLoop;
                    }
                }
                for(int cord : list){
                    if(cord==i){
                        random++;
                        continue outLoop;
                    }
                }
            }

            result.add(random);
        }
        return result;
    }


    //generate list of "count" Integers from "list"
    public List<Integer> makeRandomSeriesOnlyFromList(int count, List<Integer> list) {
        List<Integer> x = makeRandomSeries(list.size(), list.size());

        List<Integer> result = new ArrayList<>();

        int i=0;
        while(result.size()<count){
            result.add(list.get(x.get(i)));
            i++;
        }
        return result;
    }

    //generate disjunctive part of the collection
    public List<Integer> makeDisjunctiveList(List<Integer> list1, List<Integer> list2) {
        List<Integer> result = new ArrayList<>();

        for (int integer1 : list1) {
            boolean is = false;
            loop:
            for (int integer2 : list2) {
                if(integer1 == integer2){
                    is = true;
                    break loop;
                }
            }
            if(is == false){
                result.add(integer1);
            }
        }
        for (int integer1 : list2) {
            boolean is = false;
            loop:
            for (int integer2 : list1) {
                if(integer1 == integer2){
                    is = true;
                    break loop;
                }
            }
            if(is == false){
                result.add(integer1);
            }
        }


        return result;
    }
}
