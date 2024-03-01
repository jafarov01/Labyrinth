import java.sql.*;
import java.util.*;

public class Database {
    private PreparedStatement insertStatement;
    private Connection connection;

    public Database() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:1527/bayramc";
        try {
            connection = DriverManager.getConnection(dbURL, "root", "1511902001mJ!");
          } catch (SQLException e) {
            System.out.println("Connection to database failed!");
            e.printStackTrace();
          }
        String insertQuery = "INSERT INTO Labyrinth (NAME,score) VALUES (?,?)";
        insertStatement = connection.prepareStatement(insertQuery);
    }

    public ArrayList<Data> getHighScores() throws SQLException {
        String query = "SELECT * FROM tron_data";
        ArrayList<Data> points = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("POINT");
            points.add(new Data(name, score));
        }
        sortPoints(points);
        return points;
    }

    private void sortPoints(ArrayList<Data> scores) {
        Collections.sort(scores, new Comparator<Data>() {
            @Override
            public int compare(Data t, Data t1) {
                return t1.getScores() - t.getScores();
            }
        });
    }

    public void insertScore(String name, int score) throws SQLException {
        insertStatement.setString(1, name);
        insertStatement.setInt(2, score);
        insertStatement.executeUpdate();
    }
}
