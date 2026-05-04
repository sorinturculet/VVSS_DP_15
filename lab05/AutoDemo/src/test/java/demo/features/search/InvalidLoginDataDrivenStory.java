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
@UseTestDataFrom("src/test/resources/testdata/login_invalid.csv")
public class InvalidLoginDataDrivenStory {

    public String username;
    public String password;
    public String errorMessagePart;

    @Managed(uniqueSession = true, driver = "chrome")
    public WebDriver webdriver;

    @Steps
    public EndUserSteps tester;

    @Test
    public void invalid_users_should_see_error_message() {
        tester.is_the_home_page();
        tester.logs_in_with(username, password);
        tester.should_see_login_error(errorMessagePart);
    }
}
