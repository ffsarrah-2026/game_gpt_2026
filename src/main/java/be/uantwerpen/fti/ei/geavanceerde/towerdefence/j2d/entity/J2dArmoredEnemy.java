package be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.entity;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy.ArmoredEnemy;

import java.awt.*;
import java.util.List;

/**
 * ArmoredEnemy avec rendu Java2D — gris foncé avec armure visible.
 */
public class J2dArmoredEnemy extends ArmoredEnemy {

    private static final Color COLOR_BODY  = new Color(100, 100, 100);
    private static final Color COLOR_ARMOR = new Color(180, 180, 180);

    public J2dArmoredEnemy(double x, double y, List<double[]> waypoints) {
        super(x, y, waypoints);
    }

    public void render(Graphics2D g, double scaleX, double scaleY) {
        if (!isAlive()) return;

        int px   = (int)(x * scaleX);
        int py   = (int)(y * scaleY);
        int size = 30;

        // Corps
        g.setColor(COLOR_BODY);
        g.fillRect(px - size/2, py - size/2, size, size);

        // Armure (losange par-dessus)
        g.setColor(COLOR_ARMOR);
        int[] xs = { px, px + size/2, px, px - size/2 };
        int[] ys = { py - size/2, py, py + size/2, py };
        g.fillPolygon(xs, ys, 4);

        g.setColor(Color.BLACK);
        g.drawRect(px - size/2, py - size/2, size, size);

        // Barre de vie
        int barW  = size + 8;
        int barH  = 4;
        int barX  = px - barW / 2;
        int barY  = py - size / 2 - 8;
        g.setColor(Color.RED);
        g.fillRect(barX, barY, barW, barH);
        g.setColor(Color.GREEN);
        g.fillRect(barX, barY, (int)(barW * (health / maxHealth)), barH);
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barW, barH);
    }
}
