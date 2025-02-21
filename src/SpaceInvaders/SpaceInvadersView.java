package SpaceInvaders;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersView implements SpaceInvadersObserver {
    private final JFrame frame;
    private final JPanel panel;
    private final JLabel playerLabel;
    private final ArrayList<JLabel> enemyLabels;
    private final ArrayList<JLabel> projectileLabels;
    private final JLabel scoreLabel;
    private final JLabel waveLabel;
    private final JLabel lifeLabel;

    public SpaceInvadersView() {
        frame = new JFrame("Space Invaders");
        panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(SpaceInvaders.GAME_WIDTH, SpaceInvaders.GAME_HEIGHT));
        panel.setBackground(Color.BLACK);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setResizable(false);  // Empêche le redimensionnement de la fenêtre


        // Initialisation des labels
        playerLabel = new JLabel();
        scoreLabel = createLabel("Score: 0", 10);
        waveLabel = createLabel("Wave: 0", 30);
        lifeLabel = createLabel("Life Left: 5", 50);

        enemyLabels = new ArrayList<>();
        projectileLabels = new ArrayList<>();

        panel.add(playerLabel);
        panel.add(scoreLabel);
        panel.add(waveLabel);
        panel.add(lifeLabel);
    }

    private JLabel createLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setBounds(10, y, 200, 30);
        return label;
    }

    private void updateEntityPosition(JLabel label, EntitySpaceInvaders entity) {
        label.setBounds(entity.getX(), entity.getY(), entity.sizeX(), entity.sizeY());
        label.setIcon(entity.getImage());
    }

    public void resetView() {
        panel.removeAll();
        panel.add(playerLabel);
        panel.add(scoreLabel);
        panel.add(waveLabel);
        panel.add(lifeLabel);
        enemyLabels.clear();
        projectileLabels.clear();
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void onSpaceInvadersUpdated(EntitySpaceInvaders player, List<EntitySpaceInvaders> enemies, List<EntitySpaceInvaders> projectiles, int score, int wave, int lifeLeft) {
        resetView(); // Réinitialiser la vue avant de la mettre à jour

        // Mise à jour du joueur
        updateEntityPosition(playerLabel, player);

        // Mise à jour des ennemis et des projectiles
        updateEntities(enemies, enemyLabels);
        updateEntities(projectiles, projectileLabels);

        // Mise à jour des scores et autres informations
        scoreLabel.setText("Score: " + score);
        waveLabel.setText("Wave: " + wave);
        lifeLabel.setText("Life Left: " + lifeLeft);

        panel.revalidate();
        panel.repaint();
    }

    private void updateEntities(List<EntitySpaceInvaders> entities, List<JLabel> labels) {
        for (int i = 0; i < entities.size(); i++) {
            EntitySpaceInvaders entity = entities.get(i);
            if (i < labels.size()) {
                updateEntityPosition(labels.get(i), entity);
            } else {
                JLabel entityLabel = new JLabel(entity.getImage());
                entityLabel.setBounds(entity.getX(), entity.getY(), entity.sizeX(), entity.sizeY());
                labels.add(entityLabel);
                panel.add(entityLabel);
            }
        }
    }

    @Override
    public void onGameOver(String message) {
        System.out.println(message); // Optionnel : log pour le développeur
    }

    public JFrame getFrame() {
        return frame;
    }
}
