package SpaceInvaders;
import javax.swing.ImageIcon;

public class EntitySpaceInvaders {
    public enum EntityType {
        PLAYER,
        ENEMY,
        PROJECTILE_PLAYER,
        PROJECTILE_ENEMY
    }

    private int x, y;
    private final ImageIcon image;
    private final EntityType type;

    public EntitySpaceInvaders(String imgPath, int x, int y, EntityType type) {
        this.x = x;
        this.y = y;
        this.image = new ImageIcon(getClass().getResource(imgPath));
        this.type = type;
    }

    public EntityType getType() {
        return type;
    }

    public ImageIcon getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int sizeX() {
        return image.getIconWidth();
    }

    public int sizeY() {
        return image.getIconHeight();
    }

    public void move(int shiftX, int shiftY) {
        x += shiftX;
        y += shiftY;
    }

    public boolean collides(EntitySpaceInvaders e) {
        int xm1 = getX();
        int ym1 = getY();
        int xp1 = getX() + sizeX();
        int yp1 = getY() + sizeY();
        int xm2 = e.getX();
        int ym2 = e.getY();
        int xp2 = e.getX() + e.sizeX();
        int yp2 = e.getY() + e.sizeY();
        return !(xm2 > xp1 || xm1 > xp2 || ym2 > yp1 || ym1 > yp2);
    }
}