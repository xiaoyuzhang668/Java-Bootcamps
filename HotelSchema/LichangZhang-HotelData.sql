USE HotelReservation;

INSERT INTO  Guest (FirstName,LastName,Address,City,State,Zip,Phone)
VALUES
('Cathy (Li Chang)','Zhang','123 Rainforest Dr. ','Ottawa','ON','K1V1L6','(123) 456-7890'),
('Mack','Simmer','379 Old Shore Street','Council Bluffs','IA','51501','(291) 553-0508'),
('Bettyann','Seery','750 Wintergreen Dr.','Wasilla','AK','99654','(478) 277-9632'),
('Duane','Cullison','9662 Foxrun Lane','Harlingen','TX','78552','(308) 494-0198'),
('Karie','Yang','9378 W. Augusta Ave.','West Deptford','NJ','8096','(214) 730-0298'),
('Aurore','Lipton','762 Wild Rose Street','Saginaw','MI','48601','(377) 507-0974'),
('Zachery','Luechtefeld','7 Poplar Dr.','Arvada','CO','80003','(814) 485-2615'),
('Jeremiah','Pendergrass','70 Oakwood St.','Zion','IL','60099','(279) 491-0960'),
('Walter','Holaway','7556 Arrowhead St.','Cumberland','RI','2864','(446) 396-6785'),
('Wilfred','Vise','77 West Surrey Street','Oswego','NY','13126','(834) 727-1001'),
('Maritza','Tilton','939 Linda Rd.','Burke','VA','22015','(446) 351-6860'),
('Joleen','Tison','87 Queen St.','Drexel Hill','PA','19026','(231) 893-2755');

INSERT INTO  RoomType 
(RoomTypeName,StandardPrice,StandardOccupancy,MaximumOccupancy,ExtraPersonPrice)
VALUES
('Single',149.99,2,2,0),
('Double',174.99,2,4,10),
('Suite',399.99,3,8,20);

INSERT INTO  Amenity 
(AmenityName,AmenityPrice)
VALUES
('Microwave', 0),
('Jacuzzi', 25),
('Refrigerator', 0),
('Oven', 0);

INSERT INTO  Room 
(RoomNumber,IsADA,RoomTypeId)
VALUES
('201',0,2),
('202',1,2),
('203',0,2),
('204',1,2),
('205',0,1),
('206',1,1),
('207',0,1),
('208',1,1),
('301',0,2),
('302',1,2),
('303',0,2),
('304',1,2),
('305',0,1),
('306',1,1),
('307',0,1),
('308',1,1),
('401',1,3),
('402',1,3);

INSERT INTO  Reservation 
(`StartDate`,`EndDate`,`GuestId`) 
VALUES
('2023-02-02','2023-02-04',2),
('2023-02-05','2023-02-10',3),
('2023-02-22','2023-02-24',4),
('2023-03-06','2023-03-07',5),
('2023-03-17','2023-03-20',1),
('2023-03-18','2023-03-23',6),
('2023-03-29','2023-03-31',7),
('2023-03-31','2023-04-05',8),
('2023-04-09','2023-04-13',9),
('2023-04-23','2023-04-24',10),
('2023-05-30','2023-06-02',11),
('2023-06-10','2023-06-14',12),
('2023-06-17','2023-06-18',6),
('2023-06-28','2023-07-02',1),
('2023-07-13','2023-07-14',9),
('2023-07-18','2023-07-21',10),
('2023-07-28','2023-07-29',3),
('2023-08-30','2023-09-01',3),
('2023-09-16','2023-09-17',2),
('2023-09-13','2023-09-15',5),
('2023-11-22','2023-11-25',4),
('2023-11-22','2023-11-25',2),
('2023-12-24','2023-12-28',11);

INSERT INTO RoomReservation 
(`ReservationId`, `RoomId`,`NumberOfAdult`, `NumberOfChildren`)
VALUES
(1,16,1,0),
(2,3,2,1),
(3,13,2,0),
(4,1,2,2),
(5,15,1,1),
(6,10,3,0),
(7,2,2,2),
(8,12,2,0),
(9,9,1,0),
(10,7,1,1),
(11,17,2,4),
(12,6,2,0),
(12,8,1,0),
(13,12,3,0),
(14,5,2,0),
(15,4,3,1),
(16,17,4,2),
(17,11,2,1),
(18,13,1,0),
(19,8,2,0),
(20,3,2,2),
(21,17,2,2),
(22,6,2,0),
(22,9,2,2),
(23,10,2,0)
;

INSERT INTO RoomAmenity 
(`RoomId`,`AmenityId`)
VALUES
(1,1),
(1,2),
(2,3),
(3,1),
(3,2),
(4,3),
(5,1),
(5,3),
(5,2),
(6,1),
(6,3),
(7,1),
(7,3),
(7,2),
(8,1),
(8,3),
(9,1),
(9,2),
(10,3),
(11,1),
(11,2),
(12,3),
(13,1),
(13,3),
(13,2),
(14,1),
(14,3),
(15,1),
(15,3),
(15,2),
(16,1),
(16,3),
(17,1),
(17,3),
(17,4),
(18,1),
(18,3),
(18,4);


SET SQL_SAFE_UPDATES = 0;

DELETE 
FROM RoomReservation
WHERE ReservationId IN (
	SELECT ReservationId FROM Reservation AS R
    INNER JOIN Guest AS G ON R.GuestId = G.GuestId
    WHERE (FirstName LIKE 'Jeremiah' AND LastName LIKE 'Pendergrass')
    );

DELETE
FROM Reservation 
WHERE GuestId IN (
SELECT GuestId FROM Guest
WHERE (FirstName LIKE 'Jeremiah' AND LastName LIKE 'Pendergrass')
);

DELETE
FROM Guest
WHERE (FirstName LIKE 'Jeremiah' AND LastName LIKE 'Pendergrass');

SET SQL_SAFE_UPDATES = 1;











