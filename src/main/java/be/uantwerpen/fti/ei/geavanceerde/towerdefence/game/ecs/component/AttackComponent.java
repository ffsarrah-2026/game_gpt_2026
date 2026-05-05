package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs.component;

/**
 * Composant d'attaque pour les tours — données pures.
 */
public class AttackComponent {
    public double range;
    public double damage;
    public double fireRate;   // tirs par seconde
    public double cooldown;   // temps restant avant prochain tir

    public AttackComponent(double range, double damage, double fireRate) {
        this.range    = range;
        this.damage   = damage;
        this.fireRate = fireRate;
        this.cooldown = 0;
    }
}
