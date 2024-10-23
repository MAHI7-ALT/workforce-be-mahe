CREATE TABLE employee_divisions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    division_id INT NOT NULL,
	primary_division BOOLEAN NOT NULL DEFAULT FALSE,
    created_by INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by INT,
    modified_at TIMESTAMP,
    deleted_by INT,
    deleted_at TIMESTAMP,
    UNIQUE KEY (employee_id, division_id),
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (division_id) REFERENCES divisions(id) ON UPDATE CASCADE ON DELETE CASCADE,
   FOREIGN KEY (created_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (modified_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (deleted_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);