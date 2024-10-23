CREATE TABLE ts_project_tasks (
    id INT  AUTO_INCREMENT PRIMARY KEY,
    project_id INT  NOT NULL,
    task_id INT NOT NULL,
    created_by INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by INT,
    modified_at TIMESTAMP,
    deleted_by INT,
    deleted_at TIMESTAMP,
    UNIQUE KEY (project_id, task_id),
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (task_id) REFERENCES  ts_tasks(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (modified_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (deleted_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);