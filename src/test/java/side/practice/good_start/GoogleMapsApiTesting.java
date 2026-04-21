package side.practice.good_start;

import io.restassured.RestAssured;
import side.practice.files.Payload;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoogleMapsApiTesting {
	public static void main(String[] args) {
		/*
		 * given - all input details When - submit the API, resource, http method Then -
		 * validate the response
		 */
		// storing base uri
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		// giving input, submitting and asserting the status code
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.addPlace()) //got payload from helper class method
				.when().post("/maps/api/place/add/json").then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)");
	}
}