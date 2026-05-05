package be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.ui;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.Game;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.factory.J2dEntityFactory;

import javax.swing.*;

/**
 * Point d'entrée du jeu.
 * Initialise le Game avec la factory J2D et lance la fenêtre.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialise le singleton Game avec la factory J2D
            Game game = Game.getInstance();
            game.init(new J2dEntityFactory());

            // Crée la fenêtre
            JFrame frame = new JFrame("Tower Defence 2025-2026");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            GamePanel panel = new GamePanel(game);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Lance la game loop
            panel.startGameLoop();
        });
    }
}