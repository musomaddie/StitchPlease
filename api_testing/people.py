from datetime import datetime
from flask import abort, make_response


def get_timestamp():
    return datetime.now().strftime(("%Y-%m-%d %H:%M:%S"))


# Data to server
PEOPLE = {
    "Harry": {
        "fname": "Harry",
        "lname": "Potter",
        "timestamp": get_timestamp()
    },
    "Ron": {
        "fname": "Ron",
        "lname": "Weasley",
        "timestamp": get_timestamp()
    },
    "Hermione": {
        "fname": "Hermione",
        "lname": "Granger",
        "timestamp": get_timestamp()
    }
}


# Create a handler for the read (GET) people
def read():
    return [PEOPLE[key] for key in sorted(PEOPLE.keys())]


def create(person):
    lname = person.get("lname", None)
    fname = person.get("fname", None)

    if lname not in PEOPLE and lname is not None:
        PEOPLE[lname] = {
            "lname": lname,
            "fname": fname,
            "timestamp": get_timestamp(),
        }
        return make_response(
            f"{lname} successfully created", 201)

    # They exist therefore error
    abort(406, f"Person with lastname {lname} already exists")
