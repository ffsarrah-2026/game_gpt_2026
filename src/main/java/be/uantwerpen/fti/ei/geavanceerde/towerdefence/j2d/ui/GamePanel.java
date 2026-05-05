package be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.ui;

import be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.Game;
import be.uantwerpen.fti.ei.geavanceerde.towerdefence.j2d.entity.*;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel principal — game loop + rendu Java2D.
 * Appelle game.tick() et repaint() à chaque frame.
 */
public class GamePanel extends JPanel implements Runnable {

    // Coordonnées logiques du monde de jeu
    private static final double WORLD_W = 1000.0;
    private static final double WORLD_H = 600.0;
    private static final int    TARGET_FPS = 60;

    private final Game         game;
    private final InputHandler input;
    private Thread             gameThread;

    public GamePanel(Game game) {
        this.game = game;

        setPreferredSize(new Dimension(1000, 600));
        setBackground(new Color(34, 85, 34));
        setFocusable(true);

        double sx = 1000.0 / WORLD_W;
        double sy = 600.0  / WORLD_H;

        this.input = new InputHandler(game, sx, sy);
        addKeyListener(input);
        addMouseListener(input);
    }

    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // ── Game loop ──────────────────────────────────
    @Override
    public void run() {
        long frameTime = 1_000_000_000L / TARGET_FPS; // en nanosecondes

        while (true) {
            long start = System.nanoTime();

            game.tick();
            repaint();

            long elapsed = System.nanoTime() - start;
            long sleep   = (frameTime - elapsed) / 1_000_000; // en ms
            if (sleep > 0) {
                try { Thread.sleep(sleep); }
                catch (InterruptedException ignored) {}
            }
        }
    }

    // ── Rendu ──────────────────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Antialiasing pour un rendu plus propre
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        double sx = getWidth()  / WORLD_W;
        double sy = getHeight() / WORLD_H;

        // Met à jour le scale si la fenêtre est redimensionnée
        input.updateScale(sx, sy);

        // Dessine le chemin des ennemis
        drawPath(g2d, sx, sy);

        // Dessine les tours
        game.getTowers().forEach(t -> {
            if (t instanceof J2dCannonTower jt) jt.render(g2d, sx, sy);
            if (t instanceof J2dRapidTower  jt) jt.render(g2d, sx, sy);
        });

        // Dessine les ennemis
        game.getEnemies().forEach(e -> {
            if (e instanceof J2dBasicEnemy   je) je.render(g2d, sx, sy);
            if (e instanceof J2dArmoredEnemy je) je.render(g2d, sx, sy);
            if (e instanceof J2dFastEnemy    je) je.render(g2d, sx, sy);
        });

        // Dessine la base
        if (game.getBase() instanceof J2dBase jb) jb.render(g2d, sx, sy);

        // HUD
        drawHUD(g2d);

        // Écrans de fin
        if (game.getState() == Game.State.GAME_OVER) drawGameOver(g2d);
        if (game.getState() == Game.State.VICTORY)   drawVictory(g2d);
    }

    private void drawPath(Graphics2D g, double sx, double sy) {
        // Chemin simplifié — à adapter selon ton niveau
        g.setColor(new Color(180, 140, 80));
        g.setStroke(new BasicStroke(32));
        // Exemple de chemin — remplacer par les waypoints du niveau
        g.drawLine(0, (int)(300*sy), (int)(500*sx), (int)(300*sy));
        g.drawLine((int)(500*sx), (int)(300*sy), (int)(500*sx), (int)(500*sy));
        g.drawLine((int)(500*sx), (int)(500*sy), (int)(900*sx), (int)(500*sy));
        g.setStroke(new BasicStroke(1));
    }

    private void drawHUD(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), 40);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: "     + game.getScore(),     10,  26);
        g.drawString("Resources: " + game.getResources(), 160, 26);
        g.drawString("Base HP: "   + game.getBase().getLifePoints(), 340, 26);
        g.drawString("[1] Cannon  [2] Rapid  [N] Next Wave", 520, 26);
    }

    private void drawGameOver(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 64));
        g.drawString("GAME OVER", getWidth()/2 - 180, getHeight()/2);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Score final : " + game.getScore(),
                getWidth()/2 - 80, getHeight()/2 + 50);
    }

    private void drawVictory(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 64));
        g.drawString("VICTORY!", getWidth()/2 - 150, getHeight()/2);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Score final : " + game.getScore(),
                getWidth()/2 - 80, getHeight()/2 + 50);
    }
}