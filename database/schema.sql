DROP TABLE IF EXISTS thread;

CREATE TABLE thread(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	dmc_value STRING NOT NULL,
	colour STRING NOT NULL
);

/* Insert a handful of example threads */
INSERT INTO thread VALUES(1, '310', 'black');
INSERT INTO thread VALUES(2, '666', 'red');
INSERT INTO thread VALUES(3, '550', 'purple');
