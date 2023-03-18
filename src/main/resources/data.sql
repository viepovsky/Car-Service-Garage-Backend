INSERT INTO "GARAGE" VALUES
(1, 'Fast Furious Garage', 'Posen 62-222, Jana street'),
(2, 'Prestige Car Garage', 'Warsaw 60-333, Halna street');
INSERT INTO "AVAILABLE_CAR_SERVICE" (id, name, description, cost, repair_time, premium_make_list, make_multiplier, garage_id) VALUES
(1, 'Oil change', 'Oil change description', 100.00, 30, 'BMW, AUDI, MERCEDES', 1.20, 1);
-- (2, 'Tire change', 'Tire change description', '200.00', 50, '', '1.20', 1),
-- (3, 'Wheel change', 'Wheel change description', '120.00', 30, '', '1.20', 1),
-- (4, 'Timing belt change', 'Timing belt change description', '1500.00', 180, 'BMW, AUDI, MERCEDES', '1.40', 1),
-- (5, 'Pre-purchase car inspection', 'Pre-purchase description', '300.00', 60, 'BMW, AUDI, MERCEDES', '1.20', 1),
-- (6, 'Replace air filter', 'Replace air filter description', '60.00', 15, '', '1.20', 1),
-- (7, 'Replace cabin filter', 'Replace cabin filter description', '50.00', 10, '', '1.20', 1),
-- (8, 'Oil change', 'Oil change description', '110.00', 30, 'BMW, AUDI, MERCEDES', '1.20', 1),
-- (9, 'Tire change', 'Tire change description', '220.00', 50, '', '1.20', 2),
-- (10, 'Wheel change', 'Wheel change description', '130.00', 30, '', '1.20', 2),
-- (11, 'Timing belt change', 'Timing belt change description', '1700.00', 180, 'BMW, AUDI, MERCEDES', '1.40', 2),
-- (12, 'Pre-purchase car inspection', 'Pre-purchase description', '350.00', 60, 'BMW, AUDI, MERCEDES', '1.20', 2),
-- (13, 'Replace air and cabin filter', 'Replace air and cabin description', '150.00', 25, '', '1.20', 2);
INSERT INTO "GARAGE_WORK_TIMES" VALUES
(1, 0, TIME '07:00:00', TIME '15:00:00', 1),
(2, 1, TIME '07:00:00', TIME '15:00:00', 1),
(3, 2, TIME '07:00:00', TIME '15:00:00', 1),
(4, 3, TIME '07:00:00', TIME '15:00:00', 1),
(5, 4, TIME '07:00:00', TIME '15:00:00', 1),
(6, 5, TIME '08:00:00', TIME '14:00:00', 1),
(7, 6, TIME '00:00:00', TIME '00:00:00', 1),
(8, 0, TIME '07:00:00', TIME '15:00:00', 2),
(9, 1, TIME '07:00:00', TIME '15:00:00', 2),
(10, 2, TIME '07:00:00', TIME '15:00:00', 2),
(11, 3, TIME '07:00:00', TIME '15:00:00', 2),
(12, 4, TIME '07:00:00', TIME '15:00:00', 2),
(13, 5, TIME '08:00:00', TIME '14:00:00', 2),
(14, 6, TIME '00:00:00', TIME '00:00:00', 2);
INSERT INTO "USERS" (id, first_name, last_name, email, phone_number, username, password, role, created_date) VALUES
(1, 'testName', 'testLastName', 'test@email.com', '00444444444', 'testuser', '$2a$12$wVZjTRY0adwXJQh6U3cGHu44Mu65camSrYpgMZIpawuFWQVnnB4lG', 0, CURRENT_TIMESTAMP);
INSERT INTO "CARS" (id, make, model, production_year, type, engine, user_id) VALUES
(1, 'BMW', '3 Series', 2014, 'Sedan', 'diesel', 1),
(2, 'AUDI', 'A8', 2018, 'Sedan', 'diesel', 1);





