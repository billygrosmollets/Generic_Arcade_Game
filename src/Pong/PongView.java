package Pong;

import javax.swing.*;
import java.awt.*;

public class PongView implements PongObserver {
    private final JFrame frame = new JFrame("Pong Game");
    private final JPanel panel = new JPanel();
    private final JLabel ballLabel = new JLabel();
    private final JLabel paddleLabel = new JLabel();
    private final JLabel scoreLabel = new JLabel("Score: 0");
    private final JLabel pauseLabel = new JLabel(); // Nouveau label pour le message de pause

    public PongView() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Centrer la fenêtre à l'écran

        // Configuration du panneau principal du jeu
        panel.setLayout(null);  // Utilisation d'un layout nul pour positionner les éléments manuellement
        panel.setPreferredSize(new Dimension(800, 800));  // Taille du panneau
        panel.setBackground(Color.BLACK);  // Fond noir pour le jeu

        setupLabel(scoreLabel, 10, 10, 200, 30, 20, Color.WHITE);
        setupLabel(ballLabel, 0, 0, 0, 0, 0, null);  // Temporairement sans dimensions
        setupLabel(paddleLabel, 0, 0, 0, 0, 0, null);  // Temporairement sans dimensions
        setupLabel(pauseLabel, 200, 350, 400, 100, 16, Color.RED); // Label pour le message de pause
        pauseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pauseLabel.setVisible(false); // Cache le message par défaut

        // Ajouter le panneau à la fenêtre et configurer la fenêtre
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);  // Centrer la fenêtre à l'écran
        frame.setVisible(true);
        frame.setResizable(false);  // Empêche le redimensionnement de la fenêtre

    }

    // Méthode d'initialisation des étiquettes pour afficher les éléments du jeu
    private void setupLabel(JLabel label, int x, int y, int width, int height, int fontSize, Color color) {
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));  // Police de caractère
        if (color != null) label.setForeground(color);  // Couleur du texte pour le score
        panel.add(label);
    }

    // Méthode appelée par l'observateur pour mettre à jour la vue
    @Override
    public void onPongUpdated(EntityPong ball, EntityPong paddle, int score) {
        // Mettre à jour l'image de la balle et de la raquette
        ballLabel.setIcon(ball.getImage());
        paddleLabel.setIcon(paddle.getImage());

        // Mettre à jour les positions des éléments
        ballLabel.setBounds(ball.getX(), ball.getY(), ball.sizeX(), ball.sizeY());
        paddleLabel.setBounds(paddle.getX(), paddle.getY(), paddle.sizeX(), paddle.sizeY());

        // Mettre à jour le score affiché
        scoreLabel.setText("Score: " + score);

        // Révalidate et repaint pour redessiner la fenêtre
        panel.revalidate();
        panel.repaint();
    }

    public void displayPauseMessage(String message) {
        pauseLabel.setText(message);
        pauseLabel.setVisible(true);
    }

    public void clearPauseMessage() {
        pauseLabel.setVisible(false);
    }

    // Retourner la fenêtre principale pour accéder au frame de l'application
    public JFrame getFrame() {
        return frame;
    }
}
