CREATE TABLE employees (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    gender CHAR(1) NOT NULL CHECK (gender IN ('M', 'F', 'O')),
    email VARCHAR(80) NOT NULL UNIQUE,
    designation VARCHAR(30) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP,
    reports_to_id INT,
    can_approve_timesheets BOOLEAN NOT NULL  DEFAULT FALSE,
    created_by INT ,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by INT,
    modified_at TIMESTAMP,
    deleted_by INT,
    deleted_at TIMESTAMP,
    FOREIGN KEY (reports_to_id) REFERENCES employees(id) ON UPDATE CASCADE ON DELETE CASCADE 
);