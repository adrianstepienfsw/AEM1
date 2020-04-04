import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class cSample extends Object {
    public cSample(String path){
        name = path;
        readSampleFromFile(path);
        defineMapSize();
    }

    public List<cCoords> coordList = new ArrayList<cCoords>();
    public long distanceMartix[][];
    public String name;
    public int mapWidth = 0;
    public int mapHeight = 0;
    public double mapRatio = 0;

    public static class cCoords{
        public int x;
        public int y;
        public cCoords(int _x, int _y){
            x = _x;
            y = _y;
        }
    }


    public void readSampleFromFile(String path){
        try{
            Reader reader = new FileReader("./Samples/"+path);
            BufferedReader bufferedReader = new BufferedReader(reader);
            int length =  Integer.parseInt(bufferedReader.readLine().split(" ")[1]);
            System.out.println("Reading "+length+" lines form file: "+path);
            for (int i=0; i<length; i++) {
                String cord[];
                cord = bufferedReader.readLine().split(" ");
                coordList.add(new cCoords(Integer.parseInt(cord[1]), Integer.parseInt(cord[2])));
            }
            if(!bufferedReader.readLine().equals("EOF")){
                throw new Throwable("Bad length of file!");
            }

            distanceMartix = new long[length][length];
            calculateDistanceMatrix();

        }catch (FileNotFoundException e){
            System.out.println("Error!!: File: "+ path+" not found!");
        }catch (IOException e){
            System.out.println("Error!!: "+e.toString());
        }catch (Throwable e){
            System.out.println("Error!!: "+e.getMessage());
        }
    }

    public void calculateDistanceMatrix(){
        System.out.println("Calculating distance matrix to :"+name);
        for (cCoords point1: coordList) {
            for(cCoords point2: coordList) {
                distanceMartix[coordList.indexOf(point1)][coordList.indexOf(point2)] = Math.round(Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2)));
            }
        }
    }

    public void defineMapSize(){
        for (cCoords point1: coordList) {
            for(cCoords point2: coordList) {
                if (point1.x>mapWidth)      mapWidth = point1.x;
                if (point1.y>mapHeight)     mapHeight = point1.y;
            }
        }
        mapRatio = (float)mapWidth/mapHeight;
    }
}
