package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.wave;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy.Enemy;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.factory.AbstractEntityFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Gère les vagues d'ennemis.
 * Chaque vague est plus difficile que la précédente.
 */
public class WaveManager {

    private final AbstractEntityFactory factory;
    private final List<double[]> waypoints;

    private int    currentWave   = 0;
    private int    totalWaves    = 5;
    private double spawnTimer    = 0;
    private double spawnInterval = 1.5; // secondes entre chaque spawn

    private final Queue<String> spawnQueue = new LinkedList<>();
    private boolean waveInProgress = false;

    public WaveManager(AbstractEntityFactory factory, List<double[]> waypoints) {
        this.factory   = factory;
        this.waypoints = waypoints;
    }

    /**
     * Prépare la prochaine vague.
     * La difficulté augmente à chaque vague.
     */
    public void startNextWave() {
        currentWave++;
        waveInProgress = true;
        spawnQueue.clear();

        // Génère la liste d'ennemis selon la vague
        for (int i = 0; i < currentWave * 3; i++) {
            if (currentWave >= 4 && i % 3 == 0) {
                spawnQueue.add("armored");
            } else if (currentWave >= 2 && i % 4 == 0) {
                spawnQueue.add("fast");
            } else {
                spawnQueue.add("basic");
            }
        }
    }

    /**
     * Appelé à chaque tick — spawn les ennemis de la file.
     * Retourne les ennemis à ajouter au jeu.
     */
    public List<Enemy> update(double deltaTime) {
        List<Enemy> toSpawn = new ArrayList<>();
        if (!waveInProgress || spawnQueue.isEmpty()) return toSpawn;

        spawnTimer -= deltaTime;
        if (spawnTimer <= 0) {
            spawnTimer = spawnInterval;
            String type = spawnQueue.poll();

            double startX = waypoints.get(0)[0];
            double startY = waypoints.get(0)[1];

            Enemy e = switch (type) {
                case "armored" -> factory.createArmoredEnemy(startX, startY, waypoints);
                case "fast"    -> factory.createFastEnemy(startX, startY, waypoints);
                default        -> factory.createBasicEnemy(startX, startY, waypoints);
            };
            toSpawn.add(e);

            if (spawnQueue.isEmpty()) waveInProgress = false;
        }
        return toSpawn;
    }

    public boolean isWaveInProgress()  { return waveInProgress; }
    public boolean allWavesDone()      { return currentWave >= totalWaves && !waveInProgress; }
    public int     getCurrentWave()    { return currentWave; }
    public int     getTotalWaves()     { return totalWaves; }
}
