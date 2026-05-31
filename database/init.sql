CREATE DATABASE IF NOT EXISTS employee_db;

USE employee_db;

CREATE TABLE IF NOT EXISTS employees (
                                         id INT NOT NULL AUTO_INCREMENT,
                                         first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id)
    );

INSERT INTO employees (first_name, last_name, date_of_birth, salary)
VALUES
    ('Alice', 'Johnson', '1992-04-15', 5500.00),
    ('Carlos', 'Rivera', '1988-09-20', 4800.00),
    ('Maria', 'Lopez', '1995-01-10', 6200.00),
    ('Daniel', 'Kim', '1990-07-08', 7100.00);