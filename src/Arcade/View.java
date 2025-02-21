package Arcade;

import javax.swing.*;
import java.awt.*;

public class View {
    private final JComboBox<String> comboBox;
    private final JButton playButton;
    private final JTextArea gameInfoArea;

    public View() {
        // Initialisation de la fenêtre
        JFrame frame = new JFrame("Arcade Game Selector");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        // Titre principal
        JLabel titleLabel = new JLabel("Arcade Game Selector", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setForeground(Color.CYAN);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panneau central pour la sélection de jeu
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Label de sélection
        JLabel selectLabel = new JLabel("Select a game:");
        selectLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        selectLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(selectLabel, gbc);

        // ComboBox pour les jeux disponibles
        String[] strcomboBox = {"Pong", "Space Invaders", "Tetris", "Pac-Man"};
        comboBox = new JComboBox<>(strcomboBox);
        comboBox.setFont(new Font("Roboto", Font.PLAIN, 16));
        comboBox.setBackground(Color.DARK_GRAY);
        comboBox.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(comboBox, gbc);

        // Zone d'information sur le jeu
        JLabel infoLabel = new JLabel("Game Info:");
        infoLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        infoLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(infoLabel, gbc);

        gameInfoArea = new JTextArea(5, 20);
        gameInfoArea.setFont(new Font("Roboto", Font.PLAIN, 14));
        gameInfoArea.setLineWrap(true);
        gameInfoArea.setWrapStyleWord(true);
        gameInfoArea.setEditable(false);
        gameInfoArea.setBackground(Color.DARK_GRAY);
        gameInfoArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(gameInfoArea);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(scrollPane, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);

        // Panneau inférieur pour les boutons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        bottomPanel.setBackground(Color.BLACK);

        // Bouton Play
        playButton = new JButton("Play");
        playButton.setFont(new Font("Roboto", Font.BOLD, 16));
        playButton.setFocusable(false);
        playButton.setBackground(Color.GREEN);
        playButton.setForeground(Color.BLACK);
        bottomPanel.add(playButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Rendre la fenêtre visible
        frame.setVisible(true);

        // Ajouter un comportement pour afficher les informations sur le jeu sélectionné
        comboBox.addActionListener(e -> updateGameInfo());
        updateGameInfo();
    }

    // Méthode pour mettre à jour les informations sur le jeu sélectionné
    private void updateGameInfo() {
        String selectedGame = (String) comboBox.getSelectedItem();
        if (selectedGame != null) {
            switch (selectedGame) {
                case "Pong":
                    gameInfoArea.setText("Pong: A classic arcade game where players control paddles to hit a ball back and forth.");
                    break;
                case "Space Invaders":
                    gameInfoArea.setText("Space Invaders: Defend the Earth from waves of alien invaders in this iconic shooter.");
                    break;
                case "Tetris":
                    gameInfoArea.setText("Tetris: Arrange falling blocks to complete lines and score points in this timeless puzzle game.");
                    break;
                case "Pac-Man":
                    gameInfoArea.setText("Pac-Man: Navigate the maze, eat dots, and avoid ghosts in this beloved arcade classic.");
                    break;
                default:
                    gameInfoArea.setText("No information available for this game.");
            }
        }
    }

    // Getter pour le type de jeu sélectionné
    public String getTypeGame() {
        return (String) comboBox.getSelectedItem();
    }

    // Méthode pour ajouter les listeners
    public void addListeners(Controller controller) {
        playButton.addActionListener(controller);
    }
}
