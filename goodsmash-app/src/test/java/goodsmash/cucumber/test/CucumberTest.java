package goodsmash.cucumber.test;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:cucumber", glue = {"goodsmash.cucumber.common",
		"goodsmash.cucumber.steps"}, tags = "not @ignore")
public class CucumberTest {
}
