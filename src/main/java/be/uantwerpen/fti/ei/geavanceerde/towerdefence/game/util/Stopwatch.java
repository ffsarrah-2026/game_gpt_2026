package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.util;

/**
 * Calcule le deltaTime entre deux ticks.
 * Garantit une vitesse de jeu constante quelle que soit la machine.
 */
public class Stopwatch {

    private long lastTime;

    public Stopwatch() {
        lastTime = System.currentTimeMillis();
    }

    /**
     * Retourne le temps écoulé en secondes depuis le dernier appel.
     * À appeler une fois par frame au début de la boucle.
     */
    public double tick() {
        long now      = System.currentTimeMillis();
        double delta  = (now - lastTime) / 1000.0;
        lastTime      = now;
        return delta;
    }

    public void reset() {
        lastTime = System.currentTimeMillis();
    }
}