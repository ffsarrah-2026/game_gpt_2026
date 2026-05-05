package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity;

import java.util.Optional;

/**
 * Classe abstraite racine de toutes les entités du jeu.
 * Coordonnées en Double pour indépendance vis-à-vis de la résolution écran.
 */

public abstract class Entity {

    private static int nextId = 0;

    private final int id;
    protected double x;
    protected double y;
    private boolean alive;

    protected Entity(double x, double y) {
        this.id    = nextId++;
        this.x     = x;
        this.y     = y;
        this.alive = true;
    }

    public int getId()       { return id; }
    public double getX()     { return x; }
    public double getY()     { return y; }
    public boolean isAlive() { return alive; }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Marque l'entité comme détruite — jamais null, on utilise ce flag. */
    public void destroy() { this.alive = false; }

    /**
     * Retourne une description optionnelle.
     * Exemple d'utilisation d'Optional pour éviter null.
     */
    public Optional<String> getDescription() {
        return Optional.empty();
    }

    /** Mise à jour logique à chaque tick. */
    public abstract void update(double deltaTime);
}