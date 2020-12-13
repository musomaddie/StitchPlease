import sqlite3

# I could do the fancy flask thing but I could alternatively just run this as a
# script

DB_NAME = "stitch_please.db"

db = sqlite3.connect(DB_NAME)
cursor = db.cursor()
with open("schema.sql") as f:
    cursor.executescript(f.read())

db.commit()
db.close()
