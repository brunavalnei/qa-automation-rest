package restRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
//        format={"pretty", "json:target/"},
        features = "src/test/java/feature/"
        ,glue = {"stepDefinitions"}
//        format={}

)
class TestRunner {


}
