import sqlite3

DB_NAME = "testing.db"

db = sqlite3.connect(DB_NAME)
cursor = db.cursor()
with open("schema.sql") as f:
    cursor.executescript(f.read())

# Populate db
with open("testing_values.sql") as f:
    cursor.executescript(f.read())

db.commit()
db.close()
