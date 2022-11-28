DROP DATABASE IF EXISTS superhero;
CREATE DATABASE superhero;

USE superhero;

CREATE TABLE power(
    powerId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE hero(
    heroId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,    
    description VARCHAR(255) NOT NULL,
    isHero BOOLEAN NOT NULL DEFAULT 1, 
    photo BLOB,
    powerId INT,
	FOREIGN KEY (powerId) REFERENCES power(powerId)
);

CREATE TABLE location (
    locationId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    address VARCHAR(200) NOT NULL,
	phone VARCHAR(11),
    latitude VARCHAR(16) NOT NULL,
	longitude VARCHAR(16) NOT NULL
   
);

CREATE TABLE sighting (
	sightingId INT PRIMARY KEY AUTO_INCREMENT,
    heroId INT NOT NULL,
    locationId INT NOT NULL,
	description VARCHAR(255) NOT NULL,
    sightingDate DATETIME NOT NULL,
    FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (locationId) REFERENCES location(locationId)
);

CREATE TABLE organization (
	organizationId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    address VARCHAR(200) NOT NULL,
    contact VARCHAR(100) NOT NULL,
	phone VARCHAR(11) 
);

CREATE TABLE hero_organization (
    heroId INT NOT NULL,
    organizationId INT NOT NULL,
    PRIMARY KEY(heroId, organizationId),
    FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (organizationId) REFERENCES organization (organizationId)
);

INSERT INTO `superhero`.`power` (`powerId`, `name`, `description`) VALUES ('1', 'Mind Control', 'Like many of the other superpowers on this list, mind control is incredibly old. Of all the superheroes who can do it, arguably the most important, Professor X, debuted over 50 years ago.');
INSERT INTO `superhero`.`power` (`powerId`, `name`, `description`) VALUES ('2', 'Under Water Control', 'For decades, jokes have been made about being able to breathe underwater.');
INSERT INTO `superhero`.`power` (`powerId`, `name`, `description`) VALUES ('3', 'Super Strength', 'Super strength is one of the most basic traditional superhero powers.');

INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`) VALUES ('1', 'Avengers', 'organization 1 description', 'address1', 'contact 1');
INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`) VALUES ('2', 'Children of Thanos', 'organization 2 description ', 'address 2', 'contact 2');
INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`) VALUES ('3', 'Dora Milaje', 'organization 3 description', 'address3', 'contact 3');
INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`) VALUES ('4', 'Guardians of the Galaxy', 'organization 4 description', 'address 4', 'contact 4');
INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`, `phone`) VALUES ('5', 'Guardians of the Multiverse', 'organization 5 description', 'address5', 'contact 5', '343244434');
INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`, `phone`) VALUES ('6', 'Howling Commandos', 'organization 6 description ', 'address 6', 'contact 6', '343244431');
INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`, `phone`) VALUES ('7', 'Revengers', 'organization 7 description', 'address7', 'contact 7', '343244432');
INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`, `phone`) VALUES ('8', 'Salem Coven', 'organization 8 description', 'address 8', 'contact 8', '343244432');

INSERT INTO `superhero`.`hero` (`heroId`, `name`, `description`, `isHero`, `powerId`) VALUES ('1', 'Batman', 'Batman is the exact opposite of how a superhero goes about business. He’s vicious, violent, and only knows a few boundaries. ', '1', '1');
INSERT INTO `superhero`.`hero` (`heroId`, `name`, `description`, `isHero`, `powerId`) VALUES ('2', 'Superman', 'Superman is the definitive superhero and as such, should be on every superheroes list that ranks superheroes.', '1', '2');
INSERT INTO `superhero`.`hero` (`heroId`, `name`, `description`, `isHero`, `powerId`) VALUES ('3', 'Spider-Man', 'Spider-Man is the face of Marvel Comics. I’d even argue that he’s more important than the Marvel logo.', '1', '3');
INSERT INTO `superhero`.`hero` (`heroId`, `name`, `description`, `isHero`, `powerId`) VALUES ('4', ' Wonder Woman', 'Wonder Woman is the epitome of female superheroes. Wonder Woman is one of the Big Three DC superheroes.', '1', '1');


INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `latitude`, `longitude`) VALUES ('1', 'Kyrgyzstan', 'Kyrgyzstan location description', 'Kyrgyzstan address', '40.7128', '74.0060');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `latitude`, `longitude`) VALUES ('2', 'Denmark', 'Denmark location description', 'Denmark address', '56.2639', '9.5018');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `latitude`, `longitude`) VALUES ('3', 'China', 'China location description', 'China address', '23.1291', '113.2644');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `latitude`, `longitude`) VALUES ('4', 'Kazakhstan', 'Kazakhstan location descro[topm', 'Kazakhstan address', '45.4215', '75.6972');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `latitude`, `longitude`) VALUES ('5', 'Netherland', 'Netherland location description', 'Netherland address', '52.1326', '5.2913');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `latitude`, `longitude`) VALUES ('6', 'Ukraine', 'Ukraine location description', 'Ukraine address', '50.4501', '30.5234');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `latitude`, `longitude`) VALUES ('7', 'Poland', 'Poland location description', 'Poland address', '51.9194', '19.1451');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `latitude`, `longitude`) VALUES ('8', 'Balearic Sea', 'Balearic Sea location description', 'Balearic Sea address', '40.4637', '3.7492');

INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('1','2');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('1','3');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('1','4');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('1','5');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('1','6');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('2','1');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('2','2');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('2','3');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('2','4');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('2','5');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('2','6');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('2','7');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('2','8');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('3','2');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('3','3');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('3','4');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('3','6');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('4','2');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('4','3');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('4','4');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('4','5');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('4','6');
INSERT INTO `superhero`.`hero_organization` (`heroId`, `organizationId`) VALUES ('4','8');

INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('1', '1', 'Hero 1 location 1 description', '2022-02-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('3', '2', 'Hero 1 location 1 description', '2020-05-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('1', '3', 'Hero 1 location 1 description', '2021-02-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('2', '4', 'Hero 1 location 1 description', '2020-06-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('1', '5', 'Hero 1 location 1 description', '2020-02-06 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('3', '6', 'Hero 1 location 1 description', '2022-02-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('4', '7', 'Hero 1 location 1 description', '2021-02-06 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('1', '8', 'Hero 1 location 1 description', '2020-09-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('4', '1', 'Hero 1 location 1 description', '2022-03-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('3', '2', 'Hero 1 location 1 description', '2021-02-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('2', '3', 'Hero 1 location 1 description', '2020-10-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('1', '4', 'Hero 1 location 1 description', '2020-12-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('2', '5', 'Hero 1 location 1 description', '2022-12-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('3', '6', 'Hero 1 location 1 description', '2022-05-02 12:12:12');
INSERT INTO `superhero`.`sighting` (`heroId`, `locationId`, `description`, `sightingDate`) VALUES ('4', '7', 'Hero 1 location 1 description', '2022-08-02 12:12:12');


