package be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.entity;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy.FastEnemy;

import java.awt.*;
import java.util.List;

/**
 * FastEnemy avec rendu Java2D — jaune vif et petit.
 */
public class J2dFastEnemy extends FastEnemy {

    private static final Color COLOR = new Color(255, 220, 0);

    public J2dFastEnemy(double x, double y, List<double[]> waypoints) {
        super(x, y, waypoints);
    }

    public void render(Graphics2D g, double scaleX, double scaleY) {
        if (!isAlive()) return;

        int px   = (int)(x * scaleX);
        int py   = (int)(y * scaleY);
        int size = 18;

        // Corps triangulaire (rapide = pointu)
        int[] xs = { px, px - size/2, px + size/2 };
        int[] ys = { py - size/2, py + size/2, py + size/2 };
        g.setColor(COLOR);
        g.fillPolygon(xs, ys, 3);
        g.setColor(Color.BLACK);
        g.drawPolygon(xs, ys, 3);

        // Barre de vie
        int barW = size + 8;
        int barH = 4;
        int barX = px - barW / 2;
        int barY = py - size / 2 - 8;
        g.setColor(Color.RED);
        g.fillRect(barX, barY, barW, barH);
        g.setColor(Color.GREEN);
        g.fillRect(barX, barY, (int)(barW * (health / maxHealth)), barH);
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barW, barH);
    }
}