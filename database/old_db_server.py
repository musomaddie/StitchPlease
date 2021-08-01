from flask import Flask, abort, jsonify, make_response, request

import sqlite3

DB_NAME = "stitch_please.db"

app = Flask(__name__)


def _get_db():
    db = sqlite3.connect(DB_NAME,
                         detect_types=sqlite3.PARSE_DECLTYPES)
    return db, db.cursor()


def _make_key_value_pairs(cursor):
    return [dict(zip([column[0] for column in cursor.description], row))
            for row in cursor.fetchall()]


@app.route("/db_api/threads/view")
def view_all_threads():
    conn, cursor = _get_db()
    with open("db_scripts/get_all_threads.sql") as f:
        cursor.execute(f.read())
    return jsonify(_make_key_value_pairs(cursor))


if __name__ == "__main__":
    app.run(debug=True)
