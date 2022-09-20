package ca.lichangzhang.guessnumber.data;

import ca.lichangzhang.guessnumber.models.Game;
import ca.lichangzhang.guessnumber.models.Round;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author catzh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GuessNumberDaoDBTest {

    @Autowired
    GuessNumberDao guessNumberDao;

    public GuessNumberDaoDBTest() {
    }

    @Before
    public void setUp() {
        guessNumberDao.deleteTableForTest();
    }

    /**
     * Test of deleteTableForTest method, of class GuessNumberDaoDB. //
     */
    @Test
    public void deleteTableForTest() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("in progress");
        game = guessNumberDao.addGame(game);

        Round round = new Round();
        round.setGuess("9876");
        round.setGameId(game.getGameId());
        guessNumberDao.addRound(round, game);

        guessNumberDao.deleteTableForTest();

        List<Game> gameFromDao = guessNumberDao.getAllGames();
        List<Round> roundFromDao = guessNumberDao.getAllRounds();

        assertEquals(true, gameFromDao.isEmpty());
        assertEquals(true, roundFromDao.isEmpty());
    }

    /**
     * Test of addGame method, of class GuessNumberDaoDB.
     */
    @Test
    public void testAddGetGame() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("in progress");
        game = guessNumberDao.addGame(game);

        Game fromDao = guessNumberDao.getGameById(game.getGameId());
        assertEquals(game, fromDao);
    }

    /**
     * Test of addRound method, of class GuessNumberDaoDB.
     */
    @Test
    public void testAddRound() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("in progress");
        game = guessNumberDao.addGame(game);

        Round round = new Round();
        round.setGuess("9876");
        round.setGameId(game.getGameId());
        Round fromDao = guessNumberDao.addRound(round, game);

        assertEquals(round, fromDao);
    }

    /**
     * Test of getAllGames method, of class GuessNumberDaoDB.
     */
    @Test
    public void testGetAllGames() {

        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("in progress");
        guessNumberDao.addGame(game);

        Game game2 = new Game();
        game2.setAnswer("5678");
        game2.setStatus("in progress");
        guessNumberDao.addGame(game2);

        List<Game> games = guessNumberDao.getAllGames();

        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    /**
     * Test of getGameById method, of class GuessNumberDaoDB.
     */
    @Test
    public void testGetGameById() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("in progress");
        game = guessNumberDao.addGame(game);

        Game fromDao = guessNumberDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);
    }
    /**
     * Test of getAllGames method, of class GuessNumberDaoDB.
     */
    @Test
    public void testGetAllRounds() {
        LocalDateTime dateTime = LocalDateTime.now();

        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("in progress");
        game = guessNumberDao.addGame(game);

        Round round = new Round();
        round.setGuess("9876");
        round.setTimeOfGuess(dateTime);
        round.setResult("e:0:p:0");
        round.setGameId(game.getGameId());
        Round fromDao = guessNumberDao.addRound(round, game);

        Round round2 = new Round();
        round2.setGuess("4321");
        round2.setTimeOfGuess(dateTime);
        round2.setResult("e:0:p:0");
        round2.setGameId(game.getGameId());
        Round fromDao2 = guessNumberDao.addRound(round2, game);

        List<Round> rounds = guessNumberDao.getAllRounds();

        assertEquals(2, rounds.size());
        assertEquals(round, fromDao);
        assertEquals(round2, fromDao2);
    }

    /**
     * Test of getRoundByGameId method, of class GuessNumberDaoDB.
     */
    @Test
    public void testGetRoundByGameId() {
        LocalDateTime dateTime = LocalDateTime.now();
        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("in progress");
        game = guessNumberDao.addGame(game);

        Round round = new Round();
        round.setGuess("9876");
        round.setGameId(game.getGameId());
        Round fromDao = guessNumberDao.addRound(round, game);

        Round round2 = new Round();
        round2.setGuess("1234");
        round2.setGameId(game.getGameId());
        Round fromDao2 = guessNumberDao.addRound(round2, game);

        List<Round> rounds = guessNumberDao.getRoundByGameId(game.getGameId());

        assertEquals(2, rounds.size());
        assertEquals(round, fromDao);
        assertEquals(round2, fromDao2);
    }
}
