package SpaceInvaders;

import java.util.List;

public interface SpaceInvadersObserver {
    void onSpaceInvadersUpdated(EntitySpaceInvaders player, List<EntitySpaceInvaders> enemies, List<EntitySpaceInvaders> projectiles, int score, int wave, int lifeLeft);
    void onGameOver(String message);
}
