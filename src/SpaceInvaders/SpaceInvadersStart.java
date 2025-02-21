package SpaceInvaders;

public class SpaceInvadersStart {
    public SpaceInvadersStart() {
        SpaceInvaders spaceInvaders = new SpaceInvaders();
        SpaceInvadersView spaceInvadersView = new SpaceInvadersView();
        ControllerSpaceInvaders controller = new ControllerSpaceInvaders(spaceInvaders, spaceInvadersView);
        spaceInvaders.addObserver(spaceInvadersView);
    }
}