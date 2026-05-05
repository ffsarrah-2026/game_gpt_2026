package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.system;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.World;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.component.AttackComponent;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.component.HealthComponent;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.component.PositionComponent;

import java.util.List;
import java.util.Optional;

/**
 * Système ECS — fait tirer les tours sur l'ennemi le plus proche.
 * Logique pure, aucun import java.awt.
 */
public class TowerAttackSystem {

    private final World world;

    public TowerAttackSystem(World world) {
        this.world = world;
    }

    public void update(double deltaTime) {
        List<Integer> enemies = world.getEntitiesWith(HealthComponent.class);

        // Pour chaque tour
        world.getEntitiesWith(AttackComponent.class).forEach(towerId -> {
            world.getComponent(towerId, AttackComponent.class).ifPresent(atk -> {
                world.getComponent(towerId, PositionComponent.class).ifPresent(towerPos -> {

                    // Réduit le cooldown
                    if (atk.cooldown > 0) {
                        atk.cooldown -= deltaTime;
                        return;
                    }

                    // Cherche l'ennemi le plus proche dans la portée (Streams)
                    Optional<Integer> target = enemies.stream()
                            .filter(eid -> world.hasComponent(eid, PositionComponent.class))
                            .filter(eid -> {
                                PositionComponent ep = world
                                        .getComponent(eid, PositionComponent.class)
                                        .orElseThrow();
                                double dx = ep.x - towerPos.x;
                                double dy = ep.y - towerPos.y;
                                return Math.sqrt(dx*dx + dy*dy) <= atk.range;
                            })
                            .min((a, b) -> {
                                PositionComponent pa = world.getComponent(a, PositionComponent.class).orElseThrow();
                                PositionComponent pb = world.getComponent(b, PositionComponent.class).orElseThrow();
                                double da = Math.hypot(pa.x - towerPos.x, pa.y - towerPos.y);
                                double db = Math.hypot(pb.x - towerPos.x, pb.y - towerPos.y);
                                return Double.compare(da, db);
                            });

                    // Applique les dégâts si cible trouvée
                    target.ifPresent(eid ->
                            world.getComponent(eid, HealthComponent.class).ifPresent(hp -> {
                                hp.hp -= atk.damage;
                                atk.cooldown = 1.0 / atk.fireRate;
                            })
                    );
                });
            });
        });
    }
}