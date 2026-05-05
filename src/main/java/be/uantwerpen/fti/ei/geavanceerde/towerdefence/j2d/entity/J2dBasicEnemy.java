package be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.entity;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy.BasicEnemy;

import java.awt.*;
import java.util.List;

/**
 * BasicEnemy avec rendu Java2D.
 */
public class J2dBasicEnemy extends BasicEnemy {

    private static final Color COLOR = new Color(220, 50, 50);

    public J2dBasicEnemy(double x, double y, List<double[]> waypoints) {
        super(x, y, waypoints);
    }

    public void render(Graphics2D g, double scaleX, double scaleY) {
        if (!isAlive()) return;

        int px   = (int)(x * scaleX);
        int py   = (int)(y * scaleY);
        int size = 24;

        // Corps
        g.setColor(COLOR);
        g.fillOval(px - size/2, py - size/2, size, size);
        g.setColor(Color.BLACK);
        g.drawOval(px - size/2, py - size/2, size, size);

        // Barre de vie
        renderHealthBar(g, px, py, size);
    }

    protected void renderHealthBar(Graphics2D g, int px, int py, int size) {
        int barW = size + 8;
        int barH = 4;
        int barX = px - barW / 2;
        int barY = py - size / 2 - 8;

        // Fond rouge
        g.setColor(Color.RED);
        g.fillRect(barX, barY, barW, barH);

        // Vie actuelle en vert
        g.setColor(Color.GREEN);
        int filled = (int)(barW * (health / maxHealth));
        g.fillRect(barX, barY, filled, barH);

        // Contour
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barW, barH);
    }
}
