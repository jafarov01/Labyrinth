import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

public class Dragon extends GraphicalObjects {
    private double xPosition = 1;
    private double yPosition;
    private boolean isMovingOnXAxis = true;
    public String direction = "x";
    public double directionValue = 0;

    public Dragon(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
    }

    // For moving in X direction
    public void moveX() {
        if ((xPosition < 0 && x > 0) || (xPosition > 0 && x + width <= 780)) {
            x += xPosition;
        } else {
            changeDirection();
        }
    }

    // For moving in Y direction
    public void moveY() {
        if ((yPosition < 0 && y > 0) || (yPosition > 0 && y + height <= 740)) {
            y += yPosition;
        } else {
            changeDirection();
        }
    }
    // Implementation of changing the direction of Dragon randomly

    public void changeDirection(Level level) {
        Random rand = new Random();
        int n = rand.nextInt(4);
        if (n == 0) {
            boolean flag = true;
            Rectangle rec = new Rectangle(x, y - 1, width, height);
            for (Wall wall : level.walls) {
                Rectangle otherRect = new Rectangle(wall.x, wall.y, wall.width, wall.height);
                if (rec.intersects(otherRect)) {
                    flag = false;
                }

            }
            if (flag) {
                yPosition = -1;
                directionValue = yPosition;
                direction = "y";
                isMovingOnXAxis = false;
            } else {
                changeDirection(level);
            }
        } else if (n == 2) {
            boolean flag = true;
            Rectangle rec = new Rectangle(x - 1, y, width, height);
            for (Wall wall : level.walls) {
                Rectangle otherRect = new Rectangle(wall.x, wall.y, wall.width, wall.height);
                if (rec.intersects(otherRect)) {
                    flag = false;
                }

            }
            if (flag) {
                xPosition = -1;
                directionValue = xPosition;
                direction = "x";
                isMovingOnXAxis = true;
            } else {
                changeDirection(level);
            }
        } else {
            boolean flag = true;
            Rectangle rec = new Rectangle(x + 1, y, width, height);
            for (Wall wall : level.walls) {
                Rectangle otherRect = new Rectangle(wall.x, wall.y, wall.width, wall.height);
                if (rec.intersects(otherRect)) {
                    flag = false;
                }

            }
            if (flag) {
                xPosition = 1;
                directionValue = xPosition;
                direction = "x";
                isMovingOnXAxis = true;
            } else {
                changeDirection(level);
            }
        }
    }

    public void changeDirection() {
        Random rand = new Random();
        int n = rand.nextInt(4);
        if (n == 0) {
            yPosition = -1;
            directionValue = yPosition;
            direction = "y";
            isMovingOnXAxis = false;
        } else if (n == 1) {
            yPosition = 1;
            directionValue = yPosition;
            direction = "y";
            isMovingOnXAxis = false;
        } else if (n == 2) {
            xPosition = -1;
            directionValue = xPosition;
            direction = "x";
            isMovingOnXAxis = true;
        } else {
            xPosition = 1;
            directionValue = xPosition;
            direction = "x";
            isMovingOnXAxis = true;
        }
    }

    public void move() {
        if (isMovingOnXAxis == true) {
            moveX();
        } else {
            moveY();
        }
    }

    @Override
    public boolean intersects(GraphicalObjects other) {
        if (direction.equals("x")) {
            int x1 = x + (int) xPosition;
            int y1 = y;
            int x2 = other.x;
            int y2 = other.y;
            return x1 < x2 + other.width && x1 + width > x2 && y1 < y2 + other.height && y1 + height > y2;
        } else {
            int x1 = x;
            int y1 = y + (int) yPosition;
            int x2 = other.x;
            int y2 = other.y;
            return x1 < x2 + other.width && x1 + width > x2 && y1 < y2 + other.height && y1 + height > y2;
        }
    }
}
