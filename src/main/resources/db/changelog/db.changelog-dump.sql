--liquibase formatted sql
--changeset eduardo.comerlato:1
INSERT INTO department (name, location)
VALUES ('Human Sciences', 'Building A, Room 101'),
       ('Languages and Codes', 'Building A, Room 301'),
       ('Natural Sciences', 'Building B, Room 101'),
       ('Math', 'Building B, Room 301');

--changeset eduardo.comerlato:2
INSERT INTO instructor (department_name, first_name, last_name, phone)
VALUES ('Human Sciences', 'John', 'Carim', '51999999991'),
       ('Languages and Codes', 'James', 'Morne', '51999999992'),
       ('Natural Sciences', 'Laura', 'Oolacile', '51999999993'),
       ('Math', 'Anna', 'Lordran', '51999999994');

--changeset eduardo.comerlato:3
INSERT INTO course (department_name, instructor_id, duration, name)
VALUES ('Human Sciences', '1', '8', 'Social Sciences'),
       ('Human Sciences', '1', '8', 'Philosophy'),
       ('Languages and Codes', '2', '6', 'Sign Language'),
       ('Languages and Codes', '2', '8', 'Portuguese'),
       ('Natural Sciences', '3', '10', 'Astrophysics'),
       ('Natural Sciences', '3', '10', 'Biology'),
       ('Math', '4', '8', 'Accounting Sciences'),
       ('Math', '4', '8', 'Statistics');

--changeset eduardo.comerlato:4
INSERT INTO student (first_name, last_name, phone)
VALUES ('Anderson', 'Balder', '51999999995'),
       ('Mark', 'Astora', '51999999996'),
       ('Giullia', 'Berenike', '51999999997'),
       ('Jennifer', 'Catarina', '51999999998');