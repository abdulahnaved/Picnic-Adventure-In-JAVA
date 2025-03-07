
package my.company.my.yogibear;

import java.sql.*;

import javax.swing.*;

/**
 * Manages the high scores for the Yogi Bear game by interacting with a MySQL database.
 * Provides functionality to save a player's high score and retrieve the top 10 high scores.
 */
public class HighScoreManager {

    /**
     * MySQL database connection URL.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/yogi_game";

    /**
     * MySQL database username.
     */
    private static final String USER = "root";

    /**
     * MySQL database password.
     */
    private static final String PASSWORD = "Aknl/156";

    /**
     * Saves a player's high score to the database.
     *
     * @param playerName The name of the player.
     * @param score      The score achieved by the player.
     */
    public static void saveHighScore(String playerName, int score) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connection successful!");
            String sql = "INSERT INTO highscores (player_name, score) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, playerName);
                stmt.setInt(2, score);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the top 10 high scores from the database, ordered by highest score first.
     *
     * @return A string containing the formatted list of the top 10 high scores, each on a new line.
     */
    public static String getHighScores() {
        StringBuilder highScores = new StringBuilder();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "SELECT player_name, score FROM highscores ORDER BY score DESC LIMIT 10";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                int rank = 1;
                while (rs.next()) {
                    String playerName = rs.getString("player_name");
                    int score = rs.getInt("score");
                    highScores.append(rank++).append(". ").append(playerName).append(" - ").append(score).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highScores.toString();
    }
}
