package starter.stepdefinitions;

import com.google.common.base.Function;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.af.En;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.exceptions.NoSuchElementException;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.ensure.web.ElementLocated;
import net.thucydides.core.util.EnvironmentVariables;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import starter.Common.DBConnection;
import starter.Common.HelperClass;
import starter.Common.RegisteredUser;
import starter.Data.DBFactoryData;
import starter.Data.DBPUData;
import starter.navigation.NavigateTo;

import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static net.serenitybdd.core.Serenity.getDriver;

public class TilelyticsOverviewStepDefinitions {

    baseStepDefinition baseStep = new baseStepDefinition();;
    HelperClass helper = new HelperClass();
    RegisteredUser SUPERVISOR = new RegisteredUser(
            "supervisor", "ellenTest@worximity.com", "Test1234");

    @Given("{actor} is accessing Tilelytics")
    public void userIsAccessingTilelytics(Actor actor) {
        actor.wasAbleTo(NavigateTo.theTilelyticsMainPage());
    }

    @Then("{actor} should see Tilelytics default overview page")
    public void heShouldSeeTilelyticsDefaultOverviewPage(Actor actor) {
        //Temp : defect - refresh is needed to get
        getDriver().navigate().refresh();
        List<WebElement> elements = getDriver().findElements(By.id("companyOverview"));
        actor.attemptsTo(
            Ensure.that(elements.size() > 0).isTrue()
        );
    }

    @Then("{actor} should see Tilelytics default overview page with company name")
    public void heShouldSeeTilelyticsDefaultOverviewPageWithCompanyName(Actor actor) {

        String companyName = DBConnection.getDBInstance().companyName;

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.urlContains("/company-overview"));

        //TODO : defect - refresh is needed to get
//        getDriver().navigate().refresh();

        List<WebElement> elements = getDriver().findElements(By.id("companyOverview"));
        actor.attemptsTo(
                Ensure.that(elements.size() > 0).isTrue()
        );
        WebElement h1Element = getDriver().findElement(By.tagName("h1"));

        String str = h1Element.getText();
        String str2 = getInnerText(h1Element);

        actor.attemptsTo(
//                Ensure.that(str2.equalsIgnoreCase(companyName + "Overview")).isTrue()
                Ensure.that(str2.equalsIgnoreCase("Overview")).isTrue()
        );
    }

    private String getInnerText(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (String) js.executeScript("return arguments[0].innerText;", element);
    }

    @Then("{actor} should see Admin links in side bars")
    public void heShouldSeeAdminLinksInSideBars(Actor actor, DataTable table) {
//
//        WebElement element = getDriver().findElement(By.xpath("//div[@class='v-list-item__title' and contains(text(), 'Admin')]"));
//        baseStep.clickOnElement(element);

        ArrayList<String> expectedResultList = helper.getValidations(table);
        String expStr = "//div[contains(text(),'<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            List<WebElement> ele = getDriver().findElements(By.xpath(newStr));
            actor.attemptsTo(
                    Ensure.that(ele.size()>0).isTrue()
            );

        });
    }

    @Then("{actor} should see Factory links in side bars")
    public void heShouldSeeFactoryLinksInSideBars(Actor actor) throws SQLException {

//        ArrayList<String> expectedResultList = helper.getValidations(table);
        String expStr = "//div[@class='v-list-item__title' and contains(text(),'<contains>')]";

        List<DBFactoryData> expectedResultList = DBConnection.getDBInstance().getFactoryList();
        expectedResultList.forEach((e) -> {

            System.out.println("Validate string =" + e.getName());
            String newStr = expStr.replace("<contains>", e.getName());
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            actor.attemptsTo(
                    Ensure.that(ElementLocated.by(newStr)).isDisplayed()
            );

        });
    }

    @Then("{actor} should see KPI tiles")
    public void heShouldSeeKPITiles(Actor actor, DataTable table) {
        //h4 tag :
        //path = // div[@class='v-responsive__content']//h4
        ArrayList<String> expectedResultList = helper.getValidations(table);
        String expStr = "//section/div/h4[contains(text(),'<contains>')]";
//        String expStr = "//h4[contains(text(),'<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            List<WebElement> ele = getDriver().findElements(By.xpath(newStr));
            actor.attemptsTo(
                Ensure.that(ele.size()).isGreaterThan(0)
            );
        });
    }

//    @Then("{actor} should see Tilelytics default factory {string} is displayed")
//    public void heShouldSeeTilelyticsDefaultFactoryIsDisplayed(Actor actor, String defaultFactory) {
//        //TODO : validate default factory
//        String path = "//div[@role='listbox']//div[text()='<contains>']";
//        String parentPath = "//div[@class='v-input__append-inner']";
//        baseStep.waitForExisted(parentPath);
//        WebElement pe = getDriver().findElement(By.xpath(parentPath));
//        baseStep.clickOnElement(pe);
//        //get the selected factory name
//        //wait for element display
//        path = path.replace("<contains>", defaultFactory);
//        List<WebElement> elements = getDriver().findElements(By.xpath(path));
//        if (elements.size() > 0) {
//            WebElement element = getDriver().findElement(By.xpath(path));
//            String factoryName = element.getText();
//            Ensure.that(defaultFactory.equalsIgnoreCase(factoryName)).isTrue();
//        }
//
//        //dismiss dropdown list
//        baseStep.clickOnElement(pe);
//    }

    @Then("{actor} should see Opportunities section")
    public void heShouldSeeOpportunitiesSection(Actor actor) {
        //TODO : validate against QB query
        String path = "//h2[contains(text(), 'Opportunities')]";
        //if no any opportunities,
        String noOpportunitiesPath = "//div[@class='no-opportunities']";
        List<WebElement> noOpportunitiesElements = getDriver().findElements(By.xpath(noOpportunitiesPath));
        if (noOpportunitiesElements.size() > 0) {

            //With opportunities
            String withOpportunities = "//div[@role='list']";
            String opportunitiesCount = "//div[@role='list']/div";
            List<WebElement> opportunities = getDriver().findElements(By.xpath(opportunitiesCount));
            System.out.println("Opportunities count = " + opportunities.size());
        } else
            System.out.println("NO OPPORTUNITIES");

    }

    @Then("{actor} should see chart options")
    public void heShouldSeeChartOptions(Actor actor, DataTable table) {
        ArrayList<String> expectedResultList = helper.getValidations(table);

        String buttonPath = "//div[@class='row']//div[@class='v-input__slot' and @role='button']";
        WebElement elementBnt = getDriver().findElement(By.xpath(buttonPath));
        baseStep.clickOnElement(elementBnt);
        String expStr = "//div[@class='v-list-item__content']//div[contains(text(),'<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            actor.attemptsTo(
                    Ensure.that(ElementLocated.by(newStr)).isDisplayed()
            );

        });
        //dismiss dropdown list
        baseStep.clickOnElement(elementBnt);
    }

    @Then("{actor} should see charts")
    public void heShouldSeeCharts(Actor actor, DataTable table) {
        ArrayList<String> expectedResultList = helper.getValidations(table);

        String buttonPath = "//div[@class='row']//button";

        List<WebElement> elementBnts = getDriver().findElements(By.xpath(buttonPath));


        actor.attemptsTo(
                Ensure.that(elementBnts.size()).isEqualTo(expectedResultList.size())
        );


        String expStr = "//div[@class='row']//button[@title ='<contains>']";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
            actor.attemptsTo(
                    Ensure.that(elements.size()).isGreaterThan(0)
            );

        });
    }

    @When("{actor} should be able to switch to table view")
    public void heShouldBeAbleToSwitchToTable(Actor actor) {
        String pathId = "tableViewIcon";
        WebElement element = getDriver().findElement(By.id(pathId));
        baseStep.clickOnElement(element);

        String tableViewClassPath = "table-data-view";
        List<WebElement> elements = getDriver().findElements(By.className(tableViewClassPath));

        actor.attemptsTo(
                Ensure.that(elements.size() > 0).isTrue()
        );

    }

    @Then("{actor} should be able to see factories in chart")
    public void heShouldBeAbleToSeePUs(Actor actor) throws SQLException {
//        ArrayList<String> expectedResultList = helper.getValidations(table);

        List<DBFactoryData> expectedResultList = DBConnection.getDBInstance().getFactoryList();
        String colName = "//table//tbody/tr/td[contains(text(), '<contains>')]";

        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String fName = e.getName();
            String newStr = colName.replace("<contains>", fName);
            System.out.println("replaced string =" + newStr);
            List<WebElement> elements = getDriver().findElements(By.xpath(colName));
            actor.attemptsTo(
                    Ensure.that(elements.size() > 0).isTrue()
            );

        });
    }

    @Then("{actor} should see potential gain")
    public void heShouldSeePotientialGain(Actor actor) {
        String path = "//h4[contains(text(),'Total potential gain')]";
        List<WebElement> elements = getDriver().findElements(By.xpath(path));

        actor.attemptsTo(
                Ensure.that(elements.size() > 0).isTrue()
        );


        // TODO
        //Validate currency against DB
        //validate money amount
    }

    @Then("{actor} should see tooltips")
    public void heShouldSeeTooltips(Actor actor, DataTable table) {
        ArrayList<String> expectedResultList = helper.getValidations(table);
        String path = "//div[@class='v-tooltip__content']/span";

        expectedResultList.forEach((e) -> {
            List<WebElement> elements = getDriver().findElements(By.xpath(path));
            actor.attemptsTo(
                    Ensure.that(elements.size() > 0).isTrue()
            );

            boolean found = false;
            for (WebElement element : elements) {
                String tip = element.getText();
                if (tip.equalsIgnoreCase(e)) {
                    found = true;
                    break;
                }
            }
            actor.attemptsTo(
                    Ensure.that(found == true).isTrue()
            );

        });
    }

    @Then("{actor} should see accessible factory list in company overview page")
    public void heShouldSeeAccessibleFactoryList(Actor actor, DataTable table) {

        //  //div[@role='listbox']//div[@class='v-list-item__title' and text()='<contains>']
        ArrayList<String> expectedResultList = helper.getValidations(table);

        String buttonPath = "//div[@role='combobox']//div[@class='v-input__append-inner']";
        WebElement elementBnt = getDriver().findElement(By.xpath(buttonPath));
        baseStep.clickOnElement(elementBnt);

        String expStr = "//div[@role='listbox']//div[@class='v-list-item__title' and text()='<contains>']";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));

            actor.attemptsTo(
                    Ensure.that(ElementLocated.by(newStr)).isDisplayed()
            );

        });
        //dismiss dropdown list
        baseStep.clickOnElement(elementBnt);
    }

    @Then("{actor} should see period list")
    public void heShouldSeePeriodList(Actor actor, DataTable table) {
        // //ul[@role='listbox']/li/div/div[contains(text(), '<contains>')]

        ArrayList<String> expectedResultList = helper.getValidations(table);

        String buttonPath = "//button[@aria-label='append icon']";
        WebElement elementBnt = getDriver().findElement(By.xpath(buttonPath));
        baseStep.clickOnElementWithoutClickableChecking(elementBnt);

        String expStr = "//ul[@role='listbox']/li/div/div[contains(text(), '<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            List<WebElement> ele = getDriver().findElements(By.xpath(newStr));
            actor.attemptsTo(
                    Ensure.that(ele.size()).isGreaterThan(0)
            );

        });
        //dismiss dropdown list
//        TODO: add spain
        String path = "//button[@title=\'Close this window\' or @title=\'Fermer cette fenêtre\']";
        WebElement element = getDriver().findElement(By.xpath(path));
        baseStep.clickOnElement(element);
    }

//    private void validatePeriodList(String dropDownListName){
////        String unitListStr = "//label[text() = '" + dropDownList + "']";
////        List<WebElement> dropdownElements = getDriver().findElements(By.xpath(unitListStr));
////        WebElement element = dropdownElements.get(index - 1);//getDriver().findElement(By.xpath(unitListStr));
//
//        String path = "//label[normalize-space()='Period']";
//        WebElement element = getDriver().findElement(By.xpath(path));
//
//        String forId = element.getAttribute("for");
//
////        baseStep.clickOnElement(getDriver().findElement(By.xpath("//div[@id='" + forId + "']")));
//
//        String listId = forId.replace("input", "list");
//        String bntList = "//div[@aria-owns= '" + listId + "']";
//
//        baseStep.clickOnElement(getDriver().findElement(By.xpath(bntList)));
//
//        String listStr = "//div[@id='" + listId + "']/div/div/div";
//
//        String itemStr = "//div[@id='" + listId + "']/div/div/div[text()='" + text + "']";
//        baseStep.clickOnElement(getDriver().findElement(By.xpath(itemStr)));
//    }

    @Then("{actor} should see opportunity unit menu")
    public void heShouldSeeOpportunityUnitMenu(Actor actor, DataTable table) {
        // //h5[contains(text(), 'Opportunity unit')]

        ArrayList<String> expectedResultList = helper.getValidations(table);

        String buttonPath = "//form//following-sibling::button";
        WebElement elementBnt = getDriver().findElement(By.xpath(buttonPath));
        baseStep.clickOnElement(elementBnt);

        String subMenu = "//div[@role='menu']/article/h5[text() = 'Opportunity unit']";

        actor.attemptsTo(
                Ensure.that(getDriver().findElements(By.xpath(subMenu)).size() > 0).isTrue()
        );

        String expStr = "//span[@class='v-btn__content' and contains(text(), '<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
            actor.attemptsTo(
                    Ensure.that(elements.size()).isGreaterThan(0)
            );

        });
        //dismiss dropdown list
        baseStep.clickOnElement(elementBnt);
    }

    @And("{actor} navigates to DPM")
    public void heNavigatesToDPM(Actor actor) {

        //Bring side bar
        baseStep.clickOnElement(getDriver().findElement(By.xpath("//a[@title='Tilelytics']")));
        // //div[contains(text(),'Performance Manager')]
        baseStep.clickOnElement(getDriver().findElement(By.xpath("//div[contains(text(),'Performance Manager')]")));

        List<WebElement> elements = getDriver().findElements(By.xpath("//h1[contains(text(), 'Shift Changes')]"));
        actor.attemptsTo(
                Ensure.that(elements.size()).isGreaterThan(0)
                );

        //navigate to DPM page
        // String path = "//label[contains(text(), 'Selected Factory')]/following-sibling::div[@class='v-select__selections']";
        dismissMenu();

        //validate DPM page

        String path = "//h1[contains(text(), 'Shift Changes')]";
        waitElementForPresent(path);
    }

    @Then("{actor} should see DPM options")
    public void heShouldSeeDPMOptions(Actor actor, DataTable table) {
        // //div[normalize-space()='Shift Changes']

        ArrayList<String> expectedResultList = helper.getValidations(table);

        String buttonPath = "//h1[contains(text(), 'Shift Changes')]/button";

        baseStep.clickOnElement(getDriver().findElement(By.xpath(buttonPath)));
        String expStr = "//div[contains(text(),'<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));

            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });

        //dismiss dropdown
        baseStep.clickOnElement(getDriver().findElement(By.xpath(buttonPath)));
    }

    @Then("{actor} should see accessible factory list in DPM page")
    public void heShouldSeeAccessibleFactoryListInDPMPage(Actor actor) throws SQLException {
        List<DBFactoryData> expectedResultList = DBConnection.getDBInstance().getFactoryList();

        String buttonPath = "factoryFilter";
        WebElement elementBnt = getDriver().findElement(By.id(buttonPath));
        baseStep.clickOnElement(elementBnt);

        String expStr = "//div[@role='listbox']//div[@class='v-list-item__title' and text() = '<contains>']";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String fName = e.getName();
            String newStr = expStr.replace("<contains>", fName);

//            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));

            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });
        //dismiss dropdown list
        baseStep.clickOnElement(elementBnt);
    }

    @And("{actor} should see Potential Yearly Savings options")
    public void heShouldSeePotentialYearlySavingsOptions(Actor actor, DataTable table) {
        // //div[@role='menu']//div[@role='menuitem']/div[text()='View savings in currency']

        ArrayList<String> expectedResultList = helper.getValidations(table);

        String buttonPath = "//div[@class='savings-title']//button";
        WebElement elementBnt = getDriver().findElement(By.xpath(buttonPath));
        baseStep.clickOnElement(elementBnt);

        String expStr = "//div[@role='menu']//div[@role='menuitem']/div[text()='<contains>']";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));

            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });
        //dismiss dropdown list
        baseStep.clickOnElement(elementBnt);
    }

    @And("{actor} should see tooltips in DPM page")
    public void heShouldSeeTooltipsInDPMPage(Actor actor, DataTable table) {

        ArrayList<String> expectedResultList = helper.getValidations(table);
        String path = "//div[@class='v-tooltip__content']/span";

        expectedResultList.forEach((e) -> {
            List<WebElement> elements = getDriver().findElements(By.xpath(path));
            Ensure.that(elements.size() > 0).isTrue();
            boolean found = false;
            for (WebElement element : elements) {
                String tip = element.getText();
                if (tip.equalsIgnoreCase(e)) {
                    found = true;
                    break;
                }
            }
            Ensure.that(found == true).isTrue();
        });

    }

    @When("{actor} selects default factory")
    public void heSelectsFactory(Actor actor) {
        String factoryName = DBConnection.getDBInstance().defaultFactoryName;
        String buttonPath = "factoryFilter";
        WebElement elementBnt = getDriver().findElement(By.id(buttonPath));
        baseStep.clickOnElement(elementBnt);

        String expStr = "//div[@role='listbox']//div[@class='v-list-item__title' and text() = '" + factoryName + "']";

        Ensure.that(ElementLocated.by(expStr)).isDisplayed();
        baseStep.clickOnElement(getDriver().findElement(By.xpath(expStr)));
    }

    @Then("{actor} should see accessible PU lines list in DPM page")
    public void heShouldSeeAccessiblePULinesListInDPMPage(Actor actor) throws SQLException {

//        ArrayList<String> expectedResultList = helper.getValidations(table);
        List<DBPUData> expectedResultList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();
        String buttonPath = "//div[@role= 'combobox']//div[@class='v-select__selections']";
        List<WebElement> elements = getDriver().findElements(By.xpath(buttonPath));

        //First dropdown list is PU list
        WebElement elementBnt = elements.get(0);
        //first combox  id = productionLinesFilter
        //second combox id = workShiftsFilter
        baseStep.clickOnElement(elementBnt);

        String expStr = "//div[@class='v-list-item__title' and contains(text(), '<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String puName = e.getPUName();
            String newStr = expStr.replace("<contains>", puName);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));

            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });
        //dismiss dropdown list
        baseStep.clickOnElement(elementBnt);
    }

    @When("{actor} selects SF4 PU line")
    public void heSelectsPULine(Actor actor) {

        String buttonPath = "//div[@role= 'combobox']//div[@class='v-select__selections']";
        List<WebElement> elements = getDriver().findElements(By.xpath(buttonPath));

        WebElement elementBnt = elements.get(0);
        //first combox  id = productionLinesFilter
        //second combox id = workShiftsFilter
        baseStep.clickOnElement(elementBnt);

        String PUName = helper.getSF4PU();
        String expStr = "//div[text() = '" + PUName + "']";
        baseStep.clickOnElement(getDriver().findElement(By.xpath(expStr)));
        //dismiss dropdown
        baseStep.clickOnElement(elementBnt);

//        TODO : find SF4 PU

    }

    @Then("{actor} should see shift transactions list")
    public void heShouldSeeShiftTransactions(Actor actor) {

        String buttonPath = "//div[@role= 'combobox']//div[@class='v-select__selections']";
        List<WebElement> bntElements = getDriver().findElements(By.xpath(buttonPath));

        WebElement elementBnt = bntElements.get(1);
        //first combox  id = productionLinesFilter
        //second combox id = workShiftsFilter
        baseStep.clickOnElement(elementBnt);

        String path = "//div[@role='listbox']//div[@class='v-list-item__title'  and contains(text(), 'To')]";
        List<WebElement> elements = getDriver().findElements(By.xpath(path));

        Ensure.that(elements.size()>0).isTrue();

        baseStep.clickOnElement(elementBnt);
    }

    private void waitForElement(WebElement element){

        WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(120));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void dismissMenu(){

            WebElement pelement = getDriver().findElement(By.xpath("//button[contains(@title, 'Settings')]"));
            if (pelement.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
                //element = getDriver().findElement(By.cssSelector(".v-avatar.activator"));
                baseStep.clickOnElement(pelement);
            }

            //Dismiss Profile pannel
        baseStep.clickOnElement(pelement);

    }

    @And("{actor} clicks on Apply Filters button")
    public void heClicksOnApplyFiltersButton(Actor actor) {
        // //button/span[contains(text(), 'Apply Filters')]
        String applyBntPath = "//button/span[contains(text(), 'Apply Filters')]";

    }

    @When("{actor} clicks on table view")
    public void heClicksOnTableView(Actor actor) {
        String path = "tableViewIcon";
        WebElement element= getDriver().findElement(By.id(path));
        baseStep.clickOnElement(element);
    }

    @Then("{actor} should see table view")
    public void heShouldSeeTableView(Actor actor) {
        //shift change Duration progress table
        String shiftChangeTable = "//thead[@class='v-data-table-header']";

        Ensure.that(getDriver().findElements(By.xpath(shiftChangeTable)).size()>0).isTrue();

        //Top Downtime Causes During Shift Change table
        String path = "//h3[contains(text(), 'Top Downtime Causes During Shift Change')]";

        Ensure.that(getDriver().findElements(By.xpath(path)).size() >0).isTrue();
    }

    @Then("{actor} should see chart section with reports")
    public void heShouldSeeChartSection(Actor actor, DataTable table) {
        // //canvas[@id='shift-changes-line-chart']
        String canvasPath = "//canvas[@id='shift-changes-line-chart']";

        Ensure.that(getDriver().findElements(By.xpath(canvasPath)).size()>0).isTrue();
        String rightSideBar = "//label[text()='Show line interruptions']";

        ArrayList<String> expectedResultList = helper.getValidations(table);

        String expStr = "//dt[contains(text(), \"<contains>\")]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });

        //Top Downtime Causes During Shift Change table
        String path = "//h3[contains(text(), 'Top Downtime Causes During Shift Change')]";
        Ensure.that(getDriver().findElements(By.xpath(path)).size() >0).isTrue();
    }

//    @When("{actor} expands sidebar of {string}")
//    public void heExpandsSidebarOfFactory(Actor actor, String item) throws InterruptedException {
//        String defaultFactory = DBConnection.getDBInstance().defaultFactoryName;
//        String path = "//a[@title='Tilelytics']";
//        WebElement element = waitElementForPresent(path);
//
//        //Bring side bar
//        baseStep.clickOnElement(element);
//
//        baseStep.clickOnElement(getDriver().findElement(By.xpath("//div[contains(text(),'" + item +"')]")));
//
//        Thread.sleep(2000);
//
////        String sideBarItem = "//div[@class='v-list-item__title' and contains(text(), 'Factories')]";
////        element = getDriver().findElement(By.xpath(sideBarItem));
////        clickOnElementWithoutClickableChecking(element);
////
////        Thread.sleep(2000);
////        mouseHOverProfileIcon();
////        String title = "//h1[contains(text(), 'Factories')]";
////        Ensure.that(waitElementForPresent(title) !=null);
//
//    }

    @When("{actor} expands sidebar of default factory")
    public void heExpandsSidebarOfFactory(Actor actor) throws InterruptedException {
        String defaultFactory = DBConnection.getDBInstance().defaultFactoryName;
        String path = "//a[@title='Tilelytics']";
        WebElement element = waitElementForPresent(path);

        //Bring side bar
        baseStep.clickOnElement(element);

//        baseStep.clickOnElement(getDriver().findElement(By.xpath("//div[contains(text(),'" + defaultFactory + "')]")));
        baseStep.clickOnElement(getDriver().findElement(By.xpath("//div[text()='" + defaultFactory + "']")));

        Thread.sleep(2000);
    }

    @And("{actor} navigates to Factories page")
    public void heNavigatesToFactoryListPage(Actor actor) throws InterruptedException {
        String sideBarItem = "//div[@class='v-list-item__title' and contains(text(), 'Factories')]";
        WebElement element = getDriver().findElement(By.xpath(sideBarItem));
        baseStep.clickOnElement(element);
        Thread.sleep(2000);
        mouseHOverProfileIcon(actor);
        String title = "//h1[contains(text(), 'Factories')]";

        actor.attemptsTo(
                Ensure.that(waitElementForPresent(title) !=null).isTrue()
        );
    }

    @When("{actor} navigates to {string} of default factory")
    public void heNavigatesToOfFactory(Actor actor, String optionText) {
        String factoryName = DBConnection.getDBInstance().defaultFactoryName;
        WebElement element = getDriver().findElement(By.xpath("//a[@title='Tilelytics']"));
        //Bring side bar
        baseStep.clickOnElement(element);
//        String options = "//div[contains(text(), '" + optionText + "')]";
        String options =  "//div[@class='v-list-item__title'][normalize-space()='" + optionText + "']";
        List<WebElement> elements = getDriver().findElements(By.xpath(options));
        int count = elements.size();

        //TODO : first element
        baseStep.clickOnElement(elements.get(0));

        mouseHOverProfileIcon(actor);
        String path = "//h1[text()='" + factoryName + " - " + optionText + "']";

////        ######## Production Data Export page does not have factory
//        if(optionText.toLowerCase(Locale.ROOT).contains("data export")){
//            path = "//h1[contains(text(), '" + optionText + "')]";
//        }

        waitElementForPresent(path);
    }

    @When("{actor} navigates to factory {string} and option {string}")
    public void heNavigatesToFactoryAndOption(Actor actor, String factoryName, String optionText) {
        WebElement element = getDriver().findElement(By.xpath("//a[@title='Tilelytics']"));

        //Bring side bar
        baseStep.clickOnElement(element);

        baseStep.clickOnElement(getDriver().findElement(By.xpath("//div[contains(text(),'" + factoryName +"')]")));

        String options = "//div[contains(text(), '" + optionText + "')]";
        List<WebElement> elements = getDriver().findElements(By.xpath(options));
        int count = elements.size();

        //TODO : first element

        baseStep.clickOnElement(elements.get(0));

        mouseHOverProfileIcon(actor);
        String path = "//h1[text()='" + factoryName + " - " + optionText + "']";
        waitElementForPresent(path);
    }

//    @When("{actor} navigates to Availability and Downtime of factory {string}")
//    public void heNavigatesToAvailabilityAndDowntimeOfFactory(Actor actor, String factoryName) {
//
//        String optionText = "Availability & Downtime";
//        heNavigatesToFactoryAndOption(actor, factoryName, optionText);
//
//        mouseHOverProfileIcon();
//
//        String path = "//h1[text()='" + factoryName + " - " + optionText + "']";
//        waitElementForPresent(path);
//    }

    @Then("{actor} should see Availability and Downtime outline page")
    public void heShouldSeeAvailabilityAndDowntimeOutlinePage(Actor actor) {
        validateContentOfAvailabilityAndDowntime(actor);
    }

    @Then("{actor} should see report template")
    public void heShouldSeeReportTemplate(Actor actor, DataTable table) {

        String path = "//div[contains(@class, 'v-card__title') and contains(text(), '<contains>') ]";

        String canvasPath = "//div[@class='v-card__title']";
        Ensure.that(getDriver().findElements(By.xpath(canvasPath)).size()==4).isTrue();

        ArrayList<String> expectedResultList = helper.getValidations(table);

        String expStr = "//section//div[contains(text(), '<contains>') ]";

        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });
    }

    @And("{actor} should be able to download data")
    public void heShouldBeAbleToDownloadData(Actor actor) {

        String path = "//span[@class='v-btn__content' and contains(text(), 'Download')]";
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        Ensure.that(elements.size()>0).isTrue();
        //TODO : save file will be tested manually, all is not selected by default
        //baseStep.clickOnElement(getDriver().findElement(By.xpath(path)));
      }


    @Then("{actor} should see PU list in Production Data Export page")
    public void heShouldSeeDataExportPage(Actor actor) throws InterruptedException, SQLException {
        String path = "//label[text() = 'Production Unit(s)']";
        List<WebElement> allLinks = baseStep.getDropDownListByXpath(path);

//        baseStep.validateDataTableAgainstListElements(allLinks, table);

        List<String> expectedResultList = helper.getPUNameList();

        Iterator<WebElement> itr = allLinks.iterator();

        Ensure.that(allLinks.size() == expectedResultList.size()).isTrue();
        expectedResultList.forEach((e) -> {
            System.out.println("---------- e   = " + e);
            boolean flag = baseStep.findInElementList(e, allLinks);
            Ensure.that(flag).isTrue();
        });
    }



    @Then("{actor} should see factory list in Factories page")
    public void heShouldSeeFactoryList(Actor actor) throws SQLException {
        String path = "//tbody/tr";
//        ArrayList<String> expectedResultList = helper.getValidations(table);
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        List<DBFactoryData> expectedResultList = DBConnection.getDBInstance().getFactoryList();

        Ensure.that(expectedResultList.size() == elements.size()).isTrue();

        String expStr = "//tbody/tr/td[text()= '<contains>']";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e.getName());
            String newStr = expStr.replace("<contains>", e.getName());
            System.out.println("replaced string =" + newStr);
            Ensure.that(baseStep.waitForExisted(newStr)).isTrue();
        });

    }

    @When("{actor} edits default factory")
    public void heEditsDefaultFactory(Actor actor) {
        String factoryName = DBConnection.getDBInstance().defaultFactoryName;
        String path = "//tbody/tr";
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i).findElement(By.xpath("(//td)[1]"));
            String name = element.getText();
            name = getInnerText(element);
            if(name.equalsIgnoreCase(factoryName)){
                WebElement element2 = elements.get(i).findElement(By.xpath("(//td)[5]/a"));
                baseStep.clickOnElementWithoutClickableChecking(element2);
                break;
            }
        }

        String factoryPageValidation = "//h2[contains(text(), '" + factoryName + "')]";
        WebElement element = waitElementForPresent(factoryPageValidation);
        Ensure.that(element != null).isTrue();
    }


    @When("he updates default factory name as {string}")
    public void heUpdatesDefaultFactoryNameAs(String updateName) {
        String pageTitleClassName = "main-title";
        String title = baseStep.getInnerText(getDriver().findElement(By.className(pageTitleClassName)));
        Ensure.that(title.equalsIgnoreCase(DBConnection.getDBInstance().companyName)).isTrue();


    }

    @Then("{actor} should see fields in Factory page")
    public void heShouldSeeFieldsInInformationSection(Actor actor, DataTable table) {
        String path = "//label";

        ArrayList<String> expectedResultList = helper.getValidations(table);

        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        Ensure.that(elements.size()).isGreaterThanOrEqualTo(expectedResultList.size());

        String expStr = "//label[contains(text(), '<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });

    }
    private void validateContentOfAvailabilityAndDowntime(Actor actor){

        /*
        Total 13 sections in the page : //main//div[@class='v-application--wrap']/div/div
         */
        // section:
        String sectionPath = "//main//div[@class='v-application--wrap']/div";
        List<WebElement> elements = getDriver().findElements(By.xpath(sectionPath));
        Ensure.that(elements.size()).isEqualTo(6);

        // 1st section
        String subSectionPath = "//h2[contains(text(), 'How to reduce the causes of downtime?')]";
        subElementIsPresent(actor, elements.get(0), subSectionPath);

        // 2nd section
        subSectionPath = "//p[contains(text(), 'Please use the filters below to create your report')]";
        subElementIsPresent(actor, elements.get(1), subSectionPath);
        /*
        Dropdown list : From, To, Production Units, Shifts, Productions, Type of Cause
        Button : Apply
         */

        // 3rd section

        subSectionPath = "//div[contains(text(), 'This page uses “weighted averages” based on production time.')]";
        subElementIsPresent(actor, elements.get(2), subSectionPath);

        // 4th section : 6 tiles : Availability, Uptime Total Duration,Downtime Total Duration,Planned Downtime Total Duration,Unplanned Downtime Total Duration,Unjustified Downtime Total Duration
        subSectionPath = "//p";
        List<WebElement> subElements = elements.get(3).findElements(By.tagName(subSectionPath));

        Ensure.that(subElements.size()==6).isTrue();

        // 5th section : 2 charts - Top 5 Causes of planned downtime, Top 5 Causes of unplanned downtime
        subSectionPath = "//h3";
        subElements = elements.get(4).findElements(By.tagName(subSectionPath));
        Ensure.that(subElements.size()==2).isTrue();

        // 6th section : Average availability/Downtime Occurences/Downtime Total
        //               Period/Day/Week
        //               chart id = dtbyprodline
        subSectionPath = "//div[@id = 'dtbyprodline']";
        subElementIsPresent(actor, elements.get(5), subSectionPath);

        // 6th section second div : table
        subSectionPath = "//div[@class = 'v-data-table__wrapper']";

        WebElement element = getDriver().findElement(By.xpath(subSectionPath));

        Ensure.that(element.isDisplayed()).isTrue();

//        subElementIsPresent(actor, elements.get(0), subSectionPath);
        //  "//thead[@class= 'v-data-table-header']/tr                4 columns : Production Unit, Average Availability(%), Downtime Occurences, Downtime Total
        //  "//tbody/tr"                                              total row = PUs + 1(Total/Average)

    }
    private void subElementIsPresent(Actor actor, WebElement pElement, String path){
        WebElement element = pElement.findElement(By.xpath(path));
        Ensure.that(element.isDisplayed()).isTrue();
    }
    private void mouseHOverProfileIcon(Actor actor){
        WebElement element = getDriver().findElement(By.xpath("//button[contains(@title, 'Settings')]"));
        //Creating object of an Actions class
        Actions action = new Actions(getDriver());

        //Performing the mouse hover action on the target element.
        action.moveToElement(element).perform();

    }

    private WebElement waitElementForPresent(String path){

        @SuppressWarnings({ "deprecation" })
        Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(120))
                .pollingEvery(Duration.ofSeconds(10)).ignoring(NoSuchElementException.class);

        WebElement element = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver arg0) {
                WebElement element = getDriver().findElement(By.xpath(path));
                if (element.isDisplayed()) {
                    System.out.println("Element is Found");
                }
                return element;
            }
        });
        return element;
    }



    @Then("{actor} should be in company overview page")
    public void heShouldBeInCompanyOverviewPage(Actor actor) throws InterruptedException {
//        String path = "//div[contains(text(), ' Customize your view → ')]";
//        WebElement element = getDriver().findElement(By.xpath(path));
        WebElement element = getDriver().findElement(By.id("companyOverview"));
        Ensure.that(element.isDisplayed()).isTrue();

        Thread.sleep(2000);
//        waitElementForPresent(path);
    }

    @Then("{actor} should be able to see saved filter")
    public void heShouldBeAbleToSeeSavedView(Actor actor) {
        String defaultStrClass = "v-alert__wrapper";
        String selectedStrClass = "v-chip_content";
        List<WebElement> isDefault = getDriver().findElements(By.className(defaultStrClass));
        List<WebElement> isSelected = getDriver().findElements(By.className(selectedStrClass));
        if(isDefault.size()>0){
            System.out.println("Default view");
            WebElement element = getDriver().findElement(By.className("v-alert__content"));
            String innerText = getInnerText(element);
            Ensure.that(innerText.equalsIgnoreCase(" Customize your view → ")).isTrue();
        }
        else{
            Ensure.that(isSelected.size()>0).isTrue();
        }

    }

    @When("{actor} clicks on Filter")
    public void heClicksOnFilter(Actor actor) throws InterruptedException {
        String str = "//button[@title='Toggle filters window to customize the view']";
        WebElement element = getDriver().findElement(By.xpath(str));
        baseStep.clickOnElement(element);
        List<WebElement> expectedElement = getDriver().findElements(By.xpath("//h2[contains(text(), 'Filters')]"));
        Ensure.that(expectedElement.size()>0).isTrue();


        //dismiss Filter wizard
        String bnt = "//span[@class='v-btn__content' and contains(text(), 'Cancel')]";

        Thread.sleep(2000);
        baseStep.clickOnElementByXpath(bnt);


    }

    @Then("he validates Period dropdown list of options")
     public void heValidatesPeriodDropdownListOfOptions(DataTable table) throws InterruptedException {
        String unitListStr = "//label[text() = 'Period']";
        List<WebElement> allLinks = baseStep.getDropDownListByXpath(unitListStr);
        baseStep.validateElementListAgainstDataTable(allLinks, table);
    }

    @When("he expands Admin in sidebar")
    public void heExpandsAdminInSidebar() throws InterruptedException {
        String adminPath = "//nav/div[@class='v-navigation-drawer__content']//div[text() = 'Admin']/ancestor::div[@role='button']";
        List<WebElement> element = getDriver().findElements(By.xpath(adminPath));
        Ensure.that(element.size()).isGreaterThan(0);
        String str = element.get(0).getAttribute("aria-expanded");
        if(str.equalsIgnoreCase("false")){
            baseStep.clickOnElement(element.get(0));
            Thread.sleep(1000);
        }
    }

    @Then("he should see PU dropdown list of default factory")
    public void heShouldSeePUDropdownListOfDefaultFactory() throws InterruptedException, SQLException {

        List<DBPUData> expectedResultList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();
        String filterName = "Production Units";
        String path = "//label[contains(text(), '<contains>')]";
        String newStr = path.replace("<contains>", filterName);
        WebElement el = getDriver().findElement(By.xpath(newStr));
        baseStep.clickOnElement(el);
        //handle timing issue in maven run
        Thread.sleep(1000);

        //get dropdoown list ID from grandparent element
        String grandParentPath = newStr + "/../..";
        WebElement element = getDriver().findElement(By.xpath(grandParentPath));
        String divId = element.getAttribute("aria-owns");

        String listItemPath = "//div[@id='" + divId + "']//div[@class='v-list-item__title' and contains(text(), '<contains>')]";

        expectedResultList.forEach((e) -> {
            System.out.println("Validate PU =" + e);
            String newStr2 = listItemPath.replace("<contains>", e.getPUName());
            System.out.println("replaced string =" + newStr2);
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr2));
            Ensure.that(elements.size()).isGreaterThan(0);
        });
    }

    @Then("{actor} validates data display options")
    public void heValidatesDataDisplayOptions(Actor actor, DataTable table) {
        String path = "//div[@title = 'Select the data metric to display']/div[@role='button']";
        baseStep.validateElementListAgainstDataTableByXpath(path, table);
    }

//    @Then("{actor} should see {String} section")
//    public void heShouldSeeStatisticsSection(Actor actor, String term, DataTable table) {
//        String path = "//h3[contains(text(), '" + term + "')]/parent::header";
//        List<WebElement> pElements = getDriver().findElements(By.xpath(path));
//        Ensure.that(pElements.size()).isGreaterThan(0);
//
//        ArrayList<String> expectedResultList = helper.getValidations(table);
//
//        String listItemPath = "//following-sibling::div//div[contains(text(), '<contains>')]";
//
//        expectedResultList.forEach((e) -> {
//            System.out.println("Validate PU =" + e);
//            String newStr2 = listItemPath.replace("<contains>", e);
//            System.out.println("replaced string =" + newStr2);
//            List<WebElement> elements = getDriver().findElements(By.xpath(newStr2));
//            Ensure.that(elements.size()).isGreaterThan(0);
//        });
//    }

    @Then("{actor} should see {string} section")
    public void heShouldSeeSection(Actor actor, String term, DataTable table) {
        String path = "//h3[contains(text(), '" + term + "')]/parent::header";
        List<WebElement> pElements = getDriver().findElements(By.xpath(path));
        Ensure.that(pElements.size()).isGreaterThan(0);

        ArrayList<String> expectedResultList = helper.getValidations(table);

        String listItemPath = "//following-sibling::div//div[contains(text(), '<contains>')]";

        expectedResultList.forEach((e) -> {
            System.out.println("Validate PU =" + e);
            String newStr2 = listItemPath.replace("<contains>", e);
            System.out.println("replaced string =" + newStr2);
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr2));
            Ensure.that(elements.size()).isGreaterThan(0);
        });
    }
}
