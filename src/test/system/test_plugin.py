import unittest
import requests
import time
from requests.auth import HTTPBasicAuth

BAMBOO_URL = "http://172.18.78.20:8085"
PLAN_KEY = "YIT-YIT"
USERNAME = "admin1"
PASSWORD = "admin1"

class TestBuilds(unittest.TestCase):

    def test_pipeline_execution(self):
        # Trigger the build
        build_key = trigger_pipeline()
        # Verify pipeline execution
        status = check_pipeline_status(build_key)
        self.assertTrue(status, "Pipeline execution failed")

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


def check_pipeline_status(build_key, timeout=300, interval=20):
    start_time = time.time()
    
    while time.time() - start_time < timeout:
        try:
            response = requests.get(
                f"{BAMBOO_URL}/rest/api/latest/result/{build_key}", 
                auth=HTTPBasicAuth(USERNAME, PASSWORD),
                headers={'Accept': 'application/json'})
            
            if response.status_code == 200:
                result = response.json()
                state = result.get('buildState')
                
                print(f"Current build state: {state}")
                
                if state == 'Successful':
                    print("Pipeline succeeded")
                    return True
                elif state in ['Failed', 'Error']:
                    print("Pipeline failed")
                    return False
                # Continue polling if state is 'Unknown' or 'InProgress'
            else:
                print(f"Received status code: {response.status_code}")
                
        except requests.exceptions.RequestException as e:
            print(f"Request error: {e}")
            
        time.sleep(interval)
        
    raise TimeoutError(f"Pipeline status check timed out after {timeout} seconds")

     
if __name__ == "__main__":
    unittest.main()