import pytest
from flask import g, session
# from stitch_please_database.db import get_db


@pytest.mark.parametrize(
    ("dmc_value", "color", "description", "result"),
    (("166", "Green", "Medium Light Moss Green", True),
     ("310", "Black", "Black", False)))
def test_adding_one_thread(client, dmc_value, color, description, result):
    response = client.post(
        "threads/add_one", data={"dmc_value": dmc_value,
                                 "color": color,
                                 "description": description})
    assert result in response.data
