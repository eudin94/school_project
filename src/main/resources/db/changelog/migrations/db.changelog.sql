--liquibase formatted sql
--changeset eduardo.comerlato:1
SET TIMEZONE TO 'GMT+3';

--changeset eduardo.comerlato:2
CREATE TABLE IF NOT EXISTS department
(
    name                                VARCHAR(255) PRIMARY KEY,
    location                            VARCHAR(255)
);

--changeset eduardo.comerlato:3
CREATE TABLE IF NOT EXISTS instructor
(
    id                                  INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    department_name                     VARCHAR(255) REFERENCES department (name),
    headed_by                           VARCHAR(255),
    first_name                          VARCHAR(255),
    last_name                           VARCHAR(255),
    phone                               VARCHAR(255)
);

--changeset eduardo.comerlato:4
CREATE TABLE IF NOT EXISTS course
(
    id                                  INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    department_name                     VARCHAR(255) REFERENCES department (name),
    instructor_id                       INTEGER REFERENCES instructor(id),
    duration                            INTEGER,
    name                                VARCHAR(255)
);

--changeset eduardo.comerlato:5
CREATE TABLE IF NOT EXISTS student
(
    id                                  INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name                          VARCHAR(255),
    last_name                           VARCHAR(255),
    phone                               VARCHAR(255)
);

--changeset eduardo.comerlato:6
CREATE TABLE IF NOT EXISTS course_student
(
    course_id                           INTEGER REFERENCES course (id),
    student_id                          INTEGER REFERENCES student (id)
);