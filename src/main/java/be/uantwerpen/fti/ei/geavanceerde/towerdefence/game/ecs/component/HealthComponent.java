package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.component;

/**
 * Composant de santé — données pures, pas de logique.
 */
public class HealthComponent {
    public double hp;
    public double maxHp;

    public HealthComponent(double hp) {
        this.hp    = hp;
        this.maxHp = hp;
    }

    public boolean isDead() { return hp <= 0; }
}
