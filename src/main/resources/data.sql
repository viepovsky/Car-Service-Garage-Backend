INSERT INTO GARAGE (id, name, address) VALUES
(1, 'Fast Furious Garage', 'Poznan 62-222, Jana street'),
(2, 'Prestige Car Garage', 'Warsaw 60-333, Halna street');

INSERT INTO AVAILABLE_CAR_REPAIRS (id, name, description, cost, repair_time, premium_make_list, make_multiplier, garage_id) VALUES
(1, 'Oil change', 'Oil change description', 100.00, 30, 'BMW, AUDI, MERCEDES', 1.20, 1),
(2, 'Tire change', 'Tire change description', 200.00, 50, '', 1.20, 1),
(3, 'Wheel change', 'Wheel change description', 120.00, 30, '', 1.20, 1),
(4, 'Timing belt change', 'Timing belt change description', 1500.00, 180, 'BMW, AUDI, MERCEDES', 1.40, 1),
(5, 'Pre-purchase car inspection', 'Pre-purchase description', 300.00, 60, 'BMW, AUDI, MERCEDES', 1.20, 1),
(6, 'Replace air filter', 'Replace air filter description', 60.00, 15, '', 1.20, 1),
(7, 'Replace cabin filter', 'Replace cabin filter description', 50.00, 10, '', 1.20, 1),
(8, 'Oil change', 'Oil change description', 110.00, 30, 'BMW, AUDI, MERCEDES', 1.20, 2),
(9, 'Tire change', 'Tire change description', 220.00, 50, '', 1.20, 2),
(10, 'Wheel change', 'Wheel change description', 130.00, 30, '', 1.20, 2),
(11, 'Timing belt change', 'Timing belt change description', 1700.00, 180, 'BMW, AUDI, MERCEDES', 1.40, 2),
(12, 'Pre-purchase car inspection', 'Pre-purchase description', 350.00, 60, 'BMW, AUDI, MERCEDES', 1.20, 2),
(13, 'Replace air and cabin filter', 'Replace air and cabin description', 150.00, 25, '', 1.20, 2);

INSERT INTO GARAGE_WORK_TIMES (id, work_day, start_hour, end_hour, garage_id) VALUES
(1, 'MONDAY', TIME '07:00:00', TIME '15:00:00', 1),
(2, 'TUESDAY', TIME '07:00:00', TIME '15:00:00', 1),
(3, 'WEDNESDAY', TIME '07:00:00', TIME '15:00:00', 1),
(4, 'THURSDAY', TIME '07:00:00', TIME '15:00:00', 1),
(5, 'FRIDAY', TIME '07:00:00', TIME '15:00:00', 1),
(6, 'SATURDAY', TIME '09:00:00', TIME '13:00:00', 1),
(7, 'SUNDAY', TIME '00:00:00', TIME '00:00:00', 1),
(8, 'MONDAY', TIME '08:00:00', TIME '16:00:00', 2),
(9, 'TUESDAY', TIME '08:00:00', TIME '16:00:00', 2),
(10, 'WEDNESDAY', TIME '08:00:00', TIME '16:00:00', 2),
(11, 'THURSDAY', TIME '08:00:00', TIME '16:00:00', 2),
(12, 'FRIDAY', TIME '08:00:00', TIME '16:00:00', 2),
(13, 'SATURDAY', TIME '08:00:00', TIME '12:00:00', 2),
(14, 'SUNDAY', TIME '00:00:00', TIME '00:00:00', 2);

INSERT INTO USERS (id, first_name, last_name, email, phone_number, username, password, role, created_date) VALUES
(1, 'testName', 'testLastName', 'test@email.com', '00444444444', 'testuser', '$2a$12$wVZjTRY0adwXJQh6U3cGHu44Mu65camSrYpgMZIpawuFWQVnnB4lG', 'ROLE_USER', CURRENT_TIMESTAMP),
(2, 'testName', 'testLastName', 'test@email.com', '00555555555', 'testadmin', '$2a$12$wVZjTRY0adwXJQh6U3cGHu44Mu65camSrYpgMZIpawuFWQVnnB4lG', 'ROLE_ADMIN', CURRENT_TIMESTAMP);

INSERT INTO CARS (id, make, model, production_year, type, engine, user_id) VALUES
(1, 'BMW', '3 Series', 2014, 'Sedan', 'Diesel', 1),
(2, 'Volvo', 'XC60', 2018, 'SUV', 'Diesel', 1);

CREATE SEQUENCE IF NOT EXISTS bookings_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS date_seq START WITH 0 INCREMENT BY 1 MINVALUE 0;
CREATE SEQUENCE IF NOT EXISTS date2_seq START WITH 0 INCREMENT BY 1 MINVALUE 0;

INSERT INTO BOOKING (id, status, date, garage_id) VALUES
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date_seq') || ' days' AS INTERVAL), 1),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2),
(NEXTVAL('bookings_seq'), 3 , CURRENT_DATE + CAST(NEXTVAL('date2_seq') || ' days' AS INTERVAL), 2);

UPDATE BOOKING SET status = 4 WHERE EXTRACT(DOW FROM date) = 7;

UPDATE BOOKING SET start_hour = '07:00:00' WHERE EXTRACT(DOW FROM date) < 6 AND garage_id = 1;
UPDATE BOOKING SET start_hour = '09:00:00' WHERE EXTRACT(DOW FROM date) = 6 AND garage_id = 1;
UPDATE BOOKING SET start_hour = '00:00:00' WHERE EXTRACT(DOW FROM date) = 7 AND garage_id = 1;
UPDATE BOOKING SET end_hour = '15:00:00' WHERE EXTRACT(DOW FROM date) < 6 AND garage_id = 1;
UPDATE BOOKING SET end_hour = '13:00:00' WHERE EXTRACT(DOW FROM date) = 6 AND garage_id = 1;
UPDATE BOOKING SET end_hour = '00:00:00' WHERE EXTRACT(DOW FROM date) = 7 AND garage_id = 1;

UPDATE BOOKING SET start_hour = '08:00:00' WHERE EXTRACT(DOW FROM date) <= 6 AND garage_id = 2;
UPDATE BOOKING SET start_hour = '00:00:00' WHERE EXTRACT(DOW FROM date) = 7 AND garage_id = 2;
UPDATE BOOKING SET end_hour = '16:00:00' WHERE EXTRACT(DOW FROM date) < 6 AND garage_id = 2;
UPDATE BOOKING SET end_hour = '12:00:00' WHERE EXTRACT(DOW FROM date) = 6 AND garage_id = 2;
UPDATE BOOKING SET end_hour = '00:00:00' WHERE EXTRACT(DOW FROM date) = 7 AND garage_id = 2;

INSERT INTO BOOKING (id, status, date, start_hour, end_hour, total_cost, garage_id) VALUES
(123, 0, CURRENT_DATE + INTERVAL '5 days', '07:50:00', '09:10:00', 320.00, 1),
(124, 0, CURRENT_DATE + INTERVAL '9 days', '09:40:00', '12:40:00', 1700.00, 2),
(125, 2, CURRENT_DATE + INTERVAL '-10 days', '12:20:00', '15:20:00', 2100.00, 2),
(126, 4, CURRENT_DATE, '03:00:00', '23:30:00', 5500.00, 1);

UPDATE BOOKING SET date = CURRENT_DATE + INTERVAL '7 days' WHERE EXTRACT(DOW FROM date) >= 6 AND status = 0;
UPDATE BOOKING SET date = CURRENT_DATE + INTERVAL '-12 days' WHERE EXTRACT(DOW FROM date) >= 6 AND status = 2;

INSERT INTO CARS_REPAIRS (id, name, description, cost, repair_time, car_id, user_id, booking_id, service_status) VALUES
(1, 'Oil change', 'Oil change description', 120.00, 30, 1, 1, 123, 2),
(2, 'Tire change', 'Tire change description', 200.00, 50, 1, 1, 123, 2),
(3, 'Timing belt change', 'Timing belt change description', 1700.00, 180, 2, 1, 124, 2),
(4, 'Timing belt change', 'Timing belt change description', 2100.00, 180, 1, 1, 125, 6),
(5, 'Individual repair', 'Individual repair description', 5500.00, 1230, 1, 1, 126, 5);