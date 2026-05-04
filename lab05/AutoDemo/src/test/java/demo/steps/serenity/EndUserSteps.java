package demo.steps.serenity;

import demo.pages.DictionaryPage;
import net.thucydides.core.annotations.Step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class EndUserSteps {

    DictionaryPage dictionaryPage;

    @Step
    public void is_the_home_page() {
        dictionaryPage.open();
    }

    @Step
    public void logs_in_with(String username, String password) {
        dictionaryPage.loginWith(username, password);
    }

    @Step
    public void should_see_inventory_page() {
        assertThat(dictionaryPage.isOnInventoryPage(), is(true));
    }

    @Step
    public void should_see_login_error(String expectedMessagePart) {
        assertThat(dictionaryPage.getLoginErrorMessage(), containsString(expectedMessagePart));
    }

    @Step
    public void logs_out() {
        dictionaryPage.logout();
    }

    @Step
    public void should_be_back_to_login_page() {
        assertThat(dictionaryPage.isOnLoginPage(), is(true));
    }

    @Step
    public void adds_backpack_to_cart() {
        dictionaryPage.addBackpackToCart();
    }

    @Step
    public void should_see_cart_count(String expectedCount) {
        assertThat(dictionaryPage.getCartBadgeCount(), equalTo(expectedCount));
    }

    @Step
    public void opens_cart() {
        dictionaryPage.openCart();
    }

    @Step
    public void should_be_on_cart_page() {
        assertThat(dictionaryPage.isOnCartPage(), is(true));
    }

    @Step
    public void should_see_backpack_in_cart() {
        assertThat(dictionaryPage.isBackpackPresentInCart(), is(true));
    }

    @Step
    public void should_see_backpack_marked_as_added() {
        assertThat(dictionaryPage.isBackpackMarkedAsAdded(), is(true));
    }

    @Step
    public void starts_checkout() {
        dictionaryPage.startCheckout();
    }

    @Step
    public void completes_checkout_with(String firstName, String lastName, String zipCode) {
        dictionaryPage.completeCheckoutWith(firstName, lastName, zipCode);
    }

    @Step
    public void should_see_order_completed_message() {
        assertThat(dictionaryPage.isOrderCompleted(), is(true));
    }

    @Step
    public void should_be_on_checkout_step_one() {
        assertThat(dictionaryPage.isOnCheckoutStepOne(), is(true));
    }

    @Step
    public void goes_back_to_inventory() {
        dictionaryPage.goToInventoryPage();
    }
}