package starter.Pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static net.serenitybdd.core.Serenity.getDriver;

public class LoginPageSteps {

    public static Performable Login(String name, String pw) {
        //Signin page

        WebElement e = getDriver().findElement(By.xpath("//span[normalize-space()='Sign In']"));
        clickOnElement(e);

        return Task.where("{0} login for '" + name + "'",
                Enter.theValue(name)
                        .into(LoginPage.SIGN_NAME)
                        .thenHit(Keys.ENTER),
                Enter.theValue(pw)
                        .into(LoginPage.PASSWORD)
                        .thenHit(Keys.ENTER),
               Click.on(LoginPage.SIGNIN_BUTTON)
        );
    }

    public static void loginTilelytics(String name, String pw) throws InterruptedException {
        String path = "//span[@class='v-btn__content' and contains(text(), ' Sign In')]";
        WebElement element = getDriver().findElement(By.xpath(path));
        Actions action = new Actions(getDriver());
        action.moveToElement(element).click().perform();


        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInName")));

        Thread.sleep(1000);
        element = getDriver().findElement(By.id("signInName"));
        enterKeys(element,name );

        Thread.sleep(1000);
        element = getDriver().findElement(By.id("password"));
        enterKeys(element,pw);

        Thread.sleep(1000);
        element = getDriver().findElement(By.id("next"));
        clickOnElement(element);

    }

    public static void enterKeys(WebElement element, String inputString) {

            Actions actions = new Actions(getDriver());
            actions.click(element)
                    .keyDown(Keys.CONTROL)
                    .sendKeys("a")
                    .keyUp(Keys.CONTROL)
                    .sendKeys(Keys.BACK_SPACE)
                    .sendKeys(inputString)
                    .build()
                    .perform();
    }

    private static void clickOnElement(WebElement element) {
        WebDriverWait wait = null;
        Actions actions = new Actions(getDriver());
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            actions.moveToElement(element).click().perform();
        }
    }


}
