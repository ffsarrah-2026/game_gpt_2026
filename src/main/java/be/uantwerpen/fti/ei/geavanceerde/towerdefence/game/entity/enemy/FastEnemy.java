package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy;

import java.util.List;
import java.util.Optional;

/**
 * Ennemi rapide — peu de vie mais très rapide.
 */
public class FastEnemy extends Enemy {

    public FastEnemy(double startX, double startY,
                     List<double[]> waypoints) {
        super(startX, startY, 60, 180, 15, waypoints);
        // health=60, speed=180, reward=15
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Fast Enemy — speed x2, low health");
    }
}
