package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy;

import java.util.List;
import java.util.Optional;

/**
 * Ennemi blindé — beaucoup de vie, lent, résistant.
 */
public class ArmoredEnemy extends Enemy {

    private final double damageReduction; // ex: 0.5 = réduit dégâts de 50%

    public ArmoredEnemy(double startX, double startY,
                        List<double[]> waypoints) {
        super(startX, startY, 300, 40, 25, waypoints);
        // health=300, speed=40, reward=25
        this.damageReduction = 0.5;
    }

    @Override
    public void takeDamage(double amount) {
        // Réduit les dégâts reçus
        super.takeDamage(amount * (1.0 - damageReduction));
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Armored Enemy — 50% damage reduction");
    }
}
