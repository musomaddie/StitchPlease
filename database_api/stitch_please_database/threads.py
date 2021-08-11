import os

from flask import Blueprint
from flask import jsonify
from flask import request
from stitch_please_database.db import get_db

DIR = f"{os.path.realpath(__file__)[:-len('threads.py')]}/db_scripts/"
bp = Blueprint("threads", __name__, url_prefix="/threads")


def _get_db():
    db = get_db()
    return db, db.cursor()


def _close_db(conn, cursor):
    cursor.close()
    conn.commit()
    conn.close()


def _setup_response(values):
    response = {value: None for value in values}
    response["success"] = False
    return response


def _make_key_value_pairs(cursor):
    return [dict(zip([column[0] for column in cursor.description], row))
            for row in cursor.fetchall()]


@bp.route("/get")
def get_one_thread():
    response = _setup_response(("color", "description", "message"))
    dmc = request.args.get("dmc_value")

    conn, cursor = _get_db()
    with open(f"{DIR}/get_one_thread.sql") as f:
        cursor.execute(f.read(), {"dmc_value": dmc})
    result = cursor.fetchone()
    _close_db(conn, cursor)

    if result is None:
        response["message"] = f"Thread color {dmc} does not exist"
    else:
        response["success"] = True
        response["color"] = result["color"]
        response["description"] = result["description"]
    return jsonify(response)
