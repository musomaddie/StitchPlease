from flask import Flask, abort, jsonify, make_response, request
from werkzeug.security import check_password_hash, generate_password_hash

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


@app.route("/db_api/user/check_exists/<string:username>", methods=["GET"])
def check_user_exists(username):
    conn, cursor = _get_db()
    with open("db_scripts/user/get.sql") as f:
        cursor.execute(f.read(), (username,))
    result = cursor.fetchone() is not None
    return jsonify({"exists": result})


@app.route("/db_api/user/create", methods=["POST"])
def create_user():
    conn, cursor = _get_db()
    username = request.json["username"]
    password = request.json["password"]
    with open("db_scripts/user/create.sql") as f:
        cursor.execute(f.read(),
                       (username, password))

    conn.commit()
    conn.close()

    return jsonify({"username": username}), 201


@app.route("/db_api/user/get/<string:username>", methods=["GET"])
def get_user(username):
    """ NOTE: not smart error handling: only call if sure user exists """
    conn, cursor = _get_db()
    with open("db_scripts/user/get.sql") as f:
        cursor.execute(f.read(), (username, ))
    user = cursor.fetchone()
    return jsonify({"username": user[0],
                    "password": user[1]}), 201


@app.route("/db_api/threads/view", methods=["GET"])
def view_all_threads():
    conn, cursor = _get_db()
    with open("db_scripts/threads/get_all.sql") as f:
        cursor.execute(f.read())
    conn.close()
    return jsonify(_make_key_value_pairs(cursor))


if __name__ == "__main__":
    app.run(debug=True, port=5002)
