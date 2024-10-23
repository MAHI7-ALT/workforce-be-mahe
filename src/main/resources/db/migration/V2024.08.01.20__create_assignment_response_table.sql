create table dc_assignment_response(
    id INT PRIMARY KEY AUTO_INCREMENT,
    assignment_result_id INT NOT NULL,
    question_id INT NOT NULL,
    chosen_option TEXT  NOT NULL,
	FOREIGN KEY (assignment_result_id) REFERENCES dc_assignment_results(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (question_id) REFERENCES dc_questions(id) ON DELETE CASCADE ON UPDATE CASCADE
);
