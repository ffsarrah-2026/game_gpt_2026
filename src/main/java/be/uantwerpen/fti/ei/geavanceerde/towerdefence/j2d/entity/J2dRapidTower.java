package be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.entity;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.tower.RapidTower;

import java.awt.*;

/**
 * RapidTower avec rendu Java2D.
 */
public class J2dRapidTower extends RapidTower {

    private static final Color COLOR_BODY  = new Color(30, 144, 255);
    private static final Color COLOR_RANGE = new Color(30, 144, 255, 40);

    public J2dRapidTower(double x, double y) {
        super(x, y);
    }

    public void render(Graphics2D g, double scaleX, double scaleY) {
        int px = (int)(x * scaleX);
        int py = (int)(y * scaleY);
        int size = 28;

        // Cercle de portée
        g.setColor(COLOR_RANGE);
        int rangeR = (int)(range * scaleX);
        g.fillOval(px - rangeR, py - rangeR, rangeR * 2, rangeR * 2);

        // Corps circulaire
        g.setColor(COLOR_BODY);
        g.fillOval(px - size/2, py - size/2, size, size);

        // Canon fin
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(3));
        g.drawLine(px, py, px + size/2, py);
        g.setStroke(new BasicStroke(1));

        // Contour
        g.setColor(Color.BLACK);
        g.drawOval(px - size/2, py - size/2, size, size);
    }
}