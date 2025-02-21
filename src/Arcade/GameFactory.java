package Arcade;

import Pong.PongStart;
import SpaceInvaders.SpaceInvadersStart;



public class GameFactory {

    // Méthode qui crée le jeu et son contrôleur
    public Game createGame(TypeGame type, View view) {
        Game game = null; // Déclaration et initialisation de la variable `game`

        switch (type) {
            case PONG:
                PongStart pongStart = new PongStart();
                break;
            case SPACE_INVADERS:
                SpaceInvadersStart spaceInvadersStart = new SpaceInvadersStart();
                break;
            default:
                throw new IllegalArgumentException("Game type not supported");
        }
        return game;
    }
}
