DROP DATABASE IF EXISTS superherotest;
CREATE DATABASE superherotest;

USE superherotest;

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
    powerId INT,
	FOREIGN KEY (powerId) REFERENCES power(powerId)
);

CREATE TABLE location (
    locationId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    address VARCHAR(200) NOT NULL,
    latitude DECIMAL(12,9) NOT NULL,
	longitude DECIMAL(12,9) NOT NULL
);

CREATE TABLE sighting (
	sightingId INT PRIMARY KEY AUTO_INCREMENT,
    heroId INT NOT NULL,
    locationId INT,
	description VARCHAR(255) NOT NULL,
    sightingDate DATE NOT NULL,
    FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (locationId) REFERENCES location(locationId)
);

CREATE TABLE organization (
	organizationId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    address VARCHAR(200) NOT NULL,
    contact VARCHAR(100) NOT NULL
);

CREATE TABLE hero_organization (
    heroId INT NOT NULL,
    organizationId INT NOT NULL,
    PRIMARY KEY(heroId, organizationId),
    FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (organizationId) REFERENCES organization (organizationId)
);