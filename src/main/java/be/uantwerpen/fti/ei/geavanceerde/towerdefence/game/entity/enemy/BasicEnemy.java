package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy;

import java.util.List;
import java.util.Optional;

/**
 * Ennemi de base — suit le chemin sans capacité spéciale.
 */
public class BasicEnemy extends Enemy {

    public BasicEnemy(double startX, double startY,
                      List<double[]> waypoints) {
        super(startX, startY, 100, 80, 10, waypoints);
        // health=100, speed=80, reward=10
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Basic Enemy — standard unit");
    }
}
