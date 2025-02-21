package Pong;  // Le modèle Pong est dans le package 'Pong'
import Arcade.Game;
import java.util.ArrayList;
import java.util.List;

public class Pong extends Game {
    private final List<PongObserver> observers = new ArrayList<>();
    private final EntityPong ball;
    private final EntityPong paddle;
    private int speedX;
    private int speedY;
    private int score;
    private final int padSpeed;

    public Pong() {
        // Initialisation des entités du jeu
        this.ball = new EntityPong("../assets/ball.png", 0, 0);
        this.paddle = new EntityPong("../assets/pad.png", 400, 750);

        // Initialisation des variables de jeu
        this.speedX = 2;
        this.speedY = 2;
        this.score = 0;
        this.padSpeed = 20;

    }

    // Ajout d'un observateur
    public void addObserver(PongObserver observer) {
        observers.add(observer);
    }

    // Notifie tous les observateurs (comme la vue) quand l'état du jeu change
    private void notifyObservers() {
        for (PongObserver observer : observers) {
            observer.onPongUpdated(ball, paddle, score);  // Notifie la vue
        }
    }

    // Déplacer la raquette
    public void moveRight() {
        if (paddle.getX() + paddle.sizeX() < 800) {
            paddle.move(padSpeed, 0);
            notifyObservers();
        }
    }

    public void moveLeft() {
        if (paddle.getX() > 0) {
            paddle.move(-padSpeed, 0);
            notifyObservers();
        }
    }

    // Déplacer la balle et gérer les collisions
    public void moveBall() {
        if (ball.getX() + ball.sizeX() >= 800) speedX = -Math.abs(speedX);
        if (ball.getX() <= 0) speedX = Math.abs(speedX);
        if (ball.getY() <= 0) speedY = Math.abs(speedY);

        if (ball.collides(paddle)) {
            speedY = -Math.abs(speedY);
            score++;
            speedX += score / 5;
            speedY += score / 5;
        }

        ball.move(speedX, speedY);
        notifyObservers();

        if (ball.getY() > 800) { // Perte de balle
            ball.setX(0);
            ball.setY(0);
            score = 0;
            speedX = 2;
            speedY = 2;
            notifyObservers();
        }
    }

    public void startGame() {
        // Logique spécifique pour démarrer Pong
    }

    public void updateGame() {
        moveBall();  // Mise à jour du mouvement de la balle
    }

    public void renderGame() {
        // Cela peut être géré par la vue (pas nécessaire dans le modèle.)
    }
}
