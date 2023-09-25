package starter.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import starter.Common.HelperClass;
import starter.Data.ProductData;
import starter.Data.ProductionUnitData;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.core.Serenity.getDriver;

public class baseStepDefinition {

    @DataTableType
    public ProductData productEntry(Map<String, String> entry) {
        return new ProductData(
                entry.get("sku"),
                entry.get("description"),
                entry.get("category"),
                entry.get("target"),
                entry.get("associatedPU"));
    }

    @DataTableType
    public ProductionUnitData ProductionUnitEntry(Map<String, String> entry) {
        return new ProductionUnitData(
                entry.get("Name"),
                entry.get("Availability"),
                entry.get("Performance"),
                entry.get("GlobalProductionUnitQuality"),
                entry.get("ConvertedUnitName"),
                entry.get("CostPerHour"),
                entry.get("GiveawaySamplingSession"),
                entry.get("DowntimeDelay"),
                entry.get("EndOfDowntimeDelay"),
                entry.get("DowntimeJustificationDelay"));
    }

    private HelperClass helper = new HelperClass();

    public void clickOnElement(WebElement element) {
        WebDriverWait wait = null; //new WebDriverWait(getDriver(),Duration.ofSeconds(60));
        Actions actions = new Actions(getDriver());
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            actions.moveToElement(element).click().perform();
        }
    }

    public void clickOnElementByXpath(String path){
        WebElement element = getDriver().findElement(By.xpath(path));
        clickOnElement(element);
    }

    public void waitForUserPage() {
        getDriver().getPageSource().contains("Creation & edition of users are cross-factories. You can assign any of your entitled production unit(s).");
    }

    public void validatePageTitle(Actor actor, String title) {
        String path = "//h1[contains(text(),'" + title + "')]";
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        actor.attemptsTo(Ensure.that(elements.size() > 0).isTrue());
    }

    public void inputValue(String path, String inputString) {
//        WebElement element = $(net.serenitybdd.core.annotations.findby.By.xpath("//label[normalize-space()='Display Name *']/following-sibling::input"));
        WebElement element = getDriver().findElement(By.xpath(path));
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

    public void clickOnElementWithoutClickableChecking(WebElement element) {
        WebDriverWait wait = null;
        Actions actions = new Actions(getDriver());
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            actions.moveToElement(element).click().perform();
        }
    }
    public void goToMainPage() {
        //Template solution : move focus on main page
        dismissProfileWizard();

        Actions actions = new Actions(getDriver());
        WebElement pelement = getDriver().findElement(By.xpath("//button[contains(@title, 'Settings') or contains(@title, 'Paramètres') or contains(@title, 'Configuración') ]"));
        actions.moveToElement(pelement).build().perform();
    }

    public void isFactorySelected(Actor actor, String defaultFactory) {
        goToMainPage();
        //TODO : validate default factory
        String path = "//div[@role='listbox']//div[text()='<contains>']";
        String parentPath = "//div[@class='v-input__append-inner']";
        WebElement pe = getDriver().findElement(By.xpath(parentPath));
        clickOnElement(pe);
        //get the selected factory name
        //wait for element display
        path = path.replace("<contains>", defaultFactory);
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        if (elements.size() > 0) {
            WebElement element = getDriver().findElement(By.xpath(path));
            String factoryName = element.getText();

            //Default factory is selected, check by grandparent element
            WebElement pElement = element.findElement(By.xpath("../.."));
            actor.attemptsTo(Ensure.that(pElement.getAttribute("aria-selected").equalsIgnoreCase("true")).isTrue());
        }
    }
    public boolean waitForExisted(String path){
        Assert.assertTrue(getDriver().findElements(By.xpath(path)).size()>0);
        return true;
     }

    public void confirmDeletion(){
        String path = "//span[text()=' Yes, I do! ']";
        clickOnElement(getDriver().findElement(By.xpath(path)));
    }

    public List<WebElement> getDropDownListByXpath(String unitListStr) throws InterruptedException {
//        String unitListStr = "//label[text() = 'Unit']";
        //
        WebElement element = getDriver().findElement(By.xpath(unitListStr));
        String forId = element.getAttribute("for");

        //System.out.println("++++++++++ id = " + forId);

        Thread.sleep(1000);
        waitForExisted("//input[@id='" + forId + "']");
        //System.out.println("++++++++++ wait for id = " + forId);
        clickOnElement(getDriver().findElement(By.id(forId)));

        String listId = forId.replace("input", "list");

//        String bntList = "//div[@aria-owns= '" + listId + "']";
//        ; //"//div[@aria-owns= 'list-312']";
//        baseStep.waitForExisted(bntList);
//        baseStep.clickOnElement(getDriver().findElement(By.xpath(bntList)));

        String listStr = "//div[@id='" + listId + "']/div/div/div";
        List<WebElement> allLinks = getDriver().findElements(By.xpath(listStr));
        return allLinks;
    }

//    public void validateDataTableAgainstListElements(List<WebElement> allLinks, DataTable table) {
//        ArrayList<String> expectedResultList = helper.getValidations(table);
//        Iterator<WebElement> itr = allLinks.iterator();
////        while (itr.hasNext()) {
////            String text = getInnerText(itr.next());
////            System.out.println("Unit option = " + text);
////        }
//        Ensure.that(allLinks.size() == expectedResultList.size()).isTrue();
//        expectedResultList.forEach((e) -> {
//            System.out.println("Option name   = " + e);
//            boolean flag = findInElementList(e, allLinks);
//            Ensure.that(flag).isTrue();
//        });
//    }


    public boolean findInElementList(String target, List<WebElement> allRows) {
        boolean found = false;
        for (WebElement row : allRows) {
            String expected = getInnerText(row);//row.getText();
            if (target.trim().equalsIgnoreCase(expected.trim())) {
                found = true;
                System.out.println("---------- expected =" + expected);
                break;
            }
        }
        return found;
    }

    public String getInnerText(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (String) js.executeScript("return arguments[0].innerText;", element);
    }

    public void waitForElementClickable(WebElement element){

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(120));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    //   TODO : add spain setting word
    public void openProfileWizard(){
        WebElement pelement = getDriver().findElement(By.xpath("//button[contains(@title, 'Settings') or contains(@title, 'Paramètres')  or contains(@title, 'Configuración')]"));

        if (pelement.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
            //element = getDriver().findElement(By.cssSelector(".v-avatar.activator"));
            clickOnElement(pelement);
        }
    }
    //   TODO : add spain setting word
    public void dismissProfileWizard(){
        WebElement pelement = getDriver().findElement(By.xpath("//button[contains(@title, 'Settings') or contains(@title, 'Paramètres')  or contains(@title, 'Configuración')]"));

        if (pelement.getAttribute("aria-expanded").equalsIgnoreCase("true")) {
            //element = getDriver().findElement(By.cssSelector(".v-avatar.activator"));
            clickOnElement(pelement);
        }
    }

    public List<WebElement> getDropdownListByAttributeAriaOwns(String path) throws InterruptedException, SQLException {

        String newStr = path; //"//div[@class='v-input__control']/div[@role='button']";

        WebElement element = getDriver().findElement(By.xpath(newStr));
        clickOnElement(element);
        //page refresh after clicking dropdowm list
        //handle timing issue in maven run
        Thread.sleep(2000);

        String dropdownListId = element.getAttribute("aria-owns");

//        String listId = dropdownListId.replace("input", "list");
//
//        System.out.println("replaced list id  =" + listId);

        WebElement parentElement = getDriver().findElement(By.id(dropdownListId));

        List<WebElement> listItems = parentElement.findElements(By.className("v-list-item__title"));

//       dismiss dropdown listItems
        clickOnElement(element);

        return listItems;
    }

    public boolean validateElementListAgainstDataTable(List<WebElement> lists, DataTable table ){

        ArrayList<String> expectedResultList = helper.getValidations(table);
        System.out.println("Table size = " + expectedResultList.size() + " vs Web element size = " + lists.size());
        Assert.assertTrue(expectedResultList.size() == lists.size());
        boolean foundAll= true;

        for(int i = 0; i<expectedResultList.size(); i++){
            String e = expectedResultList.get(i);
            boolean found = findInElementList(e, lists);
            Assert.assertTrue(found);
            foundAll = found && foundAll;
        }
        return foundAll;
    }
    public void validateElementListAgainstDataTableByXpath(String path, DataTable table){
        WebElement element = getDriver().findElement(By.xpath(path));
        String divId = element.getAttribute("aria-owns");
        String listItemPath = "//div[@id='" + divId + "']//div[@class='v-list-item__title' and contains(text(), '<contains>')]";
        List<WebElement> allList = getDriver().findElements(By.xpath(listItemPath));
        validateElementListAgainstDataTable(allList, table);
    }
    public void dismissPopupWizard(){
        //dismiss wizard
        String path = "//div[contains(@class, 'v-dialog__content--active')]//button[@title='Close this window']";
        WebElement element = getDriver().findElement(By.xpath(path));
        clickOnElement(element);

//        String path = "//div[contains(@class, 'v-dialog__content--active')]//button[@title='Close this window']";
//        List<WebElement> elements = getDriver().findElements(By.xpath(path));
//        if(elements.size()==1) {
//            clickOnElement(elements.get(0));
//        }
//        if(elements.size()>1){
//            clickOnElement(elements.get(elements.size()-1));
//        }
    }

    public void clickInputBySiblingLable(String label){
//        there are 2 dialog
        String path = "//div[contains(@class, 'v-dialog__content--active') and @role = 'dialog']//label[normalize-space()='"+ label + "']/following-sibling::input";
        WebElement element = getDriver().findElement(By.xpath(path));
        clickOnElement(element);
    }

}
