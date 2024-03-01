import java.awt.Image;

public class Player extends GraphicalObjects {
    // New names for the class variables
    private double xVelocity = 0;
    private double yVelocity = 0;
    private boolean moveInXDirection = true;
    public String direction = "x";
    public double velocity = 0;

    public Player(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
    }

    public void moveX() {
        // Check if the object is within the left or right boundaries of the screen
        if ((xVelocity < 0 && x > 0) || (xVelocity > 0 && x + width <= 800)) {
            // Update the x position of the object
            x += xVelocity;
        }
    }

    public void moveY() {
        // Check if the object is within the top or bottom boundaries of the screen
        if ((yVelocity < 0 && y > 0) || (yVelocity > 0 && y + height <= 800)) {
            // Update the y position of the object
            y += yVelocity;
        }
    }

    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
        velocity = xVelocity;
        direction = "x";
        moveInXDirection = true;
    }

    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
        velocity = yVelocity;
        direction = "y";
        moveInXDirection = false;
    }

    public double getXVelocity() {
        return xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public void move() {
        if (moveInXDirection) {
            moveX();
        } else {
            moveY();
        }
    }

    public boolean win() {
        int rectX = x;
        int rectY = y;
        int rectWidth = width;
        int rectHeight = height;

        int otherRectX = 770;
        int otherRectY = 0;
        int otherRectWidth = 40;
        int otherRectHeight = 40;

        return (rectX < otherRectX + otherRectWidth &&
                rectX + rectWidth > otherRectX &&
                rectY < otherRectY + otherRectHeight &&
                rectY + rectHeight > otherRectY);
    }

    // Check if the x and y coordinates of the two objects overlap
    @Override
    public boolean intersects(GraphicalObjects other) {
        if (direction.equals("x")) {
            int thisX = x + (int) xVelocity;
            int thisY= y;
            int thisWidth = width;
            int thisHeight = height;
            
            int otherX = other.x;
            int otherY = other.y;
            int otherWidth = other.width;
            int otherHeight = other.height;
            
            return (thisX < otherX + otherWidth &&
                    thisX + thisWidth > otherX &&
                    thisY < otherY + otherHeight &&
                    thisY + thisHeight > otherY);
        } else {
            int thisX = x;
            int thisY = y + (int) yVelocity;
            int thisWidth = width;
            int thisHeight = height;
            
            int otherX = other.x;
            int otherY = other.y;
            int otherWidth = other.width;
            int otherHeight = other.height;
            
            return (thisX < otherX + otherWidth &&
                    thisX + thisWidth > otherX &&
                    thisY < otherY + otherHeight &&
                    thisY + thisHeight > otherY);
        }
    }
}
