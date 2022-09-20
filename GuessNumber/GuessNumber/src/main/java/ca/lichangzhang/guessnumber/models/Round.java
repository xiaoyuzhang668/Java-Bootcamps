package ca.lichangzhang.guessnumber.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public class Round {

    int roundId;
    String guess;
    LocalDateTime timeOfGuess;
    String result;
    int gameId;

    public Round() {

    }

    public Round(int roundId, String guess, int gameId) {
        this.roundId = roundId;
        this.guess = guess;
        this.gameId = gameId;
    }  
    
  
    public Round(int roundId, String guess, LocalDateTime timeOfGuess, String result, int gameId) {
        this.roundId = roundId;
        this.guess = guess;
        this.timeOfGuess = timeOfGuess;
        this.result = result;
        this.gameId = gameId;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public LocalDateTime getTimeOfGuess() {
        return timeOfGuess;
    }

    public void setTimeOfGuess(LocalDateTime timeOfGuess) {
        this.timeOfGuess = timeOfGuess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "Round{" + "roundId=" + roundId + ", guess=" + guess + ", timeOfGuess=" + timeOfGuess + ", result=" + result + ", gameId=" + gameId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.roundId;
        hash = 37 * hash + Objects.hashCode(this.guess);
        hash = 37 * hash + Objects.hashCode(this.timeOfGuess);
        hash = 37 * hash + Objects.hashCode(this.result);
        hash = 37 * hash + this.gameId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.timeOfGuess, other.timeOfGuess)) {
            return false;
        }
        return true;
    }

}
