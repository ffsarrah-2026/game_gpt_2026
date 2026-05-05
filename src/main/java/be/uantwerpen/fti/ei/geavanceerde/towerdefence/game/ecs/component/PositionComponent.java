package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.component;

/**
 * Composant de position — données pures, pas de logique.
 */
public class PositionComponent {
    public double x;
    public double y;

    public PositionComponent(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
