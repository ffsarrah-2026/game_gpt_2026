package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.Base;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy.Enemy;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.tower.Tower;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.factory.AbstractEntityFactory;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.util.Stopwatch;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.wave.WaveManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Singleton représentant l'état complet du jeu.
 * Aucun import java.awt — logique pure uniquement.
 */
public class Game {

    // ── Singleton ──────────────────────────────────
    private static Game instance;

    public static Game getInstance() {
        if (instance == null) instance = new Game();
        return instance;
    }

    private Game() {}

    // ── État ───────────────────────────────────────
    private AbstractEntityFactory factory;
    private final Stopwatch stopwatch  = new Stopwatch();
    private final List<Tower>  towers  = new ArrayList<>();
    private final List<Enemy>  enemies = new ArrayList<>();
    private Base        base;
    private WaveManager waveManager;
    private int         score;
    private int         resources;

    public enum State { RUNNING, GAME_OVER, VICTORY }
    private State state = State.RUNNING;

    // Waypoints du niveau 1 (coordonnées logiques)
    private static final List<double[]> DEFAULT_WAYPOINTS = List.of(
            new double[]{0,   300},
            new double[]{500, 300},
            new double[]{500, 500},
            new double[]{900, 500}
    );

    // ── Init ───────────────────────────────────────
    public void init(AbstractEntityFactory factory) {
        this.factory     = factory;
        this.score       = 0;
        this.resources   = 200;
        this.state       = State.RUNNING;
        this.base        = factory.createBase(900, 500);
        this.waveManager = new WaveManager(factory, DEFAULT_WAYPOINTS);
        stopwatch.reset();

        // Démarre la première vague automatiquement
        waveManager.startNextWave();
    }

    // ── Boucle principale ──────────────────────────
    public void tick() {
        if (state != State.RUNNING) return;

        double deltaTime = stopwatch.tick();

        // Spawn des ennemis depuis le WaveManager
        waveManager.update(deltaTime).forEach(this::spawnEnemy);

        // Mise à jour des entités
        towers.forEach(t -> t.update(deltaTime));
        enemies.forEach(e -> e.update(deltaTime));

        // Les tours cherchent et attaquent (Streams API)
        towers.forEach(tower ->
                tower.acquireTarget(enemies).ifPresent(tower::tryFire)
        );

        // Ennemis qui atteignent la base
        enemies.stream()
                .filter(Enemy::isAlive)
                .filter(Enemy::hasReachedBase)
                .forEach(e -> { base.takeDamage(1); e.destroy(); });

        // Récompenses + nettoyage
        enemies.stream()
                .filter(e -> !e.isAlive())
                .forEach(e -> {
                    score     += e.getReward();
                    resources += e.getReward() / 2;
                });
        enemies.removeIf(e -> !e.isAlive());

        // Vérification état
        if (!base.isAlive()) {
            state = State.GAME_OVER;
        } else if (waveManager.allWavesDone() && enemies.isEmpty()) {
            state = State.VICTORY;
        }
    }

    // ── Actions joueur ─────────────────────────────
    public boolean placeTower(String type, double x, double y) {
        int cost = type.equals("cannon") ? 100 : 75;
        if (resources < cost) return false;
        Tower t = type.equals("cannon")
                ? factory.createCannonTower(x, y)
                : factory.createRapidTower(x, y);
        towers.add(t);
        resources -= cost;
        return true;
    }

    public void spawnEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    // ── Getters ────────────────────────────────────
    public List<Tower>         getTowers()      { return List.copyOf(towers); }
    public List<Enemy>         getEnemies()     { return List.copyOf(enemies); }
    public Base                getBase()        { return base; }
    public int                 getScore()       { return score; }
    public int                 getResources()   { return resources; }
    public State               getState()       { return state; }

    // Retourne Optional<WaveManager> — jamais null
    public Optional<WaveManager> getWaveManager() {
        return Optional.ofNullable(waveManager);
    }
}