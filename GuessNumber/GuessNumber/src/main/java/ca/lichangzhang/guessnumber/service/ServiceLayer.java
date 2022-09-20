package ca.lichangzhang.guessnumber.service;

import ca.lichangzhang.guessnumber.models.Game;
import ca.lichangzhang.guessnumber.models.Round;
import java.util.List;

/**　Email: xiaoyuzhang668@gmail.com
 *   Date: 2022
 *
 *  @author catzh
 */
public interface ServiceLayer {
    
    int addGame();

    Round addRound(Round round) throws 
            GameIdNotFoundException,
            GameFinishedException ;

    List<Game> getAllGames();

    Game getGameById(int gameId)  throws GameIdNotFoundException;

    List<Round> getRoundByGameId(int gameId) throws 
            GameIdNotFoundException,
            NoRoundRecordException ;
  }
