import json
import requests

from flask import (Blueprint, flash, g, session, redirect,
                   render_template, request, url_for)
from werkzeug.security import check_password_hash, generate_password_hash

bp = Blueprint("user", __name__)

DATABASE_API = "http://localhost:5002/db_api/user/"


def _check_password_correct(username, password):
    r = requests.get(f"{DATABASE_API}get/{username}")
    og_password = json.loads(r.text)["password"]
    return check_password_hash(og_password, password)


def _check_user_exists(username):
    r = requests.get(f"{DATABASE_API}check_exists/{username}")
    user_exists = json.loads(r.text)["exists"]
    return user_exists


# Load the logged in user
@bp.before_app_request
def load_logged_in_user():
    user_id = session.get("user_id")

    if user_id is None:
        g.user = None

    else:
        if not _check_user_exists(user_id):
            g.user = None
        else:
            r = requests.get(f"{DATABASE_API}get/{user_id}")
            g.user = json.loads(r.text)


@bp.route("/login", methods=["GET", "POST"])
def login():
    if request.method == "POST":
        username = request.form["username"]
        text_password = request.form["password"]
        password = generate_password_hash(text_password)
        error = None

        if not username:
            error = "Username is required."
        elif not password:
            error = "Password is required."
        elif not _check_user_exists(username):
            error = "Incorrect Username"
        elif not _check_password_correct(username, text_password):
            error = "Incorrect password"

        if error is None:
            session.clear()
            session["user_id"] = username
            return redirect(url_for("user.home_page"))

        flash(error)

    return render_template("user/login.html")


@bp.route("/signup", methods=["GET", "POST"])
def signup():
    if request.method == "POST":
        username = request.form["username"]
        password = generate_password_hash(request.form["password"])
        error = None

        if not username:
            error = "Username is required."
        elif not password:
            error = "Password is required."
        elif _check_user_exists(username):
            error = f"The user {username} already exists"

        if error is None:
            r = requests.post(
                f"{DATABASE_API}create",
                json={"username": username, "password": password})
            # Login as this user
            session.clear()
            session["user_id"] = json.loads(r.text)["username"]
            return redirect(url_for("user.home_page"))
        flash(error)

    return render_template("user/signup.html")


# TODO: this will be the home page showing all of their details. ENFORCE LOGIN
@bp.route("/", methods=["GET"])
def home_page():
    return render_template("user/home_page.html")
