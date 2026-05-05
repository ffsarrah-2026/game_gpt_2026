package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity;

/**
 * La base du joueur.
 * Si ses points de vie tombent à 0, la partie est perdue.
 */
public class Base extends Entity {

    private int lifePoints;
    private final int maxLifePoints;

    public Base(double x, double y, int lifePoints) {
        super(x, y);
        this.lifePoints    = lifePoints;
        this.maxLifePoints = lifePoints;
    }

    /** Un ennemi a atteint la base — réduit les points de vie. */
    public void takeDamage(int amount) {
        lifePoints -= amount;
        if (lifePoints <= 0) {
            lifePoints = 0;
            destroy();
        }
    }

    @Override
    public void update(double deltaTime) {
        // La base ne bouge pas — rien à mettre à jour
    }

    public int getLifePoints()    { return lifePoints; }
    public int getMaxLifePoints() { return maxLifePoints; }
}