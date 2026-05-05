package be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.factory;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.Base;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy.Enemy;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.tower.Tower;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.factory.AbstractEntityFactory;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.entity.*;

import java.util.List;

/**
 * Implémentation concrète de l'Abstract Factory pour Java2D.
 * Crée des entités qui savent se dessiner avec Graphics2D.
 */
public class J2dEntityFactory implements AbstractEntityFactory {

    @Override
    public Tower createCannonTower(double x, double y) {
        return new J2dCannonTower(x, y);
    }

    @Override
    public Tower createRapidTower(double x, double y) {
        return new J2dRapidTower(x, y);
    }

    @Override
    public Enemy createBasicEnemy(double x, double y, List<double[]> waypoints) {
        return new J2dBasicEnemy(x, y, waypoints);
    }

    @Override
    public Enemy createArmoredEnemy(double x, double y, List<double[]> waypoints) {
        return new J2dArmoredEnemy(x, y, waypoints);
    }

    @Override
    public Enemy createFastEnemy(double x, double y, List<double[]> waypoints) {
        return new J2dFastEnemy(x, y, waypoints);
    }

    @Override
    public Base createBase(double x, double y) {
        return new J2dBase(x, y);
    }
}