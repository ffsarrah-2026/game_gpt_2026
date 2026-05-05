package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.system;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.World;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.component.PathComponent;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.component.PositionComponent;

/**
 * Système ECS — déplace les ennemis le long de leur chemin.
 * Logique pure, aucun import java.awt.
 */
public class MovementSystem {

    private final World world;

    public MovementSystem(World world) {
        this.world = world;
    }

    public void update(double deltaTime) {
        // Toutes les entités avec Position ET Path
        world.getEntitiesWith(PathComponent.class).forEach(id -> {
            world.getComponent(id, PositionComponent.class).ifPresent(pos -> {
                world.getComponent(id, PathComponent.class).ifPresent(path -> {
                    if (path.hasReachedEnd()) return;

                    double[] target = path.currentTarget();
                    double dx   = target[0] - pos.x;
                    double dy   = target[1] - pos.y;
                    double dist = Math.sqrt(dx * dx + dy * dy);

                    // Vitesse fixe : 80 unités/sec (à externaliser en config)
                    double step = 80.0 * deltaTime;

                    if (dist <= step) {
                        pos.x = target[0];
                        pos.y = target[1];
                        path.currentIndex++;
                    } else {
                        pos.x += (dx / dist) * step;
                        pos.y += (dy / dist) * step;
                    }
                });
            });
        });
    }
}