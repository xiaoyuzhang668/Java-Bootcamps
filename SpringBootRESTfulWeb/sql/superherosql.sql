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
    locationId INT,
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

INSERT INTO `superhero`.`power` (`powerId`, `name`, `description`) VALUES ('1', 'power 1', 'power 1 description');
INSERT INTO `superhero`.`power` (`powerId`, `name`, `description`) VALUES ('2', 'power 2', 'power 2 description');
INSERT INTO `superhero`.`power` (`powerId`, `name`, `description`) VALUES ('3', 'power 3', 'power 3 description');

INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`, `phone`) VALUES ('1', 'organization 1', 'organization 1 description', 'address1', 'contact 1', '343244434');
INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`, `phone`) VALUES ('2', 'organization 2', 'organization 2 description ', 'address 2', 'contact 2', '343244431');
INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`, `phone`) VALUES ('3', 'organization 3', 'organization 3 description', 'address3', 'contact 3', '343244432');
INSERT INTO `superhero`.`organization` (`organizationId`, `name`, `description`, `address`, `contact`, `phone`) VALUES ('4', 'organization 4', 'organization 4 description', 'address 4', 'contact 4', '343244432');

INSERT INTO `superhero`.`hero` (`heroId`, `name`, `description`, `isHero`, `powerId`) VALUES ('1', 'hero 1', 'hero 1 description ', '1', '1');
INSERT INTO `superhero`.`hero` (`heroId`, `name`, `description`, `isHero`, `powerId`) VALUES ('2', 'hero 2', 'hero 2 description', '2', '2');

INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `phone`, `latitude`, `longitude`) VALUES ('1', 'location 1', 'location 1 ', '12345678', 'address1', '40.7128', '74.0060');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `phone`, `latitude`, `longitude`) VALUES ('2', 'location 2', 'location 2', 'address 2', '12345678', '56.2639', '9.5018');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`,  `phone`, `latitude`, `longitude`) VALUES ('3', 'location 3', 'location 3', 'address 2', '12345678', '23.1291', '113.2644');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `phone`, `latitude`, `longitude`) VALUES ('4', 'location 4', 'location 4', 'address 2', '12345678', '45.4215', '75.6972');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `phone`, `latitude`, `longitude`) VALUES ('5', 'location 5', 'location 5', '12345678', 'address1', '52.1326', '5.2913');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `phone`, `latitude`, `longitude`) VALUES ('6', 'location 6', 'location 2', 'address 2', '12345678', '50.4501', '30.5234');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `phone`, `latitude`, `longitude`) VALUES ('7', 'location 7', 'location 3', 'address 2', '12345678', '51.9194', '19.1451');
INSERT INTO `superhero`.`location` (`locationId`, `name`, `description`, `address`, `phone`, `latitude`, `longitude`) VALUES ('8', 'location 8', 'location 4', 'address 2', '12345678', '40.4637', '3.7492');



INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('1', '1', '1', 'sighting 1', '2022-11-10');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('2', '2', '5', 'sighting 2', '2020-06-06');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('3', '1', '1', 'sighting 1', '2020-03-03');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('4', '2', '6', 'sighting 2', '2026-06-06');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('5', '1', '1', 'sighting 1', '2021-03-03');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('6', '2', '2', 'sighting 2', '2020-06-06');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('7', '1', '7', 'sighting 1', '2021-03-03');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('8', '2', '3', 'sighting 2', '2024-06-06');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('9', '1', '8', 'sighting 1', '2025-03-03');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('10', '2', '2', 'sighting 2', '2020-06-06');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('11', '1', '4', 'sighting 1', '2020-03-03');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('12', '2', '1', 'sighting 2', '2020-06-06');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('13', '1', '2', 'sighting 1', '2020-03-03');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('14', '2', '4', 'sighting 2', '2020-06-06');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('15', '1', '5', 'sighting 1', '2020-03-03');
INSERT INTO `superhero`.`sighting` (`sightingId`, `heroId`, `locationId`, `description`, `sightingDate`) VALUES ('16', '1', '3', 'sighting 2', '2029-06-06');


