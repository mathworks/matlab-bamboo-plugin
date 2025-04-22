import pytest
import requests
import time
from requests.auth import HTTPBasicAuth

BAMBOO_URL = "http://172.18.78.20:8085"
PLAN_KEY = "YIT-YIT"
USERNAME = "admin1"
PASSWORD = "admin1"

def test_pipeline_execution():
    # Trigger the build
    build_key = trigger_pipeline()
    # Verify pipeline execution
    assert check_pipeline_status(build_key) == True, "Pipeline execution failed"

def trigger_pipeline():
    response = requests.post(
        f"{BAMBOO_URL}/rest/api/latest/queue/{PLAN_KEY}", 
        auth=HTTPBasicAuth(USERNAME, PASSWORD),
        headers={'Accept': 'application/json'})
    
    # Print response details for debugging
    print(f"Status Code: {response.status_code}")
    print(f"Response Content: {response.text}")
    
    # Check if the request was successful
    if response.status_code == 200:
        print("Build triggered successfully")
        return response.json()['buildResultKey']
    else:
        raise Exception("Failed to trigger pipeline")

def check_pipeline_status(build_key):
    while True:
        response = requests.get(
            f"{BAMBOO_URL}/rest/api/latest/result/{build_key}-lastest", 
            auth=HTTPBasicAuth(USERNAME, PASSWORD))
        if response.status_code == 200:
            result = response.json()
            if result['buildState'] == 'Successful':
                print("Pipeline succeeded")
                return True
            elif result['buildState'] in ['Failed', 'Error']:
                print("Pipeline failed")
                return False
        time.sleep(20)