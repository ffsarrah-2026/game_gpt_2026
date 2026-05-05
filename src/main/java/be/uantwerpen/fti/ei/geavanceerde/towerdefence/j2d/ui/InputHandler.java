package be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.ui;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.Game;

import java.awt.event.*;

/**
 * Gère les inputs clavier et souris.
 * Translate les actions utilisateur en appels au Game.
 */
public class InputHandler extends MouseAdapter implements KeyListener {

    private final Game game;
    private String selectedTower = "cannon"; // tour sélectionnée par défaut
    private double scaleX;
    private double scaleY;

    public InputHandler(Game game, double scaleX, double scaleY) {
        this.game   = game;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    // ── Clavier ────────────────────────────────────
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_1 -> selectedTower = "cannon";
            case KeyEvent.VK_2 -> selectedTower = "rapid";
            case KeyEvent.VK_N -> game.getWaveManager()
                    .ifPresent(wm -> {
                        if (!wm.isWaveInProgress())
                            wm.startNextWave();
                    });
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e)    {}

    // ── Souris ─────────────────────────────────────
    @Override
    public void mouseClicked(MouseEvent e) {
        // Convertit les coordonnées pixels → coordonnées logiques
        double logX = e.getX() / scaleX;
        double logY = e.getY() / scaleY;
        game.placeTower(selectedTower, logX, logY);
    }

    public String getSelectedTower() { return selectedTower; }

    public void updateScale(double scaleX, double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
}