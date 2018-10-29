package runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

 

@RunWith(Cucumber.class)
@CucumberOptions(
		//include one or all feature files for testing
		 features = "Feature" //"/ValidateCitiesBasedOnIds.feature"		
		,glue={"stepDefinition"}
		,plugin = {"com.cucumber.listener.ExtentCucumberFormatter:"},
		 monochrome = true
		
		)
public class WeatherApiTestRunner {

}
