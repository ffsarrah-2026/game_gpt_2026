package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.tower;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.Entity;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy.Enemy;

import java.util.List;
import java.util.Optional;

/**
 * Classe abstraite représentant une tour défensive.
 * Aucun appel à java.awt ici.
 */
public abstract class Tower extends Entity {

    protected double range;
    protected double damage;
    protected double fireRate;   // tirs par seconde
    private   double cooldown;   // temps restant avant prochain tir

    protected Tower(double x, double y,
                    double range, double damage, double fireRate) {
        super(x, y);
        this.range    = range;
        this.damage   = damage;
        this.fireRate = fireRate;
        this.cooldown = 0;
    }

    @Override
    public void update(double deltaTime) {
        if (cooldown > 0) cooldown -= deltaTime;
    }

    /**
     * Cherche la cible la plus proche dans la portée.
     * Utilisation de Java Streams API — retourne Optional, jamais null.
     */
    public Optional<Enemy> acquireTarget(List<Enemy> enemies) {
        return enemies.stream()
                .filter(Enemy::isAlive)
                .filter(e -> distanceTo(e) <= range)
                .min((a, b) -> Double.compare(distanceTo(a), distanceTo(b)));
    }

    /** Tente de tirer — retourne true si tir effectué. */
    public boolean tryFire(Enemy target) {
        if (cooldown > 0) return false;
        cooldown = 1.0 / fireRate;
        applyDamage(target);
        return true;
    }

    protected void applyDamage(Enemy target) {
        target.takeDamage(damage);
    }

    private double distanceTo(Entity other) {
        double dx = other.getX() - this.x;
        double dy = other.getY() - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getRange()    { return range; }
    public double getDamage()   { return damage; }
    public double getFireRate() { return fireRate; }
}
