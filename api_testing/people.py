from datetime import datetime


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
