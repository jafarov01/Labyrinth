import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class LabyrinthGame extends JFrame {
    private Engine initGame;

    public LabyrinthGame() {
        setTitle("LABYRINTH");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initGame = new Engine();
        getContentPane().add(initGame);

        setPreferredSize(new Dimension(800, 800));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenuItem newMenu = new JMenuItem("New");
        gameMenu.add(newMenu);

        newMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                initGame.currentLevelNumber = 1;
                initGame.playerScore = 0;
                initGame.restartGame();
            }
        });
        
        JMenuItem Ranking = new JMenuItem("Ranking");
        gameMenu.add(Ranking);
        Ranking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    Database db = new Database();
                    ArrayList<Data> Points = db.getHighScores();
                    StringBuilder content = new StringBuilder("                Ranking   \n");
                    for (int i = 0; i < Points.size() && i < 10; i++) {
                        content.append(String.valueOf(i + 1) + ". Name: " + Points.get(i).name + "  Point:  "
                                + Points.get(i).scores + "\n");
                    }
                    JOptionPane.showMessageDialog(initGame, content.toString(), "Ranking", JOptionPane.PLAIN_MESSAGE);

                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName());
                }
            }

        });
        setResizable(false);
        pack();
        setVisible(true);
    }
}
