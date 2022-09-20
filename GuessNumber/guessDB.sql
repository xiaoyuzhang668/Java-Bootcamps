DROP DATABASE IF EXISTS GuessDB;
CREATE DATABASE GuessDB;
USE GuessDB;

CREATE TABLE Game (
GameId INT PRIMARY KEY AUTO_INCREMENT,
Answer CHAR(4) NOT NULL,
Status VARCHAR(11) NOT NULL DEFAULT 'in progress');

CREATE TABLE Round (
RoundId INT PRIMARY KEY AUTO_INCREMENT,
Guess CHAR(4) NOT NULL,
TimeOfGuess DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
Result CHAR(18) NOT NULL DEFAULT "e:0:p:0",
GameId INT NOT NULL,
CONSTRAINT fk_Round_Game     
    FOREIGN KEY fk_Round_Game (GameId)
		REFERENCES Game (GameId));
        
INSERT INTO Game(Answer, Status) VALUES("9876", "in progress");
INSERT INTO Game(Answer, Status) VALUES("6789", "finished");
INSERT INTO Game(Answer, Status) VALUES("5678", "in progress");
INSERT INTO Game(Answer, Status) VALUES("1234", "finished");


INSERT INTO Round(Guess, TimeOfGuess, Result, GameId) VALUES("9876", "2022-08-18 10:55:19", "e:0:p:0", 1);
INSERT INTO Round(Guess, TimeOfGuess, Result, GameId) VALUES("6789", "2022-08-18 10:55:19", "e:0:p:0", 2);
INSERT INTO Round(Guess, TimeOfGuess, Result, GameId) VALUES("5678", "2022-08-18 10:55:19", "e:0:p:0", 2);
INSERT INTO Round(Guess, TimeOfGuess, Result, GameId) VALUES("1234", "2022-08-18 10:55:19", "e:0:p:0", 1);

