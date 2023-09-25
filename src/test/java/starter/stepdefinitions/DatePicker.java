package starter.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.ensure.web.ElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import starter.Common.HelperClass;
import starter.Common.RegisteredUser;
import starter.navigation.NavigateTo;

import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.core.Serenity.getDriver;

public class DatePicker {

    baseStepDefinition baseStep = new baseStepDefinition();;
    HelperClass helper = new HelperClass();
    RegisteredUser SUPERVISOR = new RegisteredUser(
            "supervisor", "ellenTest@worximity.com", "Test1234");


    @When("he selects {string} from period list")
    public void heSelectsFromPeriodList(String title) {

        String buttonPath = "//button[@aria-label='append icon']";
        WebElement elementBnt = getDriver().findElement(By.xpath(buttonPath));
        baseStep.clickOnElementWithoutClickableChecking(elementBnt);

//        Customize your Start and End dates
        String path = "//div[contains(text(),'" + title + "')]";
        WebElement element = getDriver().findElement(By.xpath(path));
        baseStep.clickOnElement(element);
    }

    @Then("{actor} should see period range of {string} month")
    public void heShouldSeePeriodRangeOfMonth(Actor actor, String numberOfMonth) {
        String bnt = "//button[@aria-label='Previous month']";
        WebElement bntElement = getDriver().findElement(By.xpath(bnt));
        int count = Integer.parseInt(numberOfMonth);
        Boolean done = false;
        //        Click previous month button 15 times for tilelytics
        for (int i=0; i<count && !done; i++) {
            done = helper.isAttribtuePresent(bntElement, "disabled");
            bntElement = getDriver().findElement(By.xpath(bnt));
            baseStep.clickOnElement(bntElement);
        }
        String att = bntElement.getAttribute("disabled");
        helper.logToReport("Tilelytics time range = 15 months", att);

        actor.attemptsTo(
                Ensure.that(att.equalsIgnoreCase("true")).isTrue()
        );


        //dismiss dropdown list

        String path = "//button[@title='Close this window']";
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        if(elements.size()==1) {
            baseStep.clickOnElement(elements.get(0));
        }
        if(elements.size()>1){
            baseStep.clickOnElement(elements.get(elements.size()-1));
        }
    }

    @When("he dismisses the wizard")
    public void heDismissesTheWizard() {
        baseStep.dismissPopupWizard();
    }

    @Then("in activity page, he should see SF4 exports")
    public void inActivityPageHeShouldSeeSFExports(DataTable table) {
        String expStr = "//span[normalize-space()='<contains>']";

        ArrayList<String> expectedResultList = helper.getValidations(table);

        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
            Ensure.that(elements.size()).isGreaterThan(0);
        });
    }

    @When("{actor} clicks on {string}")
    public void theUserClicksOn(Actor actor, String term) {

        String expStr = "//span[normalize-space()='<contains>']";
        String newStr = expStr.replace("<contains>", term);
        WebElement element = getDriver().findElement(By.xpath(newStr));
        baseStep.clickOnElement(element);
    }

    @Then("{actor} should see {string} popup")
    public void heShouldSeePopupWithRange(Actor actor, String title) {
        String h2Path = "//h2[normalize-space()='<contains>']";
        String newStr = h2Path.replace("<contains>", title);
        List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
        actor.attemptsTo(Ensure.that(elements.size()).isEqualTo(1));

//        TODO : effective from and effective until

    }

    @When("he clicks on input {string}")
    public void heClicksOnInput(String label) throws InterruptedException {
        Thread.sleep(1000);
        baseStep.clickInputBySiblingLable(label);
        Thread.sleep(1000);
    }

    @Then("{actor} should see {string} button")
    public void heShouldSeeButton(Actor actor, String title) {
        String path = "//span[normalize-space()='<contains>']";
        String newStr = path.replace("<contains>", title);
        List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
        actor.attemptsTo(
                Ensure.that(elements.size()).isGreaterThan(0)
        );
    }

    @Then("{actor} should not see {string} button")
    public void heShouldNotSeeButton(Actor actor, String title) {
        String path = "//span[normalize-space()='<contains>']";
        String newStr = path.replace("<contains>", title);
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        actor.attemptsTo(
                Ensure.that(elements.size()).isEqualTo(0)
        );
    }
}
