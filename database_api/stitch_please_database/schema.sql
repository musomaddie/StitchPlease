DROP TABLE IF EXISTS thread_details;
DROP TABLE IF EXISTS thread;
DROP TABLE IF EXISTS user_details;
DROP TABLE IF EXISTS project_details;
DROP TABLE IF EXISTS user_threads;
DROP TABLE IF EXISTS user_projects;
DROP TABLE IF EXISTS project_threads;

CREATE TABLE thread_details(
	dmc_value STRING PRIMARY KEY,
	color STRING NOT NULL,
	description STRING NOT NULL,
	hexcode STRING
);

CREATE TABLE user_details(
	username STRING PRIMARY KEY,
	password STRING NOT NULL
);

CREATE TABLE project_details(
	id INTEGER PRIMARY KEY,
	name STRING NOT NULL,
	is_started BOOLEAN,
	is_complete BOOLEAN
);

CREATE TABLE user_threads(
	username STRING NOT NULL,
	dmc_value STRING NOT NULL,
	quantity_required FLOAT,
	quantity_owned FLOAT NOT NULL,
	PRIMARY KEY (username, dmc_value),
	FOREIGN KEY (username) REFERENCES user_details(username),
	FOREIGN KEY (dmc_value) REFERENCES thread_details(dmc_value)
);

CREATE TABLE user_projects(
	username STRING NOT NULL,
	project_id INTEGER NOT NULL,
	PRIMARY KEY (username, project_id)
	FOREIGN KEY (username) REFERENCES user_details(username),
	FOREIGN KEY (project_id) REFERENCES project_details(id)
);

CREATE TABLE project_threads(
	project_id INTENGER NOT NULL,
	dmc_value INTEGER NOT NULL,
	amount_required FLOAT,
	PRIMARY KEY (project_id, dmc_value),
	FOREIGN KEY (project_id) REFERENCES project_details(id)
	FOREIGN KEY (dmc_value) REFERENCES thread_details(dmc_value)
);

