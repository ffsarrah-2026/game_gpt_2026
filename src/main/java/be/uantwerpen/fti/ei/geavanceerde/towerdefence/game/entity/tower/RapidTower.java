package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.tower;

import java.util.Optional;

/**
 * Tour rapide — tir rapide mais faibles dégâts.
 */
public class RapidTower extends Tower {

    public RapidTower(double x, double y) {
        super(x, y, 100, 15, 3.0);
        // range=100, damage=15, fireRate=3 tirs/sec
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Rapid Tower — fast, low damage");
    }
}
