package demo.features.search;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import demo.steps.serenity.EndUserSteps;

@RunWith(SerenityRunner.class)
public class SearchByKeywordStory {

    @Managed(uniqueSession = true, driver = "chrome")
    public WebDriver webdriver;

    @Steps
    public EndUserSteps tester;

    @Test
    public void valid_user_can_login_add_product_checkout_and_logout() {
        tester.is_the_home_page();
        tester.logs_in_with("standard_user", "secret_sauce");
        tester.should_see_inventory_page();
        tester.adds_backpack_to_cart();
        tester.should_see_backpack_marked_as_added();
        tester.opens_cart();
        tester.starts_checkout();
        tester.should_be_on_checkout_step_one();
        tester.goes_back_to_inventory();
        tester.should_see_inventory_page();
        tester.logs_out();
        tester.should_be_back_to_login_page();
    }
} 