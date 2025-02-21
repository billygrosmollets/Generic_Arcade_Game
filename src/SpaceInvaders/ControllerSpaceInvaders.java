package SpaceInvaders;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Timer;
import java.util.List;

public class ControllerSpaceInvaders implements KeyListener {
    private final SpaceInvaders game;
    private final SpaceInvadersView view;
    private final Map<Integer, Runnable> keyActions = new HashMap<>();
    private final Timer gameUpdateTimer;
    private final Timer enemyShootTimer;

    // Stocker les états des touches
    private final Map<Integer, Boolean> keyStates = new HashMap<>();

    public ControllerSpaceInvaders(SpaceInvaders game, SpaceInvadersView view) {
        this.game = game;
        this.view = view;

        // Lier la vue au KeyListener
        view.getFrame().addKeyListener(this);

        // Configurer les actions associées aux touches
        configureKeyActions();

        // Timer pour mettre à jour le jeu
        gameUpdateTimer = new Timer(10, _ ->
        {
            handleKeys();
            game.updateGame();
        });

        // Timer pour les tirs ennemis
        enemyShootTimer = new Timer(1000, _ -> game.enemyShootProjectile());

        // Démarrer les timers
        gameUpdateTimer.start();
        enemyShootTimer.start();

        // Enregistrer la vue comme observateur du modèle
        game.addObserver(new SpaceInvadersObserver() {
            @Override
            public void onSpaceInvadersUpdated(EntitySpaceInvaders player, List<EntitySpaceInvaders> enemies, List<EntitySpaceInvaders> projectiles, int score, int wave, int lifeLeft) {
                view.onSpaceInvadersUpdated(player, enemies, projectiles, score, wave, lifeLeft);
            }

            @Override
            public void onGameOver(String message) {
                reset();
            }
        });

    }

    private void configureKeyActions() {
        keyActions.put(KeyEvent.VK_LEFT, game::movePlayerLeft);
        keyActions.put(KeyEvent.VK_RIGHT, game::movePlayerRight);
        keyActions.put(KeyEvent.VK_SPACE, game::shootProjectile);
    }

    private void handleKeys() {
        keyActions.forEach((key, action) ->
        {
            if (keyStates.getOrDefault(key, false))
            {
                action.run();
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyStates.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyStates.put(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Non utilisé
    }

    public void reset() {
        stopTimers();
        keyStates.clear();
        game.resetGame();
        view.resetView();

        // Réattacher le KeyListener
        view.getFrame().removeKeyListener(this);
        view.getFrame().addKeyListener(this);

        // Assurer le focus sur la fenêtre
        view.getFrame().requestFocus();

        // Redémarrer les timers
        gameUpdateTimer.start();
        enemyShootTimer.start();
    }

    public void stopTimers() {
        if (gameUpdateTimer.isRunning())
        {
            gameUpdateTimer.stop();
        }
        if (enemyShootTimer.isRunning())
        {
            enemyShootTimer.stop();
        }
    }
}
