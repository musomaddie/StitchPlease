import functools

from flask import Flask, abort, jsonify, make_response, request, Blueprint
from stitch_please_database.db import get_db

bp = Blueprint("threads", __name__, url_prefix="/threads")


def _get_db():
    db = get_db()
    return db, db.cursor()


def _make_key_value_pairs(cursor):
    return [dict(zip([column[0] for column in cursor.description], row))
            for row in cursor.fetchall()]


@bp.route("/view")
def view_all_threads():
    conn, cursor = _get_db()
    with open("db_scripts/get_all_threads.sql") as f:
        cursor.execute(f.read())
    return jsonify(_make_key_value_pairs(cursor))
