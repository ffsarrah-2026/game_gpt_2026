package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity;

/**
 * Un projectile tiré par une tour.
 * Se déplace vers une cible à vitesse constante.
 */
public class Projectile extends Entity {

    private final double damage;
    private final double speed;
    private final double targetX;
    private final double targetY;

    public Projectile(double x, double y,
                      double targetX, double targetY,
                      double speed, double damage) {
        super(x, y);
        this.targetX = targetX;
        this.targetY = targetY;
        this.speed   = speed;
        this.damage  = damage;
    }

    @Override
    public void update(double deltaTime) {
        if (!isAlive()) return;

        double dx   = targetX - x;
        double dy   = targetY - y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        double step = speed * deltaTime;

        if (dist <= step) {
            // Projectile a atteint la cible
            x = targetX;
            y = targetY;
            destroy();
        } else {
            x += (dx / dist) * step;
            y += (dy / dist) * step;
        }
    }

    public double getDamage() { return damage; }
}