package be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.entity;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.tower.CannonTower;

import java.awt.*;

/**
 * CannonTower avec rendu Java2D.
 * Hérite de CannonTower (logique) et ajoute l'affichage.
 */
public class J2dCannonTower extends CannonTower {

    private static final Color COLOR_BODY  = new Color(80, 80, 80);
    private static final Color COLOR_RANGE = new Color(255, 255, 0, 40);

    public J2dCannonTower(double x, double y) {
        super(x, y);
    }

    /**
     * Dessine la tour sur le Graphics2D.
     * @param g   le contexte graphique
     * @param scaleX facteur de conversion coordonnées logiques → pixels X
     * @param scaleY facteur de conversion coordonnées logiques → pixels Y
     */
    public void render(Graphics2D g, double scaleX, double scaleY) {
        int px = (int)(x * scaleX);
        int py = (int)(y * scaleY);
        int size = 32;

        // Cercle de portée (transparent)
        g.setColor(COLOR_RANGE);
        int rangeR = (int)(range * scaleX);
        g.fillOval(px - rangeR, py - rangeR, rangeR * 2, rangeR * 2);

        // Corps de la tour
        g.setColor(COLOR_BODY);
        g.fillRect(px - size/2, py - size/2, size, size);

        // Canon
        g.setColor(Color.DARK_GRAY);
        g.fillRect(px, py - 5, size/2, 10);

        // Contour
        g.setColor(Color.BLACK);
        g.drawRect(px - size/2, py - size/2, size, size);
    }
}
