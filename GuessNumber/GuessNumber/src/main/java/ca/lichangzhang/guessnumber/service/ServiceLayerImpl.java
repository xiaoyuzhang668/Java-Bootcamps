package ca.lichangzhang.guessnumber.service;

import ca.lichangzhang.guessnumber.data.GuessNumberDao;
import ca.lichangzhang.guessnumber.models.Game;
import ca.lichangzhang.guessnumber.models.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
@Service
public class ServiceLayerImpl implements ServiceLayer {

    @Autowired
    GuessNumberDao guessNumberDao;

    @Override
    public int addGame() {
        Game game = new Game();
        game.setAnswer(getNonDuplicate(4, 0, 9));
        return guessNumberDao.addGame(game).getGameId();
    }

    @Override
    public Round addRound(Round round) throws
            GameFinishedException,
            GameIdNotFoundException {
        LocalDateTime dateTime = LocalDateTime.now();
        Game game = guessNumberDao.getGameById(round.getGameId());

        if (game == null) {
            throw new GameIdNotFoundException("Game with id number of  " + round.getGameId() + " does not exist!");
        }

        if (game.getStatus().equals("finished")) {
            throw new GameFinishedException(
                    "Game with id number of  " + game.getGameId() + " is finished already!");
        }

        guessNumberDao.addRound(round, game);

        round.setResult(calculateResult(round.getGuess(), game.getAnswer()));
        round.setTimeOfGuess(dateTime);
        return guessNumberDao.updateRound(round);
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> games = guessNumberDao.getAllGames();
        for (Game game : games) {
            if (game.getStatus().equals("in progress")) {
                game.setAnswer("####");
            }
        }
        return games;
    }

    @Override
    public Game getGameById(int gameId) throws GameIdNotFoundException {

        Game search = guessNumberDao.getGameById(gameId);
        if (search == null) {
            throw new GameIdNotFoundException("Game with id number of  " + gameId + " does not exist!");
        }
        if (search.getStatus().equals("in progress")) {
            search.setAnswer("####");
        }
        return search;
    }

    @Override
    public List<Round> getRoundByGameId(int gameId) throws 
            GameIdNotFoundException,
            NoRoundRecordException {
        List<Round> rounds = guessNumberDao.getRoundByGameId(gameId);
        if (getGameById(gameId) == null ) {
            throw new GameIdNotFoundException("Game with id number of " + gameId + " does not exist.");
         }
        if (rounds.isEmpty()) {
            throw new NoRoundRecordException ("No rounds is found for game id  " + gameId + "!");
        }
        return rounds;
    }

    // get randomized string
    private static String getNonDuplicate(int size, int min, int max) {
        ArrayList numbers = new ArrayList();
        String answer = "";
        Random random = new Random();
        while (numbers.size() < size) {

            int randomNumber = random.nextInt((max - min) + 1) + min;

            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }
        for (int i = 0; i < numbers.size(); i++) {
            answer = (answer + Integer.toString((int) numbers.get(i))).trim();
        }
        return answer;
    }

    // compare answer and guess
    public static String calculateResult(String guess, String answer) {
        int exactMatch = 0;
        int partialMatch = 0;
        String result = null;

        if (Objects.equals(guess, answer) == false) {
            for (int i = 0; i < 4; i++) {
                if (guess.charAt(i) == answer.charAt(i)) {
                    exactMatch++;
                } else {
                    if (guess.indexOf(answer.charAt(i)) != -1) {
                        partialMatch++;
                    }
                }
            }
            result = "e:" + exactMatch + ":p:" + partialMatch;
        } else {
            result = "e:4:p:0";
        }
        return result;
    }
}
