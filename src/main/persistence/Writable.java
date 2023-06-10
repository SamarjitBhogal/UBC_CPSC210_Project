package persistence;

import org.json.JSONObject;

// an interface designed to help make JSON objects for classes present in model package
// Source: JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
//         this interface in based on the one presented in this repository which helps this project
//         create writable JSON objects.
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject makeJsonObject();
}
