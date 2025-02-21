package SpaceInvaders;  // La classe SpaceInvaders est dans le package SpaceInvaders

import Arcade.Game;
import Arcade.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpaceInvaders extends Game {
    private static final int ENEMY_SPEED_INCREMENT = 1;
    public static int GAME_WIDTH = 1000;
    public static int GAME_HEIGHT = 750;
    public static int PLAYER_START_X = GAME_WIDTH / 2;
    public static int PLAYER_START_Y = GAME_HEIGHT - 150;
    public static int ENEMY_ROWS = 3;
    public static int ENEMY_COLUMNS = 8;
    public static int ENEMY_SPACING_X = 50;
    public static int ENEMY_SPACING_Y = 50;
    public static int PLAYER_SPEED = 10;
    public static int PROJECTILE_SPEED = 5;
    public static int ENEMY_SPEED = 3;
    public static long SHOT_COOLDOWN = 500;
    private static final int ENEMY_SPEED_BASE = 3; // Vitesse de base des ennemis, à réinitialiser à chaque vague
    private static final double SHOOT_PROBABILITY_INCREMENT = 0.05; // Augmentation lente de la probabilité

    private final EntitySpaceInvaders player;
    private final List<EntitySpaceInvaders> projectiles;
    private final List<EntitySpaceInvaders> enemies;
    private long lastShotTime = 0;
    private int direction = 1;
    private int score = 0;
    private int wave = 1;
    private int lifeLeft = 5;
    private double enemyShootProbability = 0.1; // Probabilité initiale (10 %)
    private static int edgeHitCount = 0;  // Compteur pour savoir combien de fois les ennemis ont atteint le bord


    // Liste des observateurs
    private final List<SpaceInvadersObserver> observers = new ArrayList<>();

    public SpaceInvaders() {
        player = new EntitySpaceInvaders("../assets/ship.png", PLAYER_START_X, PLAYER_START_Y, EntitySpaceInvaders.EntityType.PLAYER);
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        initializeEnemies();
    }

    // Gestion des observateurs
    public void addObserver(SpaceInvadersObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (SpaceInvadersObserver observer : observers) {
            observer.onSpaceInvadersUpdated(player, enemies, projectiles, score, wave, lifeLeft);
        }
    }

    private void notifyGameOver() {
        for (SpaceInvadersObserver observer : observers) {
            observer.onGameOver("Game Over!");
        }
    }

    // Initialisation des ennemis
    private void initializeEnemies() {
        for (int row = 0; row < ENEMY_ROWS; row++) {
            for (int col = 0; col < ENEMY_COLUMNS; col++) {
                enemies.add(new EntitySpaceInvaders(
                        "../assets/enemy.png",
                        50 + col * ENEMY_SPACING_X,
                        50 + row * ENEMY_SPACING_Y,
                        EntitySpaceInvaders.EntityType.ENEMY
                ));
            }
        }
    }

    // Gestion des mouvements du joueur
    public void movePlayerLeft() {
        if (player.getX() > 0) {
            player.move(-PLAYER_SPEED, 0);
        }
    }

    public void movePlayerRight() {
        if (player.getX() + player.sizeX() < GAME_WIDTH) {
            player.move(PLAYER_SPEED, 0);
        }
    }

    // Tirs
    public void shootProjectile() {
        if (canShoot(lastShotTime, SHOT_COOLDOWN)) {
            projectiles.add(new EntitySpaceInvaders(
                    "../assets/projectile.png",
                    player.getX() + player.sizeX() / 2 - 5,
                    player.getY() - 20,
                    EntitySpaceInvaders.EntityType.PROJECTILE_PLAYER
            ));
            lastShotTime = System.currentTimeMillis();
        }
    }

    public void enemyShootProjectile() {
        Random random = new Random();
        for (EntitySpaceInvaders enemy : enemies) {
            if (random.nextDouble() <= enemyShootProbability) {
                projectiles.add(new EntitySpaceInvaders(
                        "../assets/projectile1.png",
                        enemy.getX() + enemy.sizeX() / 2 - 5,
                        enemy.getY() + enemy.sizeY(),
                        EntitySpaceInvaders.EntityType.PROJECTILE_ENEMY
                ));
            }
        }
    }

    private boolean canShoot(long lastShot, long cooldown) {
        return System.currentTimeMillis() - lastShot >= cooldown;
    }

    // Mise à jour du jeu
    @Override
    public void updateGame() {
        moveEnemies();
        moveProjectiles();
        handleCollisions();
        checkWinOrLose();
        notifyObservers();
    }

    // Gestion des ennemis
    private void moveEnemies() {
        boolean atEdge = false;
        for (EntitySpaceInvaders enemy : enemies) {
            enemy.move(ENEMY_SPEED * direction, 0);
            if (enemy.getX() + enemy.sizeX() >= GAME_WIDTH || enemy.getX() <= 0) {
                atEdge = true;
            }
        }

        // Si les ennemis ont touché le bord, on incrémente le compteur
        if (atEdge) {
            edgeHitCount++;
            direction *= -1;  // Inverser la direction
            for (EntitySpaceInvaders enemy : enemies) {
                enemy.move(0, ENEMY_SPACING_Y / 2);  // Déplacer les ennemis vers le bas
            }

            // Si les ennemis ont touché le bord deux fois, on augmente la vitesse
            if (edgeHitCount >= 2) {
                increaseEnemySpeed();
                edgeHitCount = 0;  // Réinitialiser le compteur
            }
        }
    }

    // Augmenter la vitesse des ennemis lentement
    private void increaseEnemySpeed() {
        if (ENEMY_SPEED < 6) {  // Limiter la vitesse maximale des ennemis
            ENEMY_SPEED += ENEMY_SPEED_INCREMENT; // Incrémentation de la vitesse
        }
    }


    // Gestion des projectiles
    private void moveProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            EntitySpaceInvaders p = projectiles.get(i);
            p.move(0, p.getType() == EntitySpaceInvaders.EntityType.PROJECTILE_PLAYER ? -PROJECTILE_SPEED : PROJECTILE_SPEED);

            if (p.getY() < 0 || p.getY() > GAME_HEIGHT) {
                projectiles.remove(i--);
            }
        }
    }

    // Gestion des collisions
    private void handleCollisions() {
        for (int i = 0; i < projectiles.size(); i++) {
            EntitySpaceInvaders p = projectiles.get(i);

            if (p.getType() == EntitySpaceInvaders.EntityType.PROJECTILE_PLAYER) {
                for (int j = 0; j < enemies.size(); j++) {
                    if (p.collides(enemies.get(j))) {
                        projectiles.remove(i--);
                        enemies.remove(j);
                        score += 100;
                        break;
                    }
                }
            }
            else if (p.getType() == EntitySpaceInvaders.EntityType.PROJECTILE_ENEMY) {
                if (p.collides(player)) {
                    projectiles.remove(i--);
                    lifeLeft--;
                    if (lifeLeft <= 0) {
                        // État initial : "PLAYING", "WIN", "LOSE"
                        notifyGameOver();
                        return;
                    }
                }
            }
        }
    }

    // Gestion de la victoire ou de la défaite
    private void checkWinOrLose() {
        if (enemies.isEmpty()) {
            startNewWave();
        }
        else if (lifeLeft <= 0) {
            notifyGameOver();
        }
    }

    private void startNewWave() {
        wave++;
        ENEMY_SPEED = ENEMY_SPEED_BASE; // Réinitialiser la vitesse des ennemis à la valeur de base
        increaseEnemyShootProbability(); // Augmenter lentement la probabilité de tir des ennemis
        enemies.clear();
        projectiles.clear();
        initializeEnemies();
    }

    // Réinitialisation du jeu
    public void resetGame() {
        player.setX(PLAYER_START_X);
        player.setY(PLAYER_START_Y);
        enemies.clear();
        projectiles.clear();
        direction = 1;
        score = 0;
        wave = 1;
        lifeLeft = 5;
        ENEMY_SPEED = ENEMY_SPEED_BASE; // Réinitialiser la vitesse des ennemis au début du jeu
        lastShotTime = 0;
        enemyShootProbability = 0.1; // Probabilité initiale
        initializeEnemies(); // Réinitialiser les ennemis
    }

    private void increaseEnemyShootProbability() {
        // Limite supérieure de la probabilité de tir
        double MAX_ENEMY_SHOOT_PROBABILITY = 0.5;
        if (enemyShootProbability < MAX_ENEMY_SHOOT_PROBABILITY) {
            enemyShootProbability += SHOOT_PROBABILITY_INCREMENT;  // Augmentation progressive
        }
    }

    @Override
    public void startGame() {
        System.out.println("Starting SpaceInvaders...");
        // Logique spécifique pour démarrer SpaceInvaders
    }

    /*@Override
    public void updateGame() {
        // Logique pour mettre à jour l'état du jeu SpaceInvaders
    }*/

    @Override
    public void renderGame() {
        // Logique pour afficher le jeu SpaceInvaders
    }
}
