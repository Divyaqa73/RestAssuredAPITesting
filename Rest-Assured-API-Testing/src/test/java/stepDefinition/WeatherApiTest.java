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
	@Given("^Launce the API$")
	public void launce_the_API() throws Throwable {
		RestAssured.baseURI = "http://api.openweathermap.org/";   
	 
	}

	@Then("^The Response code is succesfull for \"([^\"]*)\"$")
	public void the_Response_code_is_succesfull(String uri) throws Throwable {
		given().when().
		get(uri).
		then()
		.statusCode(200);
	 
	}

	@Then("^The Response code if negative$")
	public void the_Response_code_if_negative(String uri) throws Throwable {
		given().when().
		get(uri).
		then().
		body("message", equalTo("city not found"), "cod", equalTo("404"));
	 
	}

	@Then("^The Response is in JSON format for \"([^\"]*)\"$")
	public void the_Response_is_in_JSON_format(String uri) throws Throwable {
		given().when().
		get(uri).
		then().
		assertThat().
		contentType("application/json");
	 
	}

	@Then("^Validate the temperature range for \"([^\"]*)\"$")
	public void validate_the_temperature_range(String uri) throws Throwable {
	   
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

	@Then("^The City name is displayed as expected for \"([^\"]*)\"$")
	public void the_City_name_is_displayed_as_expected() throws Throwable {
	    
	 
	}

	@Then("^The Key IsInvisible is not availabe in the response for \"([^\"]*)\"$")
	public void the_Key_name_is_availabe_in_the_response(String uri) throws Throwable {
	    
		given().when().
		get(uri).
		then().
			assertThat().
			body("$", hasKey("name"));
	}

	@Then("^The Key IsInvisible is not availabe in the response$")
	public void the_Key_IsInvisible_is_not_availabe_in_the_response(String uri) throws Throwable {
	    
		given().when().
		get(uri).
		then().
			assertThat().
			body("$", not(hasKey("IamInvisible")));
	}

	@Then("^Validate response time for \"([^\"]*)\"$")
	public void validate_response_time(String uri) throws Throwable {
	    
		given().when().
		get(uri).
		then().
		assertThat().
		time(lessThan(1000L),TimeUnit.MILLISECONDS);
	}
	//ResponseSpecification respSpec;
	
	public ResponseSpecification createResponseSpecification(String name) {
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
		given().when().
		get(uri).then().			
			spec(createResponseSpecification(name));
	 
	}}
