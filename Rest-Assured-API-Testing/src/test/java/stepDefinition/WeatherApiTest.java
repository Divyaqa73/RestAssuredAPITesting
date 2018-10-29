package stepDefinition;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;


import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.testng.annotations.BeforeClass;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
public class WeatherApiTest {

	
	@Before
	@Given("^Launch the API$")
	public void launch_the_API() throws Throwable {
		/*
		 * Assign the URI
		 */
		RestAssured.baseURI = "http://api.openweathermap.org/";	 
	}

	@Then("^The Response code is successful for \"([^\"]*)\"$")
	public void the_Response_code_is_successful(String uri) throws Throwable {
		/*
		 * validate the success status code from the response 
		 */
		given().when().
		get(uri).
		then()
		.statusCode(200);	 
	}

	@Then("^The Response code if negative$")
	public void the_Response_code_if_negative(String uri) throws Throwable {
		/*
		 * Validate negative scenario
		 * 
		 */
		given().when().
		get(uri).
		then().
		body("message", equalTo("city not found"), "cod", equalTo("404"));	 
	}

	@Then("^The Response is in JSON format for \"([^\"]*)\"$")
	public void the_Response_is_in_JSON_format(String uri) throws Throwable {
		/*
		 * validate the content type from Response Spec
		 */
		given().when().
		get(uri).
		then().
		assertThat().
		contentType("application/json");	 
	}

	@Then("^Validate the temperature range for \"([^\"]*)\"$")
	public void validate_the_temperature_range(String uri) throws Throwable {
		/*
		 * validate temperature within min and max range from Response  
		 */
		float minTemp = given().
				when().
				get(uri).
				then().extract().
		        path("main.temp_min");
				
		float maxTemp = given().when().
				      get(uri).
						
						then().extract().
				        path("main.temp_max");
				
		float temp =given().when().
				get(uri).
						then().extract().
				        path("main.temp");
				
		Assert.assertTrue(minTemp <= temp && temp <= maxTemp);				
	}

	@Then("^The Key name is available in the response for \"([^\"]*)\"$")
	public void the_Key_name_is_available_in_the_response(String uri) throws Throwable {
		/*
		 * validate key name from the response
		 */
		given().when().
		get(uri).
		then().
			assertThat().
			body("$", hasKey("name"));
	}

	@Then("^The Key IsInvisible is not available in the response for \"([^\"]*)\"$")
	public void the_Key_IsInvisible_is_not_available_in_the_response(String uri) throws Throwable {
		/*
		 * Negative scenario
		 * Validate key does not exist in response
		 */
		given().when().
		get(uri).
		then().
			assertThat().
			body("$", not(hasKey("IamInvisible")));
	}

	@Then("^Validate response time for \"([^\"]*)\"$")
	public void validate_response_time(String uri) throws Throwable {
		/*
		 * validate the response received from the end point within 1000 milliseconds
		 */
		given().when().
		get(uri).
		then().
		assertThat().
		time(lessThan(1000L),TimeUnit.MILLISECONDS);
	}
	//ResponseSpecification respSpec;
	
	public ResponseSpecification createResponseSpecification(String name) {
		/*
		 * Get the response specification
		 */
		ResponseSpecification respSpec;
		respSpec = new ResponseSpecBuilder().
				expectStatusCode(200).
				expectContentType(ContentType.JSON).
				expectBody("name", equalTo(name)).
				build();
			return respSpec;
	}

	@Then("^Validate standard assertions using ResponseSpec for \"([^\"]*)\" is \"([^\"]*)\"$")
	public void validate_standard_assertions_using_ResponseSpec(String uri,String name) throws Throwable {
		/*
		 * compare the name tag value matched with expected result
		 */
		given().when().
		get(uri).then().			
			spec(createResponseSpecification(name));
	 
	}}
