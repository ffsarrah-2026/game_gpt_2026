package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.Entity;

import java.util.List;

/**
 * Classe abstraite représentant un ennemi.
 * Se déplace le long d'une liste de waypoints vers la base.
 */
public abstract class Enemy extends Entity {

    protected double health;
    protected double maxHealth;
    protected double speed;
    protected int    reward;

    private final List<double[]> waypoints;
    private int waypointIndex;

    protected Enemy(double startX, double startY,
                    double health, double speed, int reward,
                    List<double[]> waypoints) {
        super(startX, startY);
        this.health        = health;
        this.maxHealth     = health;
        this.speed         = speed;
        this.reward        = reward;
        this.waypoints     = waypoints;
        this.waypointIndex = 0;
    }

    @Override
    public void update(double deltaTime) {
        if (!isAlive() || waypointIndex >= waypoints.size()) return;

        double[] target = waypoints.get(waypointIndex);
        double tx = target[0], ty = target[1];
        double dx = tx - x,    dy = ty - y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        double step = speed * deltaTime;

        if (dist <= step) {
            x = tx; y = ty;
            waypointIndex++;
        } else {
            x += (dx / dist) * step;
            y += (dy / dist) * step;
        }
    }

    /** Inflige des dégâts à l'ennemi. */
    public void takeDamage(double amount) {
        health -= amount;
        if (health <= 0) destroy();
    }

    /** Retourne true si l'ennemi a atteint le dernier waypoint. */
    public boolean hasReachedBase() {
        return waypointIndex >= waypoints.size();
    }

    public double getHealth()    { return health; }
    public double getMaxHealth() { return maxHealth; }
    public int    getReward()    { return reward; }
    public double getSpeed()     { return speed; }
}
