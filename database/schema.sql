DROP TABLE IF EXISTS thread;
DROP TABLE IF EXISTS user;

CREATE TABLE thread(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	dmc_value TEXT NOT NULL,
	colour TEXT NOT NULL
);

CREATE TABLE user(
	username TEXT PRIMARY KEY,
	password TEXT NOT NULL
);

/* Insert a handful of example threads */
INSERT INTO thread VALUES(1, '310', 'black');
INSERT INTO thread VALUES(2, '666', 'red');
INSERT INTO thread VALUES(3, '550', 'purple');
