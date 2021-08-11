import os
import sqlite3

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


def _make_key_value_pairs(cursor):
    return [dict(zip([column[0] for column in cursor.description], row))
            for row in cursor.fetchall()]
