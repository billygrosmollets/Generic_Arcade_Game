package Arcade;  // Le TypeGame est dans le package Arcade


public class Game {
    volatile boolean alive = true;

    // Méthode pour savoir si le jeu est encore en vie (en cours)
    public boolean isAlive() {
        return alive;
    }

    // Méthode pour démarrer le jeu, à définir dans les classes filles
    public void startGame() {
        // Implémentation spécifique dans les sous-classes
    }

    // Méthode pour mettre à jour le jeu, à définir dans les classes filles
    public void updateGame() {
        // Implémentation spécifique dans les sous-classes
    }

    // Méthode pour rendre le jeu visible (affichage), à définir dans les classes filles
    public void renderGame() {
        // Implémentation spécifique dans les sous-classes
    }

    // Autres méthodes communes pour les jeux
}
