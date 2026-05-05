package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.factory;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.Base;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.enemy.Enemy;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.entity.tower.Tower;

import java.util.List;

/**
 * Interface Abstract Factory.
 * Le package game utilise UNIQUEMENT cette interface —
 * il ne connaît jamais J2dEntityFactory.
 */
public interface AbstractEntityFactory {

    Tower createCannonTower(double x, double y);
    Tower createRapidTower(double x, double y);

    Enemy createBasicEnemy(double x, double y, List<double[]> waypoints);
    Enemy createArmoredEnemy(double x, double y, List<double[]> waypoints);
    Enemy createFastEnemy(double x, double y, List<double[]> waypoints);

    Base  createBase(double x, double y);
}
