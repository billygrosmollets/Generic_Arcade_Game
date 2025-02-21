package Arcade;

public class Main {
    public static void main(String[] args) {
        // Création de la vue et du contrôleur
        View view = new View();
        Controller controller = new Controller(view);

        // Lier le contrôleur à la vue pour gérer les événements
        view.addListeners(controller);
    }
}
