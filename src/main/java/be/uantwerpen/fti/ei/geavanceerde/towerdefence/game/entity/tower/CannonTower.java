package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.tower;

import java.util.Optional;

/**
 * Tour canon — tir lent mais très puissant.
 */
public class CannonTower extends Tower {

    public CannonTower(double x, double y) {
        super(x, y, 150, 50, 0.8);
        // range=150, damage=50, fireRate=0.8 tirs/sec
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Cannon Tower — slow, high damage");
    }
}