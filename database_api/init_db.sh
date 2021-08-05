#! /bin/bash

export FLASK_APP=stitch_please_database
export FLASK_ENV=development
export FLASK_DEBUG=1

flask init-db
