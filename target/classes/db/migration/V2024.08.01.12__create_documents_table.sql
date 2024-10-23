CREATE TABLE dc_documents (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    serial_no INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    is_recurring BOOLEAN NOT NULL DEFAULT FALSE,
    recurring_type VARCHAR(10),
    recurring_time_frame INT,
    recurring_fixed_on DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INT NOT NULL ,
	document_type_id TINYINT NOT NULL,
    deleted_by INT,
    deleted_at TIMESTAMP,
    FOREIGN KEY (deleted_by) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (document_type_id) REFERENCES dc_document_type(id)ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id)ON DELETE CASCADE ON UPDATE CASCADE
);