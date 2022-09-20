package ca.lichangzhang.guessnumber.controllers;

import ca.lichangzhang.guessnumber.models.Game;
import ca.lichangzhang.guessnumber.models.Round;
import ca.lichangzhang.guessnumber.service.GameFinishedException;
import ca.lichangzhang.guessnumber.service.GameIdNotFoundException;
import ca.lichangzhang.guessnumber.service.NoRoundRecordException;
import ca.lichangzhang.guessnumber.service.ServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
@RestController
@RequestMapping("/api/guessnumber")
public class GuessNumberController {

    @Autowired
    ServiceLayer service;

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int addGame() {
        return service.addGame();
    }

    @PostMapping("/guess")
    public ResponseEntity<Round> addRound(@RequestBody Round round)
            throws GameFinishedException,
            GameIdNotFoundException {
        round = service.addRound(round);
        if (round == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(round);
    }

    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.getAllGames();
    }

    @GetMapping("game/{gameId}")
    public ResponseEntity<Game> getGameById(@PathVariable int gameId)
            throws GameIdNotFoundException {
        Game search = service.getGameById(gameId);
        if (search == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(search);
    }

    @GetMapping("rounds/{gameId}")
    public ResponseEntity<List<Round>> getRoundByGameId(@PathVariable int gameId)
            throws GameIdNotFoundException,
            NoRoundRecordException {
        Game search = service.getGameById(gameId);
        if (search == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        List<Round> games = service.getRoundByGameId(gameId);
        if (games.isEmpty()) {
            return (ResponseEntity<List<Round>>) (List<Round>) new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(games);
    }
}
