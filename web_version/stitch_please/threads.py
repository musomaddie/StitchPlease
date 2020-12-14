import functools
import json
import requests

from flask import (
    Blueprint, flash, g, redirect, render_template, request, session, url_for
)

bp = Blueprint("threads", __name__, url_prefix="/threads")

DATABASE_API = "http://localhost:5002/db_api/threads/"


@bp.route("/view_all", methods=["GET"])
def view_all():
    response = requests.get(f"{DATABASE_API}view")
    threads = json.loads(response.text)
    return render_template("threads/view_all.html", threads=threads)
