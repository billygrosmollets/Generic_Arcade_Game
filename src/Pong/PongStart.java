package Pong;

public class PongStart {
    public PongStart() {
        Pong pong = new Pong();
        PongView pongView = new PongView();
        ControllerPong controllerPong = new ControllerPong(pong, pongView);
        pong.addObserver(pongView);
    }
}