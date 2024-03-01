import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Level {
    private final int WALL_WIDTH = 40;
    private final int WALL_HEIGHT = 40;
    public ArrayList<Wall> walls;

    public Level(String levelPath) throws IOException {
        // Load the level from the given file path
        loadLevel(levelPath);
    }

    public void loadLevel(String levelPath) throws FileNotFoundException, IOException {
        // Read the level file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(levelPath))) {
            // Initialize the list of walls
            walls = new ArrayList<>();
            int y = 0;
            String line;
            while ((line = br.readLine()) != null) {
                int x = 0;
                for (char blockType : line.toCharArray()) {
                    // If the current block is a wall
                    if (Character.isDigit(blockType)) {
                        // Load the wall image
                        Image img = new ImageIcon("Images/wall.png").getImage();
                        // Create a new wall and add it to the list of walls
                        walls.add(new Wall(x * WALL_WIDTH, y * WALL_HEIGHT, WALL_WIDTH, WALL_HEIGHT, img));
                    }
                    // Increment the x coordinate for the next block
                    x++;
                }
                // Increment the y coordinate for the next row of blocks
                y++;
            }
        }
    }

    public void draw(Graphics g) {
        // Iterate over the list of walls and draw each wall
        for (Wall wall : walls) {
            wall.draw(g);
        }
    }

    public boolean intersects(Dragon dragon){
        Wall collided = null;
        for(Wall wall: walls){
            if(dragon.intersects(wall)){
                collided = wall;
                break;
            }
        }
        if (collided!= null) {
            
            return true;
        } else {
            return false;
        } 
        
    }
    
    public boolean intersects(Player player){
        Wall collided = null;
        for(Wall wall: walls){
            if(player.intersects(wall)){
                collided = wall;
                break;
            }
        }
        if (collided!= null) {
            
            return true;
        } else {
            return false;
        } 
    }
}