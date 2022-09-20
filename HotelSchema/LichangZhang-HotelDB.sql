DROP DATABASE IF EXISTS HotelReservation;

CREATE DATABASE IF NOT EXISTS HotelReservation;

USE HotelReservation;

CREATE TABLE `Guest` (
	`GuestId` INT PRIMARY KEY AUTO_INCREMENT,
	`FirstName` VARCHAR(50) NOT NULL,
    `LastName` VARCHAR(50) NOT NULL,
    `Address` VARCHAR(256) NOT NULL,
    `City` VARCHAR(100) NOT NULL,
    `State` CHAR(2) NOT NULL,
    `Zip` CHAR(6) NOT NULL,
     `Phone` VARCHAR(14) NOT NULL
);

CREATE TABLE `RoomType` (
	`RoomTypeId` INT PRIMARY KEY AUTO_INCREMENT,
	`RoomTypeName` VARCHAR(50) NOT NULL,
    `StandardPrice` DECIMAL(12,2) NOT NULL,
    `StandardOccupancy` TINYINT(1) NOT NULL,
    `MaximumOccupancy` TINYINT(1) NOT NULL,
    `ExtraPersonPrice` DECIMAL(12,2) 
);

CREATE TABLE `Amenity` (
	`AmenityId` INT PRIMARY KEY AUTO_INCREMENT,
	`AmenityName` VARCHAR(50) NOT NULL,
    `AmenityPrice` DECIMAL(12,2) 
);

CREATE TABLE `Room` (
	`RoomId` INT PRIMARY KEY AUTO_INCREMENT,	
    `RoomNumber` VARCHAR(8) NOT NULL,
    `IsADA` BOOL NOT NULL DEFAULT 0,
    `RoomTypeId` INT NOT NULL,
    CONSTRAINT fk_Room_RoomType -- constraint name     
    FOREIGN KEY fk_Room_RoomType (RoomTypeId)
		REFERENCES RoomType (RoomTypeId)
);

CREATE TABLE `Reservation` (
	`ReservationId` INT PRIMARY KEY AUTO_INCREMENT,	
    `StartDate` DATE NOT NULL,
    `EndDate` DATE NOT NULL,
    `GuestId` INT NOT NULL,
	CONSTRAINT fk_Reservation_Guest -- constraint name     
    FOREIGN KEY fk_Reservation_Guest (GuestId)
		REFERENCES Guest (GuestId)
);

CREATE TABLE `RoomReservation` (
	`ReservationId` INT NOT NULL,
	`RoomId` INT NOT NULL,	
	`NumberOfAdult` TINYINT(1) NOT NULL,
    `NumberOfChildren`TINYINT(1) NOT NULL,
    PRIMARY KEY pk_RoomReservation (ReservationId, RoomId), -- create the composite key   
    CONSTRAINT fk_RoomReservation_Reservation
        FOREIGN KEY fk_RoomReservation_Reservation (ReservationId)
        REFERENCES Reservation(ReservationId),
	CONSTRAINT fk_RoomReservation_Room -- constraint name
        FOREIGN KEY fk_RoomReservation_Room (RoomId) -- FK index name
        REFERENCES Room(RoomId)
);

CREATE TABLE  `RoomAmenity` (
	`RoomId` INT NOT NULL,	
	`AmenityId` INT NOT NULL,
    PRIMARY KEY pk_RoomAmenity (RoomId, AmenityId), -- create the composite key
    CONSTRAINT fk_RoomAmenity_Room -- constraint name
        FOREIGN KEY fk_RoomAmenity_Room (RoomId) -- FK index name
        REFERENCES Room(RoomId),
    CONSTRAINT fk_RoomAmenity_Amenity
        FOREIGN KEY fk_RoomAmenity_Amenity (AmenityId)
        REFERENCES Amenity(AmenityId)
);