import cucumber.api.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.testng.annotations.AfterSuite;
import utils.ReportingUtils;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty","html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json"})

public class TestRunner {

    @AfterSuite
    public void generateReport(){
        ReportingUtils.generateJVMReport();
    }

}
