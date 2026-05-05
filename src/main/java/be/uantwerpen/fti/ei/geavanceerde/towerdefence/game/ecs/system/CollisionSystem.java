package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.system;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.World;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.component.HealthComponent;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.component.PathComponent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Système ECS — supprime les entités mortes ou arrivées à la base.
 * Retourne les IDs des ennemis ayant atteint la base.
 */
public class CollisionSystem {

    private final World world;

    public CollisionSystem(World world) {
        this.world = world;
    }

    /**
     * Retourne les IDs des ennemis morts (hp <= 0).
     * Le Game récupère leurs récompenses avant de les supprimer.
     */
    public List<Integer> getDeadEnemies() {
        return world.getEntitiesWith(HealthComponent.class).stream()
                .filter(id -> world.getComponent(id, HealthComponent.class)
                        .map(HealthComponent::isDead)
                        .orElse(false))
                .collect(Collectors.toList());
    }

    /**
     * Retourne les IDs des ennemis ayant atteint la base.
     */
    public List<Integer> getEnemiesAtBase() {
        return world.getEntitiesWith(PathComponent.class).stream()
                .filter(id -> world.getComponent(id, PathComponent.class)
                        .map(PathComponent::hasReachedEnd)
                        .orElse(false))
                .collect(Collectors.toList());
    }

    /** Supprime une liste d'entités du World. */
    public void cleanup(List<Integer> ids) {
        ids.forEach(world::destroyEntity);
    }
}
