import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class cMyPanel extends JPanel {

    final int panelSizePx = 500;
    int panelWidthPx = 0;
    int panelHeightPx = 0;
    final int panelMargin = 10;

    int pointDiameter = 15;
    cSample sample;
    cAlgorithm.cAlgorithmResult result;

    public cMyPanel(cSample _sample, cAlgorithm.cAlgorithmResult _result) {
        sample = _sample;
        result = _result;

        panelWidthPx = (int)(panelSizePx*sample.mapRatio);
        panelHeightPx = (int)(panelSizePx/sample.mapRatio);
        setPreferredSize(new Dimension(panelWidthPx+panelMargin*2, panelHeightPx+panelMargin*2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        System.out.println("Drawing: "+sample.name);
        List<Ellipse2D> points = new ArrayList<Ellipse2D>();
        List<Line2D> lines = new ArrayList<Line2D>();

        //drawing points
        for(cSample.cCoords cords: sample.coordList){
            points.add(new Ellipse2D.Double(panelMargin+panelWidthPx*cords.x/sample.mapWidth-pointDiameter/4, panelMargin+panelHeightPx*cords.y/sample.mapHeight-pointDiameter/4, pointDiameter/2, pointDiameter/2));
        }
        for(Ellipse2D elipse: points){
            g2d.fill(elipse);
        }

        //drawing path
        for(int linePoint: result.coordsOnPath){
            if(result.coordsOnPath.indexOf(linePoint) != 0){
                int previousIndexOnPanth = result.coordsOnPath.get(result.coordsOnPath.indexOf(linePoint)-1);
                double point1X = panelMargin+panelWidthPx*sample.coordList.get(previousIndexOnPanth).x/sample.mapWidth;
                double point1Y = panelMargin+panelHeightPx*sample.coordList.get(previousIndexOnPanth).y/sample.mapHeight;
                double point2X = panelMargin+panelWidthPx*sample.coordList.get(linePoint).x/sample.mapWidth;
                double point2Y = panelMargin+panelHeightPx*sample.coordList.get(linePoint).y/sample.mapHeight;
                lines.add(new Line2D.Double(point1X, point1Y, point2X, point2Y));
            }else {
                int previousIndexOnPanth = result.coordsOnPath.get(result.coordsOnPath.size()-1);
                double point1X = panelMargin+panelWidthPx*sample.coordList.get(previousIndexOnPanth).x/sample.mapWidth;
                double point1Y = panelMargin+panelHeightPx*sample.coordList.get(previousIndexOnPanth).y/sample.mapHeight;
                double point2X = panelMargin+panelWidthPx*sample.coordList.get(linePoint).x/sample.mapWidth;
                double point2Y = panelMargin+panelHeightPx*sample.coordList.get(linePoint).y/sample.mapHeight;
                lines.add(new Line2D.Double(point1X, point1Y, point2X, point2Y));
            }
        }
        for(Line2D line: lines){
            g2d.draw(line);
        }
    }
}