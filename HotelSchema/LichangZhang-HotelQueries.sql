USE HotelReservation;

-- 1. Write a query that returns a list
-- of reservations that end in July 2023, 
-- including the name of the guest, the room number(s), 
-- and the reservation dates. - 4

SELECT CONCAT(FirstName,' ', LastName) AS Name, RoomNumber, StartDate, EndDate
FROM Reservation AS RV
INNER JOIN Guest AS G ON RV.GuestId = G.GuestId
INNER JOIN RoomReservation AS RR ON RR.ReservationId = RV.ReservationId
INNER JOIN Room AS R ON R.RoomId = RR.RoomId
WHERE EndDate BETWEEN '2023-07-01' AND '2023-07-31'
ORDER BY EndDate;


-- RESULT is 4 records as the following:
-- Cathy (Li Chang) Zhang	205	2023-06-28	2023-07-02
-- Walter Holaway	204	2023-07-13	2023-07-14
-- Wilfred Vise	401	2023-07-18	2023-07-21
-- Bettyann Seery	303	2023-07-28	2023-07-29



-- 2. Write a query that returns a list 
-- of all reservations for rooms with a jacuzzi, 
-- displaying the guest's name, the room number, 
-- and the dates of the reservation. - 11

SELECT CONCAT(FirstName,' ',LastName) AS Name, RoomNumber, 
(SELECT GROUP_CONCAT(AmenityName SEPARATOR ', ') AS Amenites FROM Amenity AS A
INNER JOIN RoomAmenity AS RA ON RA.AmenityId = A.AmenityId
WHERE R.RoomId = RA.RoomId
GROUP BY RA.RoomId
) AS Amenities,
StartDate, EndDate
FROM Guest AS G
INNER JOIN Reservation AS RV ON RV.GuestId = G.GuestId
INNER JOIN RoomReservation AS RR on RR.ReservationId = RV.ReservationId
INNER JOIN Room AS R ON R.RoomId = RR.RoomId
INNER JOIN RoomAmenity AS RA ON RA.RoomId = R.RoomId
INNER JOIN Amenity AS A ON A.AmenityId = RA.AmenityId
WHERE AmenityName = 'Jacuzzi'
ORDER BY StartDate;


-- RESULT is 11 records as the following:
-- Bettyann Seery	203	Microwave, Jacuzzi	2023-02-05	2023-02-10
-- Duane Cullison	305	Microwave, Jacuzzi, Refrigerator	2023-02-22	2023-02-24
-- Karie Yang	201	Microwave, Jacuzzi	2023-03-06	2023-03-07
-- Cathy (Li Chang) Zhang	307	Microwave, Jacuzzi, Refrigerator	2023-03-17	2023-03-20
-- Walter Holaway	301	Microwave, Jacuzzi	2023-04-09	2023-04-13
-- Wilfred Vise	207	Microwave, Jacuzzi, Refrigerator	2023-04-23	2023-04-24
-- Cathy (Li Chang) Zhang	205	Microwave, Jacuzzi, Refrigerator	2023-06-28	2023-07-02
-- Bettyann Seery	303	Microwave, Jacuzzi	2023-07-28	2023-07-29
-- Bettyann Seery	305	Microwave, Jacuzzi, Refrigerator	2023-08-30	2023-09-01
-- Karie Yang	203	Microwave, Jacuzzi	2023-09-13	2023-09-15
-- Mack Simmer	301	Microwave, Jacuzzi	2023-11-22	2023-11-25



-- 3. Write a query that returns all the rooms reserved 
-- for a specific guest, including the guest's name, 
-- the room(s) reserved, the starting date of the reservation, 
-- and how many people were included in the reservation. 
-- (Choose a guest's name from the existing data.) - 4 for Mack Simmer

SELECT  CONCAT(FirstName,' ',LastName) AS Name, RoomNumber, StartDate, NumberOfAdult, NumberOfChildren,
		NumberOfAdult + NumberOfChildren AS TotalNumberOfPeople
FROM Guest AS G
INNER JOIN Reservation AS RV ON RV.GuestId = G.GuestId
INNER JOIN RoomReservation AS RR on RR.ReservationId = RV.ReservationId
INNER JOIN Room AS R ON R.RoomId = RR.RoomId 
WHERE (FirstName LIKE 'Mack' AND LastName LIKE 'Simmer')
ORDER BY StartDate; 


-- RESULT is 4 records for Mack Simmer as the following:
-- Mack Simmer	308	2023-02-02	1	0	1
-- Mack Simmer	208	2023-09-16	2	0	2
-- Mack Simmer	206	2023-11-22	2	0	2
-- Mack Simmer	301	2023-11-22	2	2	4



-- 4. Write a query that returns a list of rooms, reservation ID, 
-- and per-room cost for each reservation. 
-- The results should include all rooms, 
-- whether or not there is a reservation associated with the room. - 26
-- BasePrice = StandardPrice + Jacuzzi price
-- TotalRoomCost = (BasePrice + ExtraPersonPrice) * (EndDate - StartDate)

SELECT RoomNumber, RR.ReservationId, CONCAT(FirstName,' ',LastName) AS Name, 
   NumberOfAdult, NumberOfChildren, StartDate, EndDate, 
CASE WHEN (NumberOfAdult > B.StandardOccupancy) 
			THEN ((NumberOfAdult - StandardOccupancy)*B.ExtraPersonPrice + B.BasePrice)*(DATEDIFF(EndDate, StartDate)) 
	 ELSE  BasePrice*(DATEDIFF(EndDate, StartDate))  
END AS TotalRoomCost 
FROM 
(
SELECT R.RoomId, RoomNumber, RoomTypeName, StandardOccupancy, StandardPrice,
(( SELECT SUM(IFNULL(AmenityPrice, 0)) FROM Amenity AS A
INNER JOIN RoomAmenity AS RA ON RA.AmenityId = A.AmenityId
WHERE RA.RoomId = R.RoomId
GROUP BY RA.RoomId
)  + StandardPrice) AS BasePrice,
ExtraPersonPrice 
FROM Amenity  AS A
       INNER JOIN RoomAmenity AS RA ON A.AmenityId = RA.AmenityId
       INNER JOIN Room AS R ON R.RoomId = RA.RoomId
       INNER JOIN RoomType AS RT ON RT.RoomTypeId = R.RoomTypeId
       GROUP BY RA.RoomId
) AS B
LEFT JOIN RoomReservation AS RR ON RR.RoomId = B.RoomId
LEFT JOIN Reservation AS RV ON RV.ReservationId = RR.ReservationId
LEFT JOIN Guest AS G ON G.GuestId = RV.GuestId
ORDER BY StartDate;


-- RESULT is 26 records as the following:
-- 402							
-- 306							
-- 308	1	Mack Simmer	1	0	2023-02-02	2023-02-04	299.98
-- 203	2	Bettyann Seery	2	1	2023-02-05	2023-02-10	999.95
-- 305	3	Duane Cullison	2	0	2023-02-22	2023-02-24	349.98
-- 201	4	Karie Yang	2	2	2023-03-06	2023-03-07	199.99
-- 307	5	Cathy (Li Chang) Zhang	1	1	2023-03-17	2023-03-20	524.97
-- 302	6	Aurore Lipton	3	0	2023-03-18	2023-03-23	924.95
-- 202	7	Zachery Luechtefeld	2	2	2023-03-29	2023-03-31	349.98
-- 301	9	Walter Holaway	1	0	2023-04-09	2023-04-13	799.96
-- 207	10	Wilfred Vise	1	1	2023-04-23	2023-04-24	174.99
-- 401	11	Maritza Tilton	2	4	2023-05-30	2023-06-02	1199.97
-- 208	12	Joleen Tison	1	0	2023-06-10	2023-06-14	599.96
-- 206	12	Joleen Tison	2	0	2023-06-10	2023-06-14	599.96
-- 304	13	Aurore Lipton	3	0	2023-06-17	2023-06-18	184.99
-- 205	14	Cathy (Li Chang) Zhang	2	0	2023-06-28	2023-07-02	699.96
-- 204	15	Walter Holaway	3	1	2023-07-13	2023-07-14	184.99
-- 401	16	Wilfred Vise	4	2	2023-07-18	2023-07-21	1259.97
-- 303	17	Bettyann Seery	2	1	2023-07-28	2023-07-29	199.99
-- 305	18	Bettyann Seery	1	0	2023-08-30	2023-09-01	349.98
-- 203	20	Karie Yang	2	2	2023-09-13	2023-09-15	399.98
-- 208	19	Mack Simmer	2	0	2023-09-16	2023-09-17	149.99
-- 401	21	Duane Cullison	2	2	2023-11-22	2023-11-25	1199.97
-- 301	22	Mack Simmer	2	2	2023-11-22	2023-11-25	599.97
-- 206	22	Mack Simmer	2	0	2023-11-22	2023-11-25	449.97
-- 302	23	Maritza Tilton	2	0	2023-12-24	2023-12-28	699.96



-- 5. Write a query that returns all the rooms accommodating 
-- at least three guests and that are reserved on any date in April 2023. - 1

SELECT R.RoomId, RoomNumber, R.RoomTypeId, MaximumOccupancy, StartDate, EndDate 
FROM Room AS R
INNER JOIN RoomReservation AS RR ON RR.RoomId = R.RoomId
INNER JOIN RoomType AS RT ON RT.RoomTypeId = R.RoomTypeId
INNER JOIN Reservation AS RV ON RV.ReservationId = RR.ReservationId
WHERE (MaximumOccupancy >= 3 AND 
(EXTRACT(MONTH FROM StartDate) = 4 OR EXTRACT(MONTH FROM EndDate) = 4));


-- RESULT is 1 records as the following:
-- 9	301	2	4	2023-04-09	2023-04-13



-- 6. Write a query that returns a list of all guest names and 
-- the number of reservations per guest, sorted starting with the 
-- guest with the most reservations and then by the guest's last name. - 11

SELECT CONCAT(FirstName,' ',LastName) AS Name, COUNT(RV.ReservationId) AS NumerOfReservation
FROM Reservation AS RV
INNER JOIN Guest AS G ON G.GuestId = RV.GuestId
GROUP BY RV.GuestId
ORDER BY NumerOfReservation DESC, LastName;


-- RESULT is 11 records as the following:
-- Bettyann Seery	3
-- Mack Simmer	3
-- Duane Cullison	2
-- Walter Holaway	2
-- Aurore Lipton	2
-- Maritza Tilton	2
-- Wilfred Vise	2
-- Karie Yang	2
-- Cathy (Li Chang) Zhang	2
-- Zachery Luechtefeld	1
-- Joleen Tison	1



-- 7. Write a query that displays the name, address, and phone number 
-- of a guest based on their phone number.
-- Choose a phone number from the existing data.)

SELECT CONCAT(FirstName,' ',LastName) AS Name, Address, Phone
FROM Guest
WHERE Phone = "(123) 456-7890";


-- RESULT is 1 records as the following:
-- Cathy (Li Chang) Zhang	123 Rainforest Dr. 	(123) 456-7890






-- DISPLAY:
-- display a list of the repot table from the lecture material 
-- GUEST TABLE
SELECT  CONCAT(FirstName,' ',LastName) AS Name, Address, City, State, Zip, Phone
FROM Guest;


-- DISPLAY ROOM DATA
SELECT RoomNumber, RoomTypeName, GROUP_CONCAT(AmenityName SEPARATOR ', ') AS Amenites,
CASE WHEN IsADA = 1 THEN "Yes"
     ELSE "No"
END AS 'ADA Accessible', 
StandardOccupancy, MaximumOccupancy, (SUM(AmenityPrice) + StandardPrice) AS BasePrice, ExtraPersonPrice
FROM RoomType AS RT 
INNER JOIN Room AS R ON R.RoomTypeId = RT.RoomTypeId
INNER JOIN RoomAmenity AS RA ON RA.RoomId = R.RoomId
INNER JOIN Amenity AS A ON A.AmenityId = RA.AmenityId
GROUP BY RA.RoomId
ORDER BY RA.RoomId;


