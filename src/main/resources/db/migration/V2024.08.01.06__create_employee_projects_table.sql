CREATE TABLE employee_projects (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    employee_division_id INT NOT NULL,
    project_id INT NOT NULL,
    created_by INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by INT,
    modified_at TIMESTAMP,
    deleted_by INT,
    deleted_at TIMESTAMP,
    UNIQUE KEY (employee_division_id, project_id),
    FOREIGN KEY (employee_division_id) REFERENCES employee_divisions(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON UPDATE CASCADE ON DELETE CASCADE,
     FOREIGN KEY (created_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (modified_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (deleted_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);