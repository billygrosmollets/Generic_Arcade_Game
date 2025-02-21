package Pong;

public interface PongObserver {
    void onPongUpdated(EntityPong ball, EntityPong paddle, int score);
}