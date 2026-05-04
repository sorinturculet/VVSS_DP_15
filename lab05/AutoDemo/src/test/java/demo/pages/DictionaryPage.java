package demo.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("https://www.saucedemo.com/")
public class DictionaryPage extends PageObject {

    @FindBy(id = "user-name")
    private WebElementFacade usernameField;

    @FindBy(id = "password")
    private WebElementFacade passwordField;

    @FindBy(id = "login-button")
    private WebElementFacade loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElementFacade loginError;

    @FindBy(css = ".title")
    private WebElementFacade pageTitle;

    @FindBy(id = "react-burger-menu-btn")
    private WebElementFacade menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElementFacade logoutButton;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElementFacade addBackpackButton;

    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElementFacade removeBackpackButton;

    @FindBy(css = ".shopping_cart_badge")
    private WebElementFacade cartBadge;

    @FindBy(css = ".shopping_cart_link")
    private WebElementFacade cartLink;

    @FindBy(id = "checkout")
    private WebElementFacade checkoutButton;

    @FindBy(id = "first-name")
    private WebElementFacade firstNameField;

    @FindBy(id = "last-name")
    private WebElementFacade lastNameField;

    @FindBy(id = "postal-code")
    private WebElementFacade postalCodeField;

    @FindBy(id = "continue")
    private WebElementFacade continueButton;

    @FindBy(id = "finish")
    private WebElementFacade finishButton;

    @FindBy(css = ".complete-header")
    private WebElementFacade completeHeader;

    public void loginWith(String username, String password) {
        usernameField.type(username);
        passwordField.type(password);
        loginButton.click();
    }

    public String getLoginErrorMessage() {
        return loginError.waitUntilVisible().getText();
    }

    public boolean isOnInventoryPage() {
        return getDriver().getCurrentUrl().contains("/inventory.html")
                && pageTitle.waitUntilVisible().containsText("Products");
    }

    public void logout() {
        evaluateJavascript("arguments[0].click();", menuButton.waitUntilVisible());
        waitABit(500);
        evaluateJavascript("arguments[0].click();", logoutButton.waitUntilVisible());
    }

    public boolean isOnLoginPage() {
        return getDriver().getCurrentUrl().endsWith("/")
                && usernameField.waitUntilVisible().isVisible();
    }

    public void addBackpackToCart() {
        evaluateJavascript("arguments[0].click();", addBackpackButton.waitUntilClickable());
    }

    public String getCartBadgeCount() {
        return cartBadge.waitUntilVisible().getText();
    }

    public void openCart() {
        getDriver().get("https://www.saucedemo.com/cart.html");
    }

    public void startCheckout() {
        getDriver().get("https://www.saucedemo.com/checkout-step-one.html");
    }

    public void completeCheckoutWith(String firstName, String lastName, String zipCode) {
        firstNameField.waitUntilVisible().type(firstName);
        lastNameField.type(lastName);
        postalCodeField.type(zipCode);
        continueButton.click();
        finishButton.waitUntilClickable().click();
    }

    public boolean isOrderCompleted() {
        return completeHeader.waitUntilVisible().containsText("Thank you for your order!");
    }

    public boolean isOnCartPage() {
        return getDriver().getCurrentUrl().contains("/cart.html");
    }

    public boolean isBackpackPresentInCart() {
        return !findAll(By.className("cart_item")).isEmpty();
    }

    public boolean isBackpackMarkedAsAdded() {
        return removeBackpackButton.waitUntilVisible().isVisible();
    }

    public boolean isOnCheckoutStepOne() {
        return getDriver().getCurrentUrl().contains("/checkout-step-one.html");
    }

    public void goToInventoryPage() {
        getDriver().get("https://www.saucedemo.com/inventory.html");
    }
}