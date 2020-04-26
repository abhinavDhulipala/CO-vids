from flask import Flask, request, send_file, jsonify
import json

app = Flask(__name__)

mock_times = {"times": {"Sunday": ["1:30 PM PST", "2:00 PM PST", "5:00 PM PST"],
"Monday": ["4:30 PM PST"],
"Tuesday": [],
"Wednesday": ["1:00 AM PST"],
"Thursday": ["2:00 PM PST"],
"Friday": ["3:00 PM PST", "5:30 PM PST", "6:00 PM PST"],
"Saturday": []}}

@app.route("/", methods=['POST'])
def receive_availability():
    availability = request.get_json()
    if "times" in availability:
        for day in availability["times"]:
            if day in mock_times["times"] and availability["times"][day] in mock_times["times"][day]:
                mock_times["times"][day].remove(availability["times"][day])
    return "\nReservation Made\n\n"

@app.route("/", methods=['GET'])
def send_availability():
    return jsonify(mock_times)

if __name__ == "__main__":
    app.run(debug=True)


'''
Test Commands

GET Request: curl localhost:5000

POST Request: curl --header "Content-Type: application/json" --request POST --data "{\"times\": {\"Sunday\": \"1:30 PM PST\"}}" http://localhost:5000/

'''
