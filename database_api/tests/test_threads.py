import pytest
import json


@pytest.mark.parametrize(
    ("dmc", "success", "color", "desc", "message"),
    (("550", True, "Purple", "Very Dark Violet", None),
     ("311", False, None, None, "Thread color 311 does not exist")))
def test_get_one_thread(client, dmc, success, color, desc, message):
    response = json.loads(client.get(f"threads/get?dmc_value={dmc}").data)
    assert response["success"] is success
    assert response["color"] == color
    assert response["description"] == desc
    assert response["message"] == message
