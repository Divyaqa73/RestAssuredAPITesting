package stepDefinition;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

import java.util.List;

import org.junit.Assert;

import cucumber.api.java.en.Then;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ListOfCities {

	@Then("^Validate latitude for each cities for \"([^\"]*)\"$")
	public void validate_latitude_for_each_cities_for(String uri) throws Throwable {
		//bounding box [lon-left,lat-bottom,lon-right,lat-top,zoom]
		
		Response response = given().when().get(uri).then().contentType(ContentType.JSON).extract().response();				
		List<Float> list = response.jsonPath().getList("list.coord.Lat");
		
		String[] coord = uri.replace("data/2.5/box/city?bbox=", "").replace("&appid=bf5a1563c57360118e92fa138279fbf1", "").split(",");

		Float bottomLattitude = Float.parseFloat(coord[1]);
		Float topLattitude = Float.parseFloat(coord[3]);
		//System.out.println(response.getBody().asString());
		//System.out.println(list);
		
		for(Float d : list){
			//System.out.println(d.floatValue());
			Assert.assertTrue(bottomLattitude <= d.floatValue() && d.floatValue() <= topLattitude);
		}
	}

	@Then("^Validate longitude for each cities for \"([^\"]*)\"$")
	public void validate_longitude_for_each_cities_for(String uri) throws Throwable {
		
		Response response = given().when().get(uri).then().contentType(ContentType.JSON).extract().response();				
		List<Object> list = response.jsonPath().getList("list.coord.Lon");
		
		String[] coord = uri.replace("data/2.5/box/city?bbox=", "").replace("&appid=bf5a1563c57360118e92fa138279fbf1", "").split(",");

		Float leftLongitude = Float.parseFloat(coord[0]);
		Float rightLongitude = Float.parseFloat(coord[2]);
		
		System.out.println(list);
		
		for(int i = 0; i < list.size(); i++){
			Float fl = Float.parseFloat(list.get(i).toString());
			Assert.assertTrue(leftLongitude <= fl && fl <= rightLongitude);
		}

	}	
	
	@Then("^Validate count from response for \"([^\"]*)\"$")
	public void validate_count_from_response_for(String uri) throws Throwable {
		Response response = given().when().get(uri).then().contentType(ContentType.JSON).extract().response();				
		List<Object> list = response.jsonPath().getList("list.id");
		
		int cnt = Integer.parseInt(uri.substring(uri.indexOf("&cnt=")+ 5, uri.indexOf("&appid=")));		
		Assert.assertTrue(cnt == list.size());
	}

	@Then("^Validate latitude for Cities in Cycle for \"([^\"]*)\"$")
	public void validate_latitude_for_Cities_in_Cycle_for(String uri) throws Throwable {
		
		Float lat = Float.parseFloat(uri.substring(uri.indexOf("?lat=")+ 5, uri.indexOf("&lon=")));	
		Response response = given().when().get(uri).then().contentType(ContentType.JSON).extract().response();				
		List<Object> list = response.jsonPath().getList("list.coord.lat");
		
		for(int i = 0; i < list.size(); i++){
			Float fl = Float.parseFloat(list.get(i).toString());
			Assert.assertTrue(Math.abs(fl-lat) < 0.1) ;
		}	
	}

	@Then("^Validate longitude for Cities in Cycle for \"([^\"]*)\"$")
	public void validate_longitude_for_Cities_in_Cycle_for(String uri) throws Throwable {
		Float lon = Float.parseFloat(uri.substring(uri.indexOf("&lon=")+ 5, uri.indexOf("&cnt=")));	
		Response response = given().when().get(uri).then().contentType(ContentType.JSON).extract().response();				
		List<Object> list = response.jsonPath().getList("list.coord.lon");
		
		for(int i = 0; i < list.size(); i++){
			Float fl = Float.parseFloat(list.get(i).toString());
			//System.out.println(lon + "::" + fl);
			Assert.assertTrue(Math.abs(fl-lon) < 1) ;
		}	
	}
	
	@Then("^Validate Count and City details for \"([^\"]*)\"$")
	public void validate_Count_and_City_details_for(String uri) throws Throwable {
		String Ids = uri.substring(uri.indexOf("?id=")+ 4, uri.indexOf("&units="));
		String[] inputIds = Ids.split(",");
		
		Response response = given().when().get(uri).then().contentType(ContentType.JSON).extract().response();				
		List<Object> list = response.jsonPath().getList("list.id");
		
		//Verify the number of City ids from resoponse should be less than or equal to given number of city ids
		Assert.assertTrue(list.size() <= inputIds.length);
		
		for(int i = 0; i < list.size(); i++){
			boolean b = false;
			for(int j = 0; j < inputIds.length; j++){
				//loop input city ids to find any match with response city id
				if(inputIds[j].toString().equals(list.get(i).toString())){
					b = true;
					break;
				}
			}
			Assert.assertTrue(b);;
		}
	}
}
