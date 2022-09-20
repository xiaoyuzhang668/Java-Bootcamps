package ca.lichangzhang.guessnumber.data;

import ca.lichangzhang.guessnumber.models.Game;
import ca.lichangzhang.guessnumber.models.Round;
import java.util.List;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public interface GuessNumberDao {

    Game addGame(Game game);

    Round addRound(Round round, Game game);
    Round updateRound(Round round);

    List<Game> getAllGames();

    Game getGameById(int gameId);

    List<Round> getRoundByGameId(int gameId);
    
    void deleteTableForTest();
    List<Round> getAllRounds();
}
