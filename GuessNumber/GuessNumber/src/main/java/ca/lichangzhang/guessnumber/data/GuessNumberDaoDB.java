package ca.lichangzhang.guessnumber.data;

import ca.lichangzhang.guessnumber.models.Game;
import ca.lichangzhang.guessnumber.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
@Repository
public class GuessNumberDaoDB implements GuessNumberDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Game addGame(Game game) {
        final String sql = "INSERT INTO Game(Answer) VALUES(?)";
        jdbc.update(sql,
                game.getAnswer());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newId);
        return game;
    }

    @Override
    @Transactional
    public Round addRound(Round round, Game game) {
        final String sql = "INSERT INTO Round(Guess, GameId) VALUES(?,?)";
        jdbc.update(sql,
                round.getGuess(),
                round.getGameId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newId);

        if (game.getAnswer().equals(round.getGuess())) {
            updateGame(game);
        }
        return round;
    }

    @Override
    public Round updateRound(Round round) {
        final String sql =  "UPDATE Round "
                            + "SET TimeOfGuess = ?, Result = ?  WHERE RoundId = ?";
        jdbc.update(sql,
                Timestamp.valueOf(round.getTimeOfGuess()),
                round.getResult(),
                round.getRoundId());
        return round;
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT * FROM Game";
        return jdbc.query(sql,
                new GameMapper());
    }

    @Override
    public List<Round> getAllRounds() {
        final String sql = "SELECT * FROM Round";
        return jdbc.query(sql,
                new RoundMapper());
    }

    @Override
    public Game getGameById(int gameId) {
        try {
            final String sql = "SELECT * FROM Game WHERE GameId = ?";
            return jdbc.queryForObject( sql,
                                        new GameMapper(),
                                        gameId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Round> getRoundByGameId(int gameId) {
        final String sql =  "SELECT * FROM Round "
                            + "WHERE GameId = ?  "
                            + "ORDER BY TimeOfGuess ASC;";
        List<Round> rounds = jdbc.query(sql,
                                new RoundMapper(),
                                gameId);
        return rounds;
    }

    @Override
    public void deleteTableForTest() {
        final String sql = "DELETE FROM Round ";
        jdbc.update(sql);

        final String sql2 = "DELETE FROM Game";
        jdbc.update(sql2);
    }

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("GameId"));
            game.setAnswer(rs.getString("Answer"));
            game.setStatus(rs.getString("Status"));
            return game;
        }
    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("RoundId"));
            round.setGuess(rs.getString("Guess"));
            round.setTimeOfGuess(rs.getTimestamp("TimeOfGuess").toLocalDateTime());
            round.setResult(rs.getString("Result"));
            round.setGameId(rs.getInt("GameId"));
            return round;
        }
    }

    private void updateGame(Game game) {
        final String sql =  "UPDATE Game  "
                            + "SET Status = 'finished'  "
                            + "WHERE GameId = ? ";
        jdbc.update(sql,
                game.getGameId());
    }
}
