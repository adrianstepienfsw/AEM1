import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class cMyPanel extends JPanel {
    public cMyPanel(cSample _sample) {
        sample = _sample;

        panelWidthPx = (int)(panelSizePx*sample.mapRatio);
        panelHeightPx = (int)(panelSizePx/sample.mapRatio);
        setPreferredSize(new Dimension(panelWidthPx+panelMargin*2, panelHeightPx+panelMargin*2));
    }

    final int panelSizePx = 500;
    int panelWidthPx = 0;
    int panelHeightPx = 0;
    final int panelMargin = 10;

    int pointDiameter = 10;
    cSample sample;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        System.out.println("Drawing: "+sample.name);
        List<Ellipse2D> points = new ArrayList<Ellipse2D>();
        for(cSample.cCoords cords: sample.coordList){
            points.add(new Ellipse2D.Double(panelMargin+panelWidthPx*cords.x/sample.mapWidth, panelMargin+panelHeightPx*cords.y/sample.mapHeight, pointDiameter/2, pointDiameter/2));
        }
        for(Ellipse2D elipse: points){
            g2d.fill(elipse);
        }
    }
}