INSERT INTO address (id, city, code, street) VALUES
(1, 'Poznań', '50-500', 'Wrocławska Street');

INSERT INTO garage (id, name, description, address_id) VALUES
(1, 'Fast Garage & Service', 'Short garage description', 1);

INSERT INTO garage_schedule (id, schedule_date, open_time, close_time, garage_id) VALUES
(1, '2100-03-03', TIME '07:00:00', TIME '15:00:00', 1);

INSERT INTO catalog_offer (id, name, price, repair_time, garage_id) VALUES
(1, 'Tire removal', 50.00, 60, 1);