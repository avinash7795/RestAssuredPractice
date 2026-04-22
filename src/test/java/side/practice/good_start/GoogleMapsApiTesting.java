package side.practice.good_start;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import side.practice.files.Payload;
import side.practice.files.ReusableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class GoogleMapsApiTesting {
	public static void main(String[] args) {
		/*
		 * given - all input details When - submit the API, resource, http method Then -
		 * validate the response
		 */
		// storing base uri
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		// giving input, submitting and asserting the status code and extracting the response
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.addPlace()) // got payload from helper class method
				.when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();
		System.out.println(response);
		// parsing string into json
		JsonPath js = ReusableMethods.rawToJson(response);
		// extracting place id from json response body
		String placeId = js.getString("place_id");
		System.out.println(placeId);

		// storing new address in a variable
		String newAddress = "70 winter walk, USA";
		// updating the address of the place and asserting the success message, status code
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// getting response as string by fetching GET api using place id
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId).when()
				.get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();
		// parsing string into json format
		JsonPath js1 = ReusableMethods.rawToJson(getResponse);
		// extracting 'address' data from the response
		String responseAddress = js1.getString("address");
		System.out.println(responseAddress);
		//TestNg assertion to validate the address is updated or not
		Assert.assertEquals(newAddress, responseAddress);
	}
}