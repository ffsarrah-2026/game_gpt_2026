package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.component;

import java.util.List;

/**
 * Composant de chemin pour les ennemis — données pures.
 */
public class PathComponent {
    public final List<double[]> waypoints;
    public int currentIndex;

    public PathComponent(List<double[]> waypoints) {
        this.waypoints    = waypoints;
        this.currentIndex = 0;
    }

    public boolean hasReachedEnd() {
        return currentIndex >= waypoints.size();
    }

    public double[] currentTarget() {
        return waypoints.get(currentIndex);
    }
}