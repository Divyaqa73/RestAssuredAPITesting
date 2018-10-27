package runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

 

@RunWith(Cucumber.class)
@CucumberOptions(
		 features = "Feature"		
		,glue={"stepDefinition"}
		,plugin = {"com.cucumber.listener.ExtentCucumberFormatter:"},
		 monochrome = true
		
		)
public class WeatherApiTestRunner {

}
