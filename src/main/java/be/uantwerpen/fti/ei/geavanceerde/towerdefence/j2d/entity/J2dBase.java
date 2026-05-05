package be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.entity;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.Base;

import java.awt.*;

/**
 * Base du joueur avec rendu Java2D.
 */
public class J2dBase extends Base {

    public J2dBase(double x, double y) {
        super(x, y, 20);
    }

    public void render(Graphics2D g, double scaleX, double scaleY) {
        int px   = (int)(x * scaleX);
        int py   = (int)(y * scaleY);
        int size = 48;

        // Corps de la base
        g.setColor(new Color(0, 180, 100));
        g.fillRect(px - size/2, py - size/2, size, size);

        // Croix au centre
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(4));
        g.drawLine(px - size/4, py, px + size/4, py);
        g.drawLine(px, py - size/4, px, py + size/4);
        g.setStroke(new BasicStroke(1));

        // Contour
        g.setColor(Color.BLACK);
        g.drawRect(px - size/2, py - size/2, size, size);

        // Points de vie
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("HP: " + getLifePoints(), px - 16, py + size/2 + 16);
    }
}