package Pong;

import javax.swing.*;
import java.awt.event.*;

public class ControllerPong implements KeyListener, ActionListener {
    private final Pong pong; // Le modèle de jeu Pong
    private final PongView view; // La vue du jeu Pong
    private final Timer timer; // Timer pour le mouvement de la balle
    private boolean isPaused; // Indicateur de pause // Le modèle de jeu Pong

    public ControllerPong(Pong pong, PongView pongView) {
        this.pong = pong;
        this.view = pongView; // Stocker la vue dans le contrôleur
        this.isPaused = false; // Le jeu démarre sans pause
        pong.addObserver(view);
        view.getFrame().addKeyListener(this);
        this.timer = new Timer(10, this); // Lancement du timer
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isPaused) {
            pong.moveBall(); // Mise à jour de la balle uniquement si le jeu n'est pas en pause
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) pong.moveLeft();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) pong.moveRight();

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            togglePause(); // Gère l'état de pause
        }
    }

    private void togglePause() {
        isPaused = !isPaused; // Inverse l'état de pause
        if (isPaused) {
            timer.stop(); // Stoppe le timer
            view.displayPauseMessage("Game Paused! Press SPACE to resume.");
        } else {
            timer.start(); // Redémarre le timer
            view.clearPauseMessage(); // Efface le message de pause
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
