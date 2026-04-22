package side.practice.files;

import io.restassured.path.json.JsonPath;
//helper class to hold reusable methods
public class ReusableMethods {
	//method to convert String and return as Json
	public static JsonPath rawToJson(String response) {
		JsonPath js = new JsonPath(response);
		return js;
	}
}
