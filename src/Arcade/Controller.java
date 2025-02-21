package Arcade;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private final View view;
    private final GameFactory gameFactory;

    public Controller(View view) {
        this.view = view;
        this.gameFactory = new GameFactory(); // On crée une instance de GameFactory
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Play")) {
            // Récupère le type de jeu sélectionné par l'utilisateur
            TypeGame selectedGame = TypeGame.fromString(view.getTypeGame());


            // Délégue la création du jeu à la fabrique
            Game currentGame = gameFactory.createGame(selectedGame, view);

            // Si un jeu est créé, on démarre le jeu
            if (currentGame != null) {
                currentGame.startGame();
            }
        }
    }
}
