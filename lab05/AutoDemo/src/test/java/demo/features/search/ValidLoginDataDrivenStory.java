package demo.features.search;

import demo.steps.serenity.EndUserSteps;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/testdata/login_valid.csv")
public class ValidLoginDataDrivenStory {

    public String username;
    public String password;

    @Managed(uniqueSession = true, driver = "chrome")
    public WebDriver webdriver;

    @Steps
    public EndUserSteps tester;

    @Test
    public void valid_users_should_login_successfully() {
        tester.is_the_home_page();
        tester.logs_in_with(username, password);
        tester.should_see_inventory_page();
    }
}
