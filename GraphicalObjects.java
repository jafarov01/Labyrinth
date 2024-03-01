import java.awt.Graphics;
import java.awt.Image;

public class GraphicalObjects {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image img;

    public GraphicalObjects(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    public boolean intersects(GraphicalObjects object) {
        // Check if the object's position intersects with this object
        return x + width > object.x && x < object.x + object.width && y + height > object.y && y < object.y + object.height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImg() {
        return img;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}
