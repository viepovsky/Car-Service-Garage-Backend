INSERT INTO make (id, name) VALUES
(1, 'Test Make 1'),
(2, 'Test Make 2');

INSERT INTO model (id, name, make_id, type) VALUES
(1, 'Test Model 1', 1, 'SEDAN'),
(2, 'Test Model 2', 2, 'SUV');

INSERT INTO vehicle (id, license_plate, user_id, model_id) VALUES
(1, 'PO1111', 1, 1);