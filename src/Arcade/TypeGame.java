package Arcade;  // Le TypeGame est dans le package Arcade

public enum TypeGame {
    PONG,
    SPACE_INVADERS;

    public static TypeGame fromString(String name) {
        switch (name) {
            case "Pong":
                return PONG;
            case "Space Invaders":
                return SPACE_INVADERS;
            default:
                throw new IllegalArgumentException("Unknown game type: " + name);
        }
    }
}
