package restRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features/"
        , glue = {"stepDefinitions"}
        , plugin = {"pretty", "html:target/site/cucumber-pretty", "json:target/cucumber/cucumber.json"}
        , monochrome = true



)


class TestRunner {

}
