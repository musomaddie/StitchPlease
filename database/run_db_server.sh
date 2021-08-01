#! /bin/bash

export FLASK_APP=db_server.py
export FLASK_ENV=development
export FLASK_DEBUG=1

flask run
