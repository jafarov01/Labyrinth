import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.util.Random;
import java.sql.SQLException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Engine extends JPanel {
    private final int FRAMES_PER_SECOND = 250;
    private final int PLAYER_MOVEMENT_DISTANCE = -1;
    private boolean gamePaused = false;
    private Image backgroundImage;
    public int currentLevelNumber = 1;
    public int playerScore = 0;
    private Level currentLevel;
    private Player player;
    private Dragon dragon;
    private Dark darkBoard;
    private Timer frameTimer;

    public Engine() {

        super();
        backgroundImage = new ImageIcon("Images/BG.png").getImage();
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed S");
        this.getActionMap().put("pressed S", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("PRESSED S");
                player.setYVelocity(-PLAYER_MOVEMENT_DISTANCE);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed W");
        this.getActionMap().put("pressed W", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("PRESSED W");
                player.setYVelocity(PLAYER_MOVEMENT_DISTANCE);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed D");
        this.getActionMap().put("pressed D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("PRESSED D");
                player.setXVelocity(-PLAYER_MOVEMENT_DISTANCE);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed A");
        this.getActionMap().put("pressed A", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("PRESSED A");
                player.setXVelocity(PLAYER_MOVEMENT_DISTANCE);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getActionMap().put("space", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("PRESSEED space");
                gamePaused = !gamePaused;
            }
        });
        restartGame();
        frameTimer = new Timer(1000 / FRAMES_PER_SECOND, new NewFrameListener(this));
        frameTimer.start();

    }

    public void restartGame() {
        try {
            currentLevel = new Level("data/level" + currentLevelNumber + ".txt");
        } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Image playerImage = new ImageIcon("Images/player.png").getImage();
        player = new Player(0, 600, 39, 39, playerImage);

        generateDragonForLevel(currentLevel);
        darkBoard = new Dark();
        darkBoard.update(player);
    }

    private Dragon generateDragonForLevel(Level level) {
        Random r = new Random();
        boolean dragonExists = false;
        while (!dragonExists) {
            int x = r.nextInt(741);
            int y = r.nextInt(321) + 40;
            boolean isValidPosition = true;
            for (Wall wall : level.walls) {
                // Check if the dragon's position intersects with the wall
                if (x + 40 > wall.x && x < wall.x + wall.width && y + 40 > wall.y && y < wall.y + wall.height) {
                    isValidPosition = false;
                    break;
                }
            }
            if (isValidPosition) {
                Image dragonImage = new ImageIcon("Images/dragon.png").getImage();
                dragon = new Dragon(x, y, 40, 40, dragonImage);
                dragonExists = true;
                return dragon;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(backgroundImage, 0, 0, 800, 800, null);
        currentLevel.draw(grphcs);
        player.draw(grphcs);
        dragon.draw(grphcs);
        darkBoard.update(player);
        darkBoard.draw(grphcs);
    }

    class NewFrameListener implements ActionListener {
        public JPanel panel;

        NewFrameListener(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!gamePaused) {
                dragon.move();
                if (dragon.intersects(player)) {
                    String name = JOptionPane.showInputDialog(
                        "Oops, you got caught. But don't worry, you still scored " + (playerScore) + " points!\nEnter your name here to be added to the leaderboard:",
                        "Caught player");
                    if (name != null) {
                        try {
                            Database db = new Database();
                            db.insertScore(name, playerScore);
                        } catch (SQLException ex) {
                        }
                        playerScore = 0;
                        currentLevelNumber = 1;
                        restartGame();
                    }
                }
                if (player.win()) {
                    playerScore = playerScore + 1;
                    JOptionPane.showMessageDialog(panel, "You're a pro! You won this level and scored " + playerScore + " points. Keep it up, pro!",
                        "You're a pro!", JOptionPane.PLAIN_MESSAGE);
                    if (currentLevelNumber <= 9) {
                        currentLevelNumber = currentLevelNumber + 1;
                    } else {
                        currentLevelNumber = 1;
                    }
                    restartGame();
                }
                if (currentLevel.intersects(dragon)) {
                    dragon.changeDirection(currentLevel);
                }
                if (currentLevel.intersects(player)) {
                    player.setXVelocity(0);
                    player.setYVelocity(0);
                }
                player.move();
            }
            repaint();
        }
    }
}
