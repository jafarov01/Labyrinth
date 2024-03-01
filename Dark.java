import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

class Darkness extends GraphicalObjects {
    boolean exposed = false;

    public Darkness(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
    }
}

public class Dark {
    private final int TILE_WIDTH = 40;
    private final int TILE_HEIGHT = 40;
    private final int GRID_WIDTH = 800;
    private final int GRID_HEIGHT = 800;
    public ArrayList<Darkness> darkTiles;

    public Dark() {
        darkTiles = new ArrayList<>();
        Image img = new ImageIcon("Images/Darkness.png").getImage();
        for (int i = 0; i < GRID_WIDTH / TILE_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT / TILE_HEIGHT; j++) {
                darkTiles.add(new Darkness(i * TILE_WIDTH, j * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT, img));
            }
        }
    }

    public void draw(Graphics g) {
        for (Darkness darkness : darkTiles) {
            if (!darkness.exposed) {
                darkness.draw(g);
            }
        }
    }

    public void update(Player player) {
        for (Darkness darkness : darkTiles) {
            Rectangle rect = new Rectangle(player.x - 3 * player.width, player.y - 3 * player.height, 7 * player.width,
                    7 * player.height);
            Rectangle otherRect = new Rectangle(darkness.x, darkness.y, darkness.width, darkness.height);
            if (rect.intersects(otherRect)) {
                darkness.exposed = true;
            } else {
                darkness.exposed = false;
            }
        }
    }
}
