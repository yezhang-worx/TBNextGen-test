package starter.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.ensure.web.ElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import starter.Common.DBConnection;
import starter.Common.HelperClass;
import starter.Common.RegisteredUser;
import starter.Data.*;
import starter.navigation.NavigateTo;
import starter.Pages.LoginPageSteps;
import starter.Pages.TilePlusMainPageSteps;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import static net.serenitybdd.core.Serenity.getDriver;


public class TilePlusOverviewStepDefinitions {

    boolean isSF4 = false;

    Actor actor = Actor.named("User");

    HelperClass helper = new HelperClass();
    baseStepDefinition baseStep = new baseStepDefinition();

    RegisteredUser MANAGEMENT = new RegisteredUser(
            "MANAGEMENT", "ellenTest@worximity.com", "Test1234");
    RegisteredUser WXADMIN = new RegisteredUser("WXADMIN", "wxadmin@Test.com", "Test1234");

    @Given("{actor} is accessing TilePlus")
    public void researchingThings(Actor actor) {
        actor.wasAbleTo(NavigateTo.theTilePlusMainPage());
    }

    @When("{actor} logins as default {string}")
    public void loginTilePlus(Actor actor, String term) {
        RegisteredUser user;
        String userName;
        String password;
        switch (term.toLowerCase(Locale.ROOT)) {
            case "wxadmin":
                user = WXADMIN;
                userName = helper.getAdminUserName();
                password = helper.getAdminPW();
                break;
            default:
                user = MANAGEMENT;
                userName = helper.getUserName();
                password = helper.getPW();
        }

        actor.attemptsTo(
//                LoginPageSteps.Login(user.username(), user.password())
                LoginPageSteps.Login(userName, password)
        );

//
//        switch (term.toLowerCase(Locale.ROOT)) {
//            case "wxadmin":
//                LoginPageSteps.Login(WXADMIN.username(), WXADMIN.password());
//                break;
//            default:
//                LoginPageSteps.Login(MANAGEMENT.username(), MANAGEMENT.password());
//        }
//        setDefaultSetting(actor);
    }


    @When("{actor} logins Tilelyics as default {string}")
    public void loginTilelyics(Actor actor, String term) throws InterruptedException {

        String userName;
        String password;
        switch (term.toLowerCase(Locale.ROOT)) {
            case "wxadmin":
                userName = helper.getAdminUserName();
                password = helper.getAdminPW();
//                LoginPageSteps.loginTilelytics(WXADMIN.username(), WXADMIN.password());
                break;
            default:
                userName = helper.getUserName();
                password = helper.getPW();
//                LoginPageSteps.loginTilelytics(MANAGEMENT.username(), MANAGEMENT.password());
        }
        LoginPageSteps.loginTilelytics(userName, password);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
        wait.until(ExpectedConditions.urlContains("overview"));
    }

    @When("{actor} logins with {string} and {string}")
    public void heLoginsWithAnd(Actor actor, String email, String password) throws InterruptedException {
        actor.attemptsTo(
                LoginPageSteps.Login(email, password)
        );
        setDefaultSetting(actor);

    }

    private void setDefaultSetting(Actor actor) throws InterruptedException {
        List<WebElement> elements = getDriver().findElements(By.className("terms-title"));
        if (elements.size() > 0) {
            WebElement element = getDriver().findElement(By.xpath("//input[@name='termsOfUseConsentChoice']"));
            element.click();
            element = getDriver().findElement(By.id("continue"));
            element.click();
        }
        WebElement pElement = getDriver().findElement(By.cssSelector(".v-avatar.activator"));
        baseStep.clickOnElement(pElement);
        WebElement element = getDriver().findElement(By.xpath("//span[contains(text(),'English')]/parent::button"));
        Thread.sleep(1000);
        baseStep.clickOnElement(element);
        element = getDriver().findElement(By.xpath("//span[contains(text(),'Dark')]/parent::button"));
        baseStep.clickOnElement(element);

        //Dismiss profile panel
        baseStep.clickOnElement(pElement);
        System.out.println("Login!");
    }


    @When("{actor} selects {string} mode and {string} version")
    public void heSelectsModeAndVersion(Actor actor, String mode, String version) throws InterruptedException {
        baseStep.openProfileWizard();

        WebElement element = getDriver().findElement(By.xpath("//span[contains(text(),'" + version + "')]/parent::button"));
        Thread.sleep(1000);
        baseStep.clickOnElement(element);

        element = getDriver().findElement(By.xpath("//span[normalize-space()='" + mode + "']"));
        baseStep.clickOnElement(element);

        //Dismiss Profile pannel
        baseStep.dismissProfileWizard();
    }

    private void openProfilePanel(boolean expanded) {
        WebElement element = getDriver().findElement(By.xpath("//button[contains(@title, 'Settings')]"));
        //click user profile icon
        if (element.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
            //element = getDriver().findElement(By.cssSelector(".v-avatar.activator"));
            baseStep.clickOnElement(element);
        }
    }

    private void dismissProfilePanel(boolean expanded) {
        WebElement element = getDriver().findElement(By.xpath("//button[contains(@title, 'Settings')]"));
        //click user profile icon
        if (element.getAttribute("aria-expanded").equalsIgnoreCase("true")) {
            //element = getDriver().findElement(By.cssSelector(".v-avatar.activator"));
            baseStep.clickOnElement(element);
        }
    }

    @Then("{actor} should see user profile")
    public void should_see_user_profile(Actor actor) throws InterruptedException {

//        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
//        wait.until(ExpectedConditions.urlContains("overview"));

        WebElement element = getDriver().findElement(By.className("main-title"));
//        wait.until(ExpectedConditions.visibilityOf(element));

        String str = element.getText();
        String str2 = baseStep.getInnerText(element);
        actor.attemptsTo(
                Ensure.that(str2.equalsIgnoreCase("Overview")).isTrue()
        );

    }

    @When("{actor} logout")
    public void logout(Actor actor) throws InterruptedException {

        baseStep.goToMainPage();
        WebElement element = getDriver().findElement(By.cssSelector(".v-avatar.activator"));
        baseStep.clickOnElement(element);

        Thread.sleep(2000);
        WebElement elementLanguage = getDriver().findElement(By.xpath("//span[contains(text(),'English')]/parent::button"));
        baseStep.clickOnElement(elementLanguage);

        WebElement element2 = getDriver().findElement(By.xpath("//span[normalize-space()='Sign Out']"));
        if (!element2.isEnabled()) {
            System.out.println("Sign out button is not displayed.");
            element = getDriver().findElement(By.cssSelector(".v-avatar.activator"));
            baseStep.clickOnElement(element);
        }
        Thread.sleep(2000);
        baseStep.clickOnElement(element2);
    }


    @Then("{actor} should see links in side bars")
    public void heShouldSeeSideBarsWith(Actor actor, DataTable table) throws InterruptedException {
        Thread.sleep(2000);

        ArrayList<String> expectedResultList = helper.getValidations(table);
        String expStr = "//div[contains(text(),'<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            actor.attemptsTo(
                    Ensure.that(ElementLocated.by(newStr)).isDisplayed()
            );

        });
        baseStep.goToMainPage();
    }

    @Then("{actor} should see factories links in side bars")
    public void heShouldSeeSideBarsWithFactories(Actor actor) throws InterruptedException, SQLException {
        Thread.sleep(2000);
        TilePlusMainPageSteps.expandSideBar(actor);
        WebElement element = getDriver().findElement(By.xpath("//div[@class='v-list-item__title' and contains(text(), 'Admin')]"));
        baseStep.clickOnElement(element);

//        ArrayList<String> expectedResultList = helper.getValidations(table);
        List<DBFactoryData> expectedResultList = DBConnection.getDBInstance().getFactoryList();
        String expStr = "//div[contains(text(),'<contains>')]";
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


    @Then("{actor} should see profile display in {string} mode")
    public void heShouldSeeTileDisplayInMode(Actor actor, String mode) throws InterruptedException {
        String path = ".v-menu__content.theme--" + mode.toLowerCase();
        List<WebElement> elements = getDriver().findElements(By.cssSelector(path));
        actor.attemptsTo(Ensure.that(elements.size() > 0).isTrue());

        System.out.println("Mode = " + mode);
        Thread.sleep(1000);
//
//        actor.attemptsTo(
//                Ensure.that(ElementLocated.by(".v-menu__content.theme--" + mode.toLowerCase())).isDisplayed()
//        );
    }

    @When("{actor} accesses admin users")
    public void heAccessesAdminUsers(Actor actor) throws InterruptedException {
        String term = "Users";
        actor.attemptsTo(
                TilePlusMainPageSteps.expandSideBar(actor)
        );
        Thread.sleep(1000);
        WebElement element = getDriver().findElement(By.xpath("//div[@class='v-list-item__title' and contains(text(), 'Admin')]"));
        baseStep.clickOnElement(element);
        Thread.sleep(500);
        element = getDriver().findElement(By.xpath("//div[@class='v-list-item__title' and contains(text(), '" + term + "')]"));
        baseStep.clickOnElement(element);
    }

    @When("{actor} accesses admin {string}")
    public void heAccessesAdmin(Actor actor, String term) throws InterruptedException {
        actor.attemptsTo(
                TilePlusMainPageSteps.expandSideBar(actor)
        );
        WebElement element = getDriver().findElement(By.xpath("//div[@class='v-list-item__title' and contains(text(), 'Admin')]"));
        baseStep.clickOnElement(element);

//        need wait for sub list
        Thread.sleep(1000);
        element = getDriver().findElement(By.xpath("//div[@class='v-list-item__title' and contains(text(), '" + term + "')]"));
        baseStep.clickOnElement(element);
        baseStep.goToMainPage();
        baseStep.validatePageTitle(actor, term);
    }

    @When("{actor} should not see Admin in the sidebar")
    public void heShouldNotSeeAdmin(Actor actor) {
        actor.attemptsTo(
                TilePlusMainPageSteps.expandSideBar(actor)
        );
        // Admin is not display in the sidebar
        List<WebElement> m = getDriver().findElements(By.xpath("//div[@class='v-list-item__title' and contains(text(), 'Admin')]"));
        actor.attemptsTo(Ensure.that(m.size() < 1).isTrue());
    }

    @When("{actor} should see Admin in the sidebar")
    public void heShouldSeeAdmin(Actor actor) {
        actor.attemptsTo(
                TilePlusMainPageSteps.expandSideBar(actor)
        );
        // Admin is not display in the sidebar
        List<WebElement> m = getDriver().findElements(By.xpath("//div[@class='v-list-item__title' and contains(text(), 'Admin')]"));
        actor.attemptsTo(Ensure.that(m.size() > 0).isTrue());
    }


    @Then("{actor} should see users list associated to selected company")
    public void heShouldSeeUsersListAssociatedToSelectedCompany(Actor actor) throws InterruptedException {
        //TODO : validate against DB query
        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
        List<WebElement> columns = getDriver().findElements(By.xpath("//tbody/tr[1]/td"));

        //TODO: at least has one user in list
        actor.attemptsTo(Ensure.that(rows.size()).isGreaterThan(1));
        Thread.sleep(5000);
    }

    private void queryFactoriesUser() {
        String query = "select id, name from factories where account_id in (select account_id from users where display_name LIKE 'EllenTest' )";

    }

    @When("{actor} selects {string} version")
    public void heSelectsVersion(Actor actor, String language) throws InterruptedException {
        baseStep.openProfileWizard();

        WebElement element = getDriver().findElement(By.xpath("//span[contains(text(),'" + language + "')]/parent::button"));
        Thread.sleep(1000);
        baseStep.clickOnElement(element);

        Thread.sleep(2000); //wait for page refreshing
        baseStep.dismissProfileWizard();

    }


    @Then("{actor} should see default factory is displayed")
    public void heShouldSeeDefaultFactoryIsDisplayed(Actor actor) throws InterruptedException {
        String defaultFactory = DBConnection.getDBInstance().defaultFactoryName;
        //TODO : validate default factory
        String parentPath = "//div[@title='Switch factory' or @title = 'Changer d’usine' or @title = 'Cambiar fábrica' ]/div[@role='combobox']/div/div";
        WebElement pe = getDriver().findElement(By.xpath(parentPath));
        baseStep.clickOnElement(pe);
        //get the selected factory name
        String path = "//div[@role='listbox']/div[@aria-selected='true']/div/div";
        //wait for element display
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        if (elements.size() > 0) {
            WebElement element = getDriver().findElement(By.xpath(path));
            String factoryName = baseStep.getInnerText(element);//element.getText();
            actor.attemptsTo(Ensure.that(defaultFactory.equalsIgnoreCase(factoryName)).isTrue());
        }
        Thread.sleep(1000);
    }

    @Then("{actor} validates links of application")
    public void heValidatesLinksOfApplication(Actor actor) {
        String appicationsIcon = "//button[@title='Applications' or @title = 'Aplicaciones']";
        String TilelyticsLink = "//a[@title='Tilelytics']";
        WebElement element = getDriver().findElement(By.xpath(appicationsIcon));
        baseStep.clickOnElement(element);
        List<WebElement> apps = getDriver().findElements(By.xpath(TilelyticsLink));
        actor.attemptsTo(Ensure.that(apps.size() > 0).isTrue());
    }


    //    @Then("{actor} validate PU tiles in overview page")
    public void heValidatePUTiles_backup(Actor actor, DataTable table) throws SQLException {
        //TODO : validation against DB query -- production lines associated to user
        String puTilePath = "//div[@class='PU-list']/article";
        List<WebElement> eles = getDriver().findElements(By.xpath(puTilePath));

        String expStr = "//div[@class='PU-list']/article[@title='View Dashboard of “<contains>”']";

        ArrayList<String> expectedResultList = helper.getValidations(table);

        actor.attemptsTo(Ensure.that(eles.size() == expectedResultList.size()).isTrue());
        expectedResultList.forEach((e) -> {
            System.out.println("Validate option =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
            actor.attemptsTo(Ensure.that(elements.size() > 0).isTrue());
        });
    }

    @Then("{actor} validate PU tiles in overview page")
    public void heValidatePUTiles(Actor actor) throws SQLException {
        //TODO : validation against DB query -- production lines associated to user
        String puTilePath = "//div[@class='PU-list']/article";
        List<WebElement> eles = getDriver().findElements(By.xpath(puTilePath));

//        String expStr = "//div[@class='PU-list']/article[@title='View Dashboard of “<contains>”' or @title='Accéder au Tableau de bord de « <contains> »' or @title='Ver panel de control de “<contains>”']";
        String expStr = "//div[@class='PU-list']/article[contains(@title, '<contains>')]";

        List<DBPUData> expectedResultList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();

        actor.attemptsTo(Ensure.that(eles.size() == expectedResultList.size()).isTrue());
        expectedResultList.forEach((e) -> {
            System.out.println("Validate option =" + e);
            String puName = e.getPUName();
            String newStr = expStr.replace("<contains>", puName);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
            actor.attemptsTo(Ensure.that(elements.size() > 0).isTrue());
        });
    }


//    @When("{actor} validates PU list under dashboard menu")
//    public void heValidatesPUListUnderDashboard(Actor actor) throws SQLException {
//        actor.attemptsTo(
//                TilePlusMainPageSteps.expandSideBar(actor)
//        );
//
//        String expStr = "//div[@class='PU-list']/article[@title='View Dashboard of “<contains>”']";
//
//        List<DBPUData> expectedResultList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();
//
//        expectedResultList.forEach((e) -> {
//            System.out.println("------------------Validate option in sidebar =" + e);
//            String puName = e.getPUName();
//            String newStr = expStr.replace("<contains>", puName);
//            System.out.println("replaced string =" + newStr);
//            WebElement ele = getDriver().findElement(By.xpath(newStr));
//            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
//            actor.attemptsTo(Ensure.that(elements.size() > 0).isTrue());
//        });
//    }


    @Then("{actor} validates Timeline tile with {string} options")
    public void heValidatesTileOptions(Actor actor, String tileOptions, DataTable table) {
        //click on the second tile, which can be div or section
        String str = "((//section[@class='tile-slider'])[2]/*)[2]/button";
        WebElement element = getDriver().findElement(By.xpath(str));
        baseStep.clickOnElement((element));

        int optionCount = Integer.parseInt(tileOptions);
        List<WebElement> options = getDriver().findElements(By.xpath("//div[@role='listbox']/div[@role='option']"));
        ArrayList<String> expectedResultList = helper.getValidations(table);

        //there are 3 hidden options if SF!=4, so the size is options.size() = 20 and tile list = 17
        System.out.println("=============option size = " + options.size());
        System.out.println("=============tileOptions size = " + Integer.valueOf(optionCount));
        Boolean correctSize = (optionCount == expectedResultList.size()) || ((optionCount + 3) == expectedResultList.size());
        actor.attemptsTo(
                Ensure.that(correctSize).isTrue()
        );
//        Ensure.that(correctSize).isTrue();

        String expStr = "//div[@role='menu']//div[text()='<contains>']";

        expectedResultList.forEach((e) -> {
            System.out.println("Validate option =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
            actor.attemptsTo(Ensure.that(elements.size() > 0).isTrue());
        });

//        dismiss dropdown list
        baseStep.clickOnElement((element));

        baseStep.goToMainPage();
    }

    @Then("{actor} validates Giveaway tile with {string} options")
    public void heValidatesGiveawayTileWithOptions(Actor actor, String tileOptions, DataTable table) {
        //all tiles are tag section
        String str = "(//section[contains(@class,'tile-slider')])[2]/section[2]/button";
        WebElement element = getDriver().findElement(By.xpath(str));
        baseStep.clickOnElement((element));

        int optionCount = Integer.parseInt(tileOptions);
        List<WebElement> options = getDriver().findElements(By.xpath("//div[@role='listbox']/div"));
        actor.attemptsTo(Ensure.that(options.size() == optionCount).isTrue());

        String expStr = "//div[@role='menu']//div[text()='<contains>']";
        ArrayList<String> expectedResultList = helper.getValidations(table);
        expectedResultList.forEach((e) -> {
            System.out.println("Validate option =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
            actor.attemptsTo(Ensure.that(elements.size() > 0).isTrue());
        });
        baseStep.goToMainPage();
    }

    @Then("{actor} validates overview time period")
    public void heValidatesOverviewTimePeriod(Actor actor, DataTable table) {

        String path = "//div[@title='Click to select a date' or @title = 'Cliquez pour sélectionner une date' or @title = 'Haga clic para seleccionar una fecha']//button";
        WebElement element = getDriver().findElement(By.xpath(path));
        baseStep.clickOnElement(element);

        ArrayList<String> expectedResultList = helper.getValidations(table);
        String listItems = "//ul[@role='listbox']/li";
        List<WebElement> eles = getDriver().findElements(By.xpath(listItems));
        actor.attemptsTo(Ensure.that(eles.size() == expectedResultList.size()).isTrue());

        String expStr = "//div[contains(text(),'<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate option =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
            actor.attemptsTo(Ensure.that(elements.size() > 0).isTrue());
        });
    }

    @Then("{actor} validates overview KPI options")
    public void heValidatesOverviewKPIOptions(Actor actor, DataTable table) {
        String path = "//div[@role='button']//div[@class='v-input__append-inner']";
        WebElement element = getDriver().findElement(By.xpath(path));
        baseStep.clickOnElement(element);

        ArrayList<String> expectedResultList = helper.getValidations(table);

        actor.attemptsTo(Ensure.that(expectedResultList.size() > 0).isTrue());
        String expStr = "//div[text()='<contains>']";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate option =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));

        });

    }


    @Then("{actor} validates overview page titles")
    public void heValidatesOverviewPageLayout(Actor actor) throws InterruptedException {
        Thread.sleep(1000);
        WebElement elementh1 = getDriver().findElement(By.tagName("h1"));
        String titleh1 = baseStep.getInnerText(elementh1);
        WebElement elementh2 = getDriver().findElement(By.tagName("h2"));
        String titleh2 = baseStep.getInnerText(elementh2);
        actor.attemptsTo(
            Ensure.that(titleh1).isEqualToIgnoringCase("Overview"),
            Ensure.that(titleh2).isEqualToIgnoringCase("Production Units")
        );
    }

    @When("{actor} accesses dashboard PU {string}")
    public void heAccessesDashboardPU(Actor actor, String puName) throws InterruptedException {
        actor.attemptsTo(
                TilePlusMainPageSteps.expandSideBar(actor)
        );

        Thread.sleep(1000);
        WebElement element = getDriver().findElement(By.xpath("//div[contains(text(),'Dashboard')]"));
        baseStep.clickOnElement(element);

        Thread.sleep(1000);
        WebElement element2 = getDriver().findElement(By.xpath("//div[@class='v-list-group__items']//div[contains(text(),'" + puName + "')]"));
        System.out.println("PUname =" + puName);
        baseStep.clickOnElement(element2);

        String path = "//span[contains(text(), '" + puName + "')]";
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        actor.attemptsTo(
            Ensure.that(elements.size() > 0).isTrue()
        );

        Actions actions = new Actions(getDriver());
        actions.moveToElement(elements.get(0)).perform();

        //Template solution : move focus on main page
        baseStep.goToMainPage();
    }


    @Then("{actor} validates PU overview page with {string} tiles")
    public void heValidatesPUOverviewWithTiles(Actor actor, String count) {
        int tileCount = Integer.parseInt(count);
        //Active : v-window-item v-window-item--active

        String divStr = "//div[contains(@class, 'v-window-item--active')]//section[contains(@class, 'tile-slider')]/div";
        String sectionStr = "//div[contains(@class, 'v-window-item--active')]//section[contains(@class, 'tile-slider')]/section";
        List<WebElement> divElements = getDriver().findElements(By.xpath(divStr));
        List<WebElement> sectionElements = getDriver().findElements(By.xpath(sectionStr));

        //fist sectiio is a dummy one
        actor.attemptsTo(
                Ensure.that((sectionElements.size() + divElements.size() == tileCount + 1)).isTrue()
        );


        //must contains in-progress tile
        String inProgessTile = "//section[contains( @class,'in-progress')]"; //duplicated
        List<WebElement> elements = getDriver().findElements(By.xpath(inProgessTile));
        actor.attemptsTo(
                Ensure.that(elements.size() > 0).isTrue()
        );

    }

    @Then("{actor} should see two views")
    public void heShouldSeeTwoViews(Actor actor) {
        String timeline = "//span[contains(text(), ' Timeline ')]/parent::button";
        String giveaway = "//span[contains(text(), ' Giveaway ')]/parent::button";

        actor.attemptsTo(
            Ensure.that(getDriver().findElements(By.xpath(timeline)).size()).isGreaterThan(0),
            Ensure.that(getDriver().findElements(By.xpath(giveaway)).size()).isGreaterThan(0)
        );
        WebElement element = getDriver().findElement(By.xpath(timeline));
    }

    @When("{actor} clicks on {string} view button")
    public void heClicksOnViewButton(Actor actor, String viewName) {
        //"//span[contains(text(), ' Giveaway ')]/parent::button"
        String giveaway = "//span[contains(text(), ' " + viewName + " ')]/parent::button";
        baseStep.clickOnElement(getDriver().findElement(By.xpath(giveaway)));
    }


    @Then("{actor} validates PU Giveaway page with {string} tiles")
    public void heValidatesPUOverviewPaGeWithTiles(Actor actor, String count) {
        int tileCount = Integer.parseInt(count);

        //should contain 3 tiles
        String path = "//div[contains(@class, 'v-window-item--active')]//section/section[@sf='4']";
        List<WebElement> elements = getDriver().findElements(By.xpath(path));

        actor.attemptsTo(
                Ensure.that(elements.size()).isEqualTo(tileCount)
        );
    }


    @Then("{actor} should see Shift Schedules filters in the page")
    public void heShouldSeeShiftSchedulesFilters(Actor actor, DataTable table) {
        validateFilters(table);
    }

    private void validateFilters(DataTable table) {

        String expStr = "//label[contains(text(), '<contains>')]";

        validateUIListAgainstTableByTextContains(expStr, table);

        ArrayList<String> expectedResultList = helper.getValidations(table);
        expectedResultList.forEach((e) -> {
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
            actor.attemptsTo(
                    Ensure.that(elements.size()).isGreaterThan(0)
            );

        });
    }

    @Then("{actor} should see root downtime category list of default factory")
    public void heShouldSeeRootDowntimeCategoryListOfDefaultFactory(Actor actor, DataTable table) {
        String defaultFactory = DBConnection.getDBInstance().defaultFactoryName;
        baseStep.isFactorySelected(actor, defaultFactory);
        validateRootDowntimeCategoryList(table);
    }

    private void validateRootDowntimeCategoryList(DataTable dtable) {

        ArrayList<String> expectedResultList = helper.getValidations(dtable);

        //parent path
        String pPath = "//div[@class='finder-container']/div/div/div/div";

        List<WebElement> elements = getDriver().findElements(By.xpath(pPath));
        actor.attemptsTo(
                Ensure.that(expectedResultList.size()).isEqualTo(elements.size())
        );


        for (int i = 0; i < elements.size(); i++) {
            WebElement iElement = elements.get(i);
            //first element of tag i
//            WebElement iElement = element.findElement(By.xpath("/div/i"));
            String categoryName = baseStep.getInnerText(iElement);
            AtomicBoolean found = new AtomicBoolean(false);
            expectedResultList.forEach((e) -> {
                if (e.contains(categoryName)) {
                    found.set(true);
                }
            });

            actor.attemptsTo(
                    Ensure.that(found.get()).isTrue()
            );

        }
        System.out.println("Done");
    }


//    private void validatePUList1(DataTable dataTable) {
//
//        ArrayList<String> expectedResultList = helper.getValidations(dataTable);
//        // Grab the table
//        WebElement table = getDriver().findElement(By.className("v-data-table__wrapper"));
//
//        // Now get all the TR elements from the table
//        List<WebElement> allRows = table.findElements(By.xpath("//tbody/tr"));
//        // And iterate over them, getting the cells
//
//        expectedResultList.forEach((e) -> {
//            boolean found = false;
//            Ensure.that(findInList(e, allRows, 0)).isTrue();
//        });
//    }

    private boolean findInList(String target, List<WebElement> allRows, int columnIndex) {
        boolean found = false;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String PUName = baseStep.getInnerText(cells.get(columnIndex));//cells.get(columnIndex).getText();
            if (target.equalsIgnoreCase(PUName)) {
                found = true;
            }
        }
        return found;
    }


    private boolean findInTableAndClick(String rowPath, int columnIndex, String findByText, int buttonIndex, String buttonPath) throws InterruptedException {

        Thread.sleep(1000);
        // Grab the table
        WebElement table = getDriver().findElement(By.className("v-data-table__wrapper"));

        WebElement foundRowElement = null;

        // Now get all the TR elements from the table
        List<WebElement> allRows = table.findElements(By.xpath(rowPath));
        // And iterate over them, getting the cells

        AtomicBoolean found = new AtomicBoolean(false);
        int count = 0;
        while (count < allRows.size() && !found.get()) {
            WebElement e = allRows.get(count);
//        allRows.forEach((e) -> {
            List<WebElement> columns = e.findElements(By.tagName("td"));
            WebElement element = columns.get(columnIndex);
            WebElement sfElement = columns.get(1);
            isSF4 = baseStep.getInnerText(sfElement).equalsIgnoreCase("SF4") ? true : false;
            String actualText = element.getText(); //empty
            actualText = baseStep.getInnerText(element);
            if (actualText.equalsIgnoreCase(findByText)) {
                WebElement bntElement = columns.get(buttonIndex);

                if (!buttonPath.isEmpty()) {
                    bntElement = bntElement.findElement(By.xpath(buttonPath));
                }
                else if(buttonPath.contains("href")){
                    bntElement = bntElement.findElement(By.partialLinkText("measurement-activity"));
                }
                baseStep.clickOnElement(bntElement);
                found.set(true);
                foundRowElement = element;
                break;
            }
            count++;
        }
        return foundRowElement!=null;
    }

//    private void  getTextInTable(String rowPath, int rowIndex, String findByText) throws InterruptedException {
//
//        Thread.sleep(1000);
//        // Grab the table
//        WebElement table = getDriver().findElement(By.className("v-data-table__wrapper"));
//
//        WebElement foundRowElement = null;
//
//        // Now get all the TR elements from the table
//        List<WebElement> allRows = table.findElements(By.xpath(rowPath));
//        // And iterate over them, getting the cells
//
//        AtomicBoolean found = new AtomicBoolean(false);
//        int count = 0;
//        while (count < allRows.size() && !found.get()) {
//            WebElement e = allRows.get(count);
//
//            List<WebElement> columns = e.findElements(By.tagName("td"));
//            WebElement element = columns.get(columnIndex);
//
//            String text = baseStep.getInnerText(element);
//
//        }
//
////        return foundRowElement;
//    }


    @Then("{actor} should see page with title {string}")
    public void heShouldSeePageWithTile(Actor actor, String pageTitle) {
        List<WebElement> elements = getDriver().findElements(By.xpath("//h1[text()='" + pageTitle + "']"));
        Ensure.that(elements.size()).isGreaterThan(0);
    }

    @Then("he should see text {string} displays in the page")
    public void heShouldSeeTextDisplaysInThePage(String expectedText) {
        String expStr = "//*[contains(text(), '<contains>')]";
        String newStr = expStr.replace("<contains>", expectedText);
        List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
        Ensure.that(elements.size() > 0).isTrue();

//        //Dismiss form
//        String path = "//span[contains(text(),'Back')]";
//        baseStep.clickOnElementByXpath(path);
    }

    @Then("he navigates back to devices list")
    public void heNavigatesBackToDeviceList() {
        //Dismiss form
        String path = "//span[contains(text(),'Back')]";
        baseStep.clickOnElementByXpath(path);
    }

    @Then("he should see PU form page with sections")
    public void heShouldSeePUFormPageWithSections(DataTable table) {
        String expStr = "//fieldset/legend[contains(text(), '<contains>')]";

        validateUIListAgainstTableByTextContains(expStr, table);
//        ArrayList<String> expectedResultList = helper.getValidations(table);
//        expectedResultList.forEach((e) -> {
//            System.out.println("Validate option =" + e);
//            String newStr = expStr.replace("<contains>", e);
//            System.out.println("replaced string =" + newStr);
//            WebElement ele = getDriver().findElement(By.xpath(newStr));
//            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
//            Ensure.that(elements.size() > 0);
//        });
    }

    @Then("he should see PU form page with delays")
    public void heShouldSeeFormPageWithDelays(DataTable dataTable) throws InterruptedException {
        //without delay, clicking on the second link "sample"
        Thread.sleep(1000);
        String delayBnt = "//button[contains(text(),'Delays')]";
        WebElement element = getDriver().findElement(By.xpath(delayBnt));
        baseStep.clickOnElement(element);

        String path = "//button[contains(text(),'Delays')]/following-sibling::div//fieldset";
        ///legend[contains(text(), ' Downtime Delay ')]
        List<WebElement> delayElements = getDriver().findElements(By.xpath(path));

        ArrayList<String> expectedResultList = helper.getValidations(dataTable);
        Ensure.that(delayElements.size()).isEqualTo(expectedResultList.size());

        expectedResultList.forEach((e) -> {
            String childPath = path + "/legend[contains(text(), ' " + e + " ')]";
            Ensure.that(baseStep.waitForExisted(childPath)).isTrue();
        });
    }

    @Then("he should see PU form page with Giveaway Sampling Session")
    public void heShouldSeePUFormPageWithGiveawaySamplingSession() {
        if (isSF4) {
            String delayBnt = "//button[contains(text(),'Giveaway Sampling Session')]";
            baseStep.clickOnElementByXpath(delayBnt);

            String inputPath = "//label[contains(text(), 'Required Samples *')]/following-sibling::input";
            Ensure.that(baseStep.waitForExisted(inputPath)).isTrue();
        }
    }

    @Then("in production unit page, he should see advance settings")
    public void inPageHeShouldSeeAdvanceSettings(DataTable dataTable) throws InterruptedException {
     //there is huge delay in UI
        Thread.sleep(5000);
        String btnPath = "//button[contains(text(),'Advanced settings')]";
        baseStep.clickOnElementByXpath(btnPath);

        ArrayList<String> expectedResultList = helper.getValidations(dataTable);
        expectedResultList.forEach((e) -> {
            String childPath = "//legend[contains(text(), ' " + e + " ')]";
            System.out.println("Production Unit advanced setting = " + childPath);

            Ensure.that(baseStep.waitForExisted(childPath)).isTrue();
        });

    }

    @Then("In (.*) page, he should see Advanced settings in Production unit page")
    public void heShouldSeePUFormPageAdvanceSettings() {
        if (isSF4) {
            String delayBnt = "//button[contains(text(),'Giveaway Sampling Session')]";
            baseStep.clickOnElementByXpath(delayBnt);

            String inputPath = "//label[contains(text(), 'Required Samples *')]/following-sibling::input";
            Ensure.that(baseStep.waitForExisted(inputPath)).isTrue();
        }
    }


    @And("he should see buttons in the page")
    public void heShouldSeeButtonsInThePage(DataTable table) {
        String path = "//span[contains(text(), '<contains>')]";
        validateUIListAgainstTableByTextContains(path, table);
    }

    private void validateUIListAgainstTableByTextContains(String path, DataTable table) {
        ArrayList<String> expectedResultList = helper.getValidations(table);
        expectedResultList.forEach((e) -> {
            System.out.println("Validate option =" + e);
            String newStr = path.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr));
            Ensure.that(elements.size()).isGreaterThan(0);
        });
    }


    @When("he clicks on {string} button")
    public void heClicksOnButton(String bntName) throws InterruptedException {
        //Mandatory to wait
        Thread.sleep(1000);
        String path = "//span[contains(text(), '<contains>')]";
        String newStr = path.replace("<contains>", bntName);

        baseStep.clickOnElement(getDriver().findElement(By.xpath(newStr)));
    }


    @Then("he should see production unit associations")
    public void heShouldSeeProductionUnitAssociations() {
        //TODO
        //validate PU associations against DB
        String path = "//ul[@class='associated-pu-list']";
        //        "//div[contains(text(), 'FlexPression Test')]";

        Ensure.that(getDriver().findElements(By.xpath(path)).size()).isGreaterThan(0);

    }

    @When("{actor} creates a new shift")
    public void heOpensShiftEditForm(Actor actor) {
        String path = "//span[contains(text(), 'New shift')]";
        baseStep.clickOnElement(getDriver().findElement(By.xpath(path)));
//        baseStep.validatePageTitle(actor, "Create new Shift Schedule");
    }


    @When("{actor} should see shift schedule page")
    public void heShouldSeeShiftSchedulePage(Actor actor) {
        baseStep.validatePageTitle(actor, "Create new Shift Schedule");
    }

    @When("{actor} cancels shift creation")
    public void heCancelCreation(Actor actor) {
        String path = "//span[contains(text(), 'New shift')]";
        baseStep.clickOnElement(getDriver().findElement(By.xpath(path)));
    }

    @And("{actor} should see PUs shifts display with default factory")
    public void heShouldSeePUsOfDefaultFactory(Actor actor) throws SQLException {
        String defaultFactory = DBConnection.getDBInstance().defaultFactoryName;
        baseStep.isFactorySelected(actor, defaultFactory);
        String path = "//article//button/span[contains(text(), '<contains>')] ";

//        ArrayList<String> expectedResultList = helper.getValidations(table);
        List<DBPUData> expectedResultList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();
        expectedResultList.forEach((e) -> {
            System.out.println("Validate PU =" + e.getPUName());
            String newStr = path.replace("<contains>", e.getPUName());
            System.out.println("replaced string =" + newStr);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            //expand PU with WS
            WebElement parent = ele.findElement(By.xpath("./.."));
            String status = parent.getAttribute("aria-expanded");
            if (status == null) {
                baseStep.clickOnElement(parent);
                System.out.println("Click on element : " + newStr);
            }

//            WebElement bntSibling = getDriver().findElement(By.xpath(newStr +"/../following-sibling::div"));
//            System.out.println("PU should be expended : " + bntSibling.getAttribute("style"));
//           //style != "display: none;"
//           Ensure.that(bntSibling.getAttribute("style").isEmpty());

            //TODO : shift list against DB query
        });

    }


    @Then("{actor} should see dropdown list of {string} of factory {string}")
    public void heShouldSeeDropdownListOfOfFactory(Actor actor, String filterName, String factoryName) {
        baseStep.isFactorySelected(actor, factoryName);
    }

    @Then("{actor} should see dropdown list of {string}")
    public void heShouldSeeDropdownListOf(Actor actor, String filterName, DataTable table) throws InterruptedException, SQLException {

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

        ArrayList<String> expectedResultList = helper.getValidations(table);
//        List<PUData> expectedResultList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();

        expectedResultList.forEach((e) -> {
            System.out.println("Validate PU =" + e);
            String newStr2 = listItemPath.replace("<contains>", e);
            System.out.println("replaced string =" + newStr2);

//            WebElement ele = getDriver().findElement(By.xpath(newStr2));

            List<WebElement> elements = getDriver().findElements(By.xpath(newStr2));
            Ensure.that(elements.size()).isGreaterThan(0);
        });
    }


    @Then("he validates Unit dropdown list of options")
    public void heValidatesUnitDropdownList(DataTable table) throws InterruptedException {
        String unitListStr = "//label[text() = 'Unit']";
        List<WebElement> allLinks = baseStep.getDropDownListByXpath(unitListStr);
        Ensure.that(baseStep.validateElementListAgainstDataTable(allLinks, table)).isTrue();
    }


    @Then("he should see PU list of default factory")
    public void heShouldSeePUListOfSelectedFactory() throws InterruptedException, SQLException {
//        isFactoryEditable();
        String unitListStr = "//label[text() = 'Production Unit *']";
        List<WebElement> allLinks = baseStep.getDropDownListByXpath(unitListStr);

//       baseStep.validateDataTableAgainstListElements(allLinks, table);

        List<DBPUData> expectedResultList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();

        Ensure.that(allLinks.size() == expectedResultList.size()).isTrue();
        expectedResultList.forEach((e) -> {
            System.out.println("Option Name   = " + e);
            boolean flag = baseStep.findInElementList(e.getPUName(), allLinks);
            Ensure.that(flag).isTrue();
        });

    }

    private boolean isFactoryEditable() {
        String str = "//span[text() = ' Factory switching not allowed while editing another entity. ']";
        List<WebElement> elements = getDriver().findElements(By.xpath(str));
        Ensure.that(elements.size()).isGreaterThan(0);
        return elements.size() > 0;
    }


    @Then("{actor} should see table with columns")
    public void heShouldSeeTableWithColumns(Actor actor, DataTable table) throws InterruptedException {
        String path = "//thead/tr/th";
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        ArrayList<String> expectedResultList = helper.getValidations(table);


        //Edit any device : click on first //a[contains(text(), 'Edit')]
        String editPath = "//tbody/tr[1]/td[4]/a[contains(text(), 'Edit')]";
        WebElement el = getDriver().findElement(By.xpath(editPath));
        baseStep.clickOnElement(el);


//        for (int i = 0; i < elements.size(); i++) {
//            WebElement iElement = elements.get(i);
//            String categoryName = baseStep.getInnerText(iElement);
//            AtomicBoolean found = new AtomicBoolean(false);
//            expectedResultList.forEach((e) -> {
//                if(e.equalsIgnoreCase("[EMPTY]"))
//                    e="";
//                if (e.contains(categoryName)) {
//                    found.set(true);
//                }
//            });
//            Ensure.that(found.get()).isTrue();
//        }
    }


    @When("he adds a new product")
    public void heCreatesANewProduct() {

//        List<testCredentials>  usercredentials = new ArrayList<testCredentials>();
//        usercredentials = table.asList(testCredentials.class);
        String bnt = "//a[@href='/products/creation']";
        WebElement element = getDriver().findElement(By.xpath(bnt));
        baseStep.clickOnElement(element);

        Ensure.thatTheCurrentPage().currentUrl().contains("products/creation");

    }

    @When("he fills new product")
    public void heFillsNewProduct(List<ProductData> products) throws InterruptedException {
        //TODO: create one product
        ProductData productData;
        for (ProductData product : products) {
            Thread.sleep(1000);
            String sku = product.getSKU();
            fillElement("SKU *", sku);
            String description = product.getDescription();
            fillElement("Name", description);
            String category = product.getCategory();
            fillElement("Category", category);
            selectFromDropdownList("Unit", "kg", 1);

            String target = product.getTarget();
            fillElement("Target value", target);
            String associatedPUStr = product.getAssociatedPUStr();
            List<String> associatedPU = product.getAssociatedPU();

//            PROD : not PU name, just PU type
            if(associatedPUStr.toUpperCase(Locale.ROOT).contains("<SF")) {
                associatedPU.clear();
                associatedPU.add(helper.getSF4PU());
            }
            fillProductAssociatedPU(associatedPU);
//            selectFromDropdownList("Production Unit *", associatedPU);
            submitForm();

            System.out.println("product name        = " + sku);
            System.out.println("product description = " + description);
            System.out.println("product category    = " + category);
            System.out.println("product target      = " + target);
            System.out.println("product associatedPU= " + associatedPU);
            productData = new ProductData(sku, description, category, target, associatedPUStr);
            break;
        }

    }

    //     TODO : validate if it is used
    @When("he updates Production Unit  -- test")
    public void heUpdatesProductionUnit(List<ProductionUnitData> productionUnit) throws InterruptedException {

        ProductionUnitData productionUnitData;
        for (ProductionUnitData PU : productionUnit) {
            Thread.sleep(1000);
            String name = PU.getPUName();
            fillElement("Name *", name);
            String availability = PU.getAvailability();
            fillElement("Availability *", availability);
            String performance = PU.getPerformance();
            fillElement("Performance *", performance);
            String globalProductionUnitQuality = PU.getGlobalProductionUnitQuality();
            fillElement("Quality *", globalProductionUnitQuality);

            String convertedUnitName = PU.getConvertedUnitName();
            selectFromDropdownList("Unit *", convertedUnitName, 1);

            String costPerHour = PU.getCostPerHour();
            fillElement("CAD", costPerHour);

            String giveawaySamplingSession = PU.getGiveawaySamplingSession();
            baseStep.clickOnElementByXpath("//button[normalize-space()='Giveaway Sampling Session']");
            fillElement("Required Samples *", giveawaySamplingSession);

            //TODO : fill time
//            String downtimeDelay = PU.getDowntimeDelay();
//            fillElement("downtimeDelay", downtimeDelay);

            submitForm();

            break;
        }
    }

    private void fillProductAssociatedPU(List<String> associatedPU) throws InterruptedException {
        String addPU = "//button[@title = 'Define an additional production unit for this product']";
        int index = 1;
        for (Iterator<String> iter = associatedPU.iterator(); iter.hasNext(); ) {
            String PUname = iter.next();
            System.out.println("PU association = " + PUname);
            selectFromDropdownList("Production Unit *", PUname, index);
            fillPopupFieldOfProduct(index);
            //fill mandatory field for SF4 : Target Giveaway Percentage
            if (iter.hasNext()) {
                baseStep.clickOnElementByXpath(addPU);
                index++;
            }
        }
    }

    private void fillElement(String text, String value) {
        if (!value.trim().equalsIgnoreCase("N/A")) {
            //Name Category "Target value" "SKU *"
            String path = "//label[text()='" + text + "']/following-sibling::input";
            WebElement element = getDriver().findElement(By.xpath(path));
            baseStep.inputValue(path, value);
        }
    }


    private void selectFromDropdownList(String dropDownList, String text, int index) throws InterruptedException {
        String unitListStr = "//label[text() = '" + dropDownList + "']";

        List<WebElement> dropdownElements = getDriver().findElements(By.xpath(unitListStr));

        WebElement element = dropdownElements.get(index - 1);//getDriver().findElement(By.xpath(unitListStr));

        String forId = element.getAttribute("for");

//        baseStep.clickOnElement(getDriver().findElement(By.xpath("//div[@id='" + forId + "']")));

        String listId = forId.replace("input", "list");
        String bntList = "//div[@aria-owns= '" + listId + "']";//
        baseStep.clickOnElement(getDriver().findElement(By.xpath(bntList)));

        Thread.sleep(1000);

        String listStr = "//div[@id='" + listId + "']/div/div/div";

        String itemStr = "//div[@id='" + listId + "']/div/div/div[text()='" + text + "']";
        baseStep.clickOnElement(getDriver().findElement(By.xpath(itemStr)));
    }

    private void fillPopupFieldOfProduct(int index) {
        //check of Target Giveaway Percentage exists (which is mandatory for SF4 product
        //TODO hardcoded
        String TargetGiveawayPercentage = "90";
        fillSF4MandatoryFieldByXpath(index, TargetGiveawayPercentage);
    }

    private void fillSF4MandatoryFieldByXpath(int index, String value) {
        //check of Target Giveaway Percentage exists (which is mandatory for SF4
        String mandatoryField = "//ul[@class='associated-pu-list']/li";
        List<WebElement> giveAwayMandatoryList = getDriver().findElements(By.xpath(mandatoryField));
        //TODO
        WebElement mElement = giveAwayMandatoryList.get(index - 1);
        String childPath = "//label[text()='Target Giveaway Percentage']";
        List<WebElement> childElements = mElement.findElements(By.xpath(childPath));
        String path = "//ul[@class='associated-pu-list']/li[" + index + "]" + childPath;
        if (childElements.size() > 0) {
            baseStep.inputValue(path, value);
        }
    }

    private void submitForm() {
        String path = "//button[@title='Submit your entries [s]']";
        baseStep.clickOnElement(getDriver().findElement(By.xpath(path)));
    }

    @When("he searches product {string}")
    public void heSearchesProduct(String SKUName) throws InterruptedException {

        String path = "//label[text()='Search']/following-sibling::input";
        baseStep.waitForExisted(path);
//        Thread.sleep(1000);
        baseStep.inputValue(path, SKUName);
        System.out.println("Search = " + SKUName);
    }

    @Then("he should find product {string}")
    public void heShouldFindProduct(String SKUName) {
        String path = "//tbody/tr[1]/td[text()='" + SKUName + "']";
        Ensure.that(baseStep.waitForExisted(path)).isTrue();
    }

    @When("he deletes product {string}")
    public void heDeletesProduct(String SKUName) throws InterruptedException {
        String path = "//button[@title='Delete']";
        baseStep.clickOnElementByXpath(path);
        Thread.sleep(1000);
        baseStep.confirmDeletion();
        Thread.sleep(1000);
        System.out.println("Deleted SKU=" + SKUName);
    }

    @When("he edits the first product")
    public void heClicksOnFirstProduct() {
        //Edit button
        String path = "//a[@aria-label='Edit']";
        List<WebElement> edits = getDriver().findElements(By.xpath(path));
        baseStep.clickOnElement(edits.get(0));
    }

    @When("he edits product {string}")
    public void heClicksOnProduct(String SKUName) {
        //Edit button
        String path = "//a[@aria-label='Edit']";
        baseStep.clickOnElementByXpath(path);

//        String editBnt = "a[@aria-label='Edit']";
//        WebElement foundElement = findInTable("//tbody/tr", 0, productName, 3, editBnt);
//        if(foundElement!=null){
//            baseStep.clickOnElement(foundElement);
//        }
    }

    @Then("he should not find product {string}")
    public void heShouldNotFindProduct(String SKUName) {
        String path = "//td[text()='No matching records found']";
        Ensure.that(baseStep.waitForExisted(path)).isTrue();
    }

    @When("he update product name to {string}")
    public void heUpdateProductNameTo(String PName) throws InterruptedException {
        Thread.sleep(1000);
        fillElement("Name", PName);
        submitForm();
    }

    @Then("he should find product by name {string}")
    public void heShouldFindProductByName(String Pname) {
        String path = "//tbody/tr[1]/td[text()='" + Pname + "']";
        Ensure.that(baseStep.waitForExisted(path)).isTrue();
    }


    @Then("he validates converted Unit dropdown list options")
    public void heValidatesConvertedUnitDropdownListOptions(DataTable table) throws InterruptedException {
        String unitListStr = "//label[text() = 'Unit *']";
        List<WebElement> allLinks = baseStep.getDropDownListByXpath(unitListStr);
        Ensure.that(baseStep.validateElementListAgainstDataTable(allLinks, table)).isTrue();
    }


    @When("he clicks on the first Device of default factory")
    public void heClicksOnStDeviceOfDefaultFactory() {
        //TODO: why Editar
        String editPath = "//tbody/tr[1]/td[6]/a[@aria-label='Edit']";
        WebElement element = getDriver().findElement(By.xpath(editPath));
        baseStep.clickOnElement(element);
    }

    @Then("{actor} should see {string} dropdown list of {string}")
    public void heShouldSeeDropdownListOf(Actor actor, String listName, String filterName) throws SQLException, InterruptedException {

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

//        if(listName.equalsIgnoreCase("PU")) {
        List<DBPUData> expectedResultList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();
//        }
        expectedResultList.forEach((e) -> {
            System.out.println("Validate PU =" + e.getPUName());
            String newStr2 = listItemPath.replace("<contains>", e.getPUName());
            System.out.println("replaced string =" + newStr2);
            List<WebElement> elements = getDriver().findElements(By.xpath(newStr2));
            Ensure.that(elements.size()).isGreaterThan(0);
        });
    }

    @Then("{actor} should see {string} list of default factory")
    public void heShouldSeePUListOfDefaultFactory(Actor actor, String term) throws SQLException {
        List<String> expectedResultList = new ArrayList<>();
        switch (term.toUpperCase(Locale.ROOT)) {
            case "PRODUCTS":
                List<DBProductData> pList = DBConnection.getDBInstance().getProductListOfDefaultFactory();
                pList.forEach((e) -> {
                    expectedResultList.add(e.getDescription());
                    System.out.println("e.getDescription() =" + e.getDescription());
                });
                break;
            case "DEVICES":
                List<DBDeviceData> dList = DBConnection.getDBInstance().getDeviceListOfDefaultFactory();
                dList.forEach((e) -> {
                    expectedResultList.add(e.getId());
                });
                break;
            case "ALERTS":
                List<DBAlertData> aList = DBConnection.getDBInstance().getAlertsListOfDefaultFactory();
                aList.forEach((e) -> {
                    expectedResultList.add(e.getId());
                });
                break;

            default:
                List<DBPUData> puList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();
                puList.forEach((e) -> {
                    expectedResultList.add(e.getPUName());
                });
                break;
        }

        //check if multiple pages
        String path = "//div[@class= 'v-data-footer__pagination']";
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        if (elements.size() > 0) {
            String str = baseStep.getInnerText(elements.get(0));

            String[] splitStr = str.split("\\s+");
            String last = splitStr[splitStr.length - 1];
            int count = Integer.parseInt(last);
            helper.logToReport("DB item ", Integer.toString(expectedResultList.size()));
            helper.logToReport("Page elements ", Integer.toString(count));
            Ensure.that(count).isEqualTo(expectedResultList.size());

        } //check each item
        else {

            // Grab the table
            WebElement table = getDriver().findElement(By.className("v-data-table__wrapper"));

            // Now get all the TR elements from the table
            List<WebElement> allRows = table.findElements(By.xpath("//tbody/tr"));
            // And iterate over them, getting the cells

            System.out.println("allRows.size() =" + allRows.size());
            System.out.println("expectedResultList.size() =" + expectedResultList.size());

//            There are some product without PU association
            Ensure.that(allRows.size() <= expectedResultList.size()).isTrue();

            expectedResultList.forEach((e) -> {
                Ensure.that(findInList(e, allRows, 0)).isTrue();
            });
        }
    }

    @When("{actor} navigates to {string} of main sidebar")
    public void heNavigatesToOfMainSidebar(Actor actor, String optionText) {
        String factoryName = DBConnection.getDBInstance().defaultFactoryName;
        WebElement element = getDriver().findElement(By.xpath("//a[@title='Tile+']"));
        //Bring side bar
        baseStep.clickOnElement(element);
        String options = "//div[@class='v-list-item__title'][normalize-space()='" + optionText + "']";
        WebElement elementLink = getDriver().findElement(By.xpath(options));
        baseStep.clickOnElement(elementLink);

        baseStep.validatePageTitle(actor, optionText);
    }

    @When("{actor} opens profile wizard")
    public void heOpensProfileWizard(Actor actor) {

        baseStep.openProfileWizard();
    }


    @When("{actor} dismisses profile wizard")
    public void heDismissesProfileWizard(Actor actor) {
        WebElement pelement = getDriver().findElement(By.xpath("//button[contains(@title, 'Settings')]"));
        if (pelement.getAttribute("aria-expanded").equalsIgnoreCase("true")) {
            //element = getDriver().findElement(By.cssSelector(".v-avatar.activator"));
            baseStep.clickOnElement(pelement);
        }
    }

    @Then("{actor} should see supported languages in profile wizard")
    public void heShouldSeeSupportedLanguagesInProfileWizard(Actor actor, DataTable table) {
        ArrayList<String> expectedResultList = helper.getValidations(table);
        String expStr = "//div/article/div/button/span[@class ='v-btn__content' and contains(text(), '<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });
    }

    @Then("{actor} should see supported mode in profile wizard")
    public void heShouldSeeSupportedModeInProfileWizard(Actor actor, DataTable table) {


        ArrayList<String> expectedResultList = helper.getValidations(table);

        String expStr = "//div/article/div/button/span[@class ='v-btn__content' and contains(text(), '<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });
    }


    @And("he should see Appearance text is {string} and language text is {string}")
    public void heShouldSeeAppearanceTextIsAndLanguageTextIs(String Appearance, String language) {
        String expStr = "//h5[contains(text(), '<contains>')]";
        String newStr = expStr.replace("<contains>", Appearance);
        WebElement ele = getDriver().findElement(By.xpath(newStr));
        Ensure.that(ElementLocated.by(newStr)).isDisplayed();

        newStr = expStr.replace("<contains>", language);
        ele = getDriver().findElement(By.xpath(newStr));
        Ensure.that(ElementLocated.by(newStr)).isDisplayed();
    }

    @And("he should see sign out button text is {string}")
    public void heShouldSeeSignoutButtonTextIs(String signoutText) throws InterruptedException {
        List<WebElement> elements = getDriver().findElements(By.xpath("//span[normalize-space()= '" + signoutText + "']"));
        Ensure.that(elements.size() > 0).isTrue();
    }

    @And("he should see Term text is {string} and clicks it")
    public void heShouldSeeTermTextIs(String termText) {
        String footer = "//footer/a/span";
        String footerText = getDriver().findElement(By.xpath(footer)).getText();
        WebElement element = getDriver().findElement(By.xpath(footer));
        String footerText1 = baseStep.getInnerText(element);
        Ensure.that(footerText1.equalsIgnoreCase(termText)).isTrue();

        baseStep.clickOnElement(element);
    }

    @Then("he should see page title contains {string}")
    public void heShouldSeePageTitleContains(String title) throws InterruptedException {
        Thread.sleep(1000);
        Ensure.that(ElementLocated.by("h1")).text().contains(title.trim());
    }


    @And("{actor} should see views")
    public void heShouldSeeViews(Actor actor, DataTable table) {
        ArrayList<String> expectedResultList = helper.getValidations(table);


        String expStr = "//button[contains(@title,'<contains>')]";
        expectedResultList.forEach((e) -> {
            System.out.println("Validate button string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement ele = getDriver().findElement(By.xpath(newStr));
            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });
    }

    @Then("he should see three articles")
    public void heShouldSeeThreeArticles(DataTable table) {
        String path = "//aside/article/h3";
        List<WebElement> listItems = getDriver().findElements(By.xpath(path));
        Ensure.that(baseStep.validateElementListAgainstDataTable(listItems, table)).isTrue();
//        validateElementsAgainstDataTable
    }

    @Then("he should see PU tiles")
    public void heShouldSeePUTiles() throws SQLException {
        validatePUList();
    }

    private void validatePUList() throws SQLException {
        String path = "//div[contains(@class, 'PU-list')]/article//h3/span";
        List<WebElement> listItems = getDriver().findElements(By.xpath(path));
        List<DBPUData> expectedResultList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();
        actor.attemptsTo(Ensure.that(listItems.size() == expectedResultList.size()).isTrue());

        expectedResultList.forEach((e) -> {
            boolean found = baseStep.findInElementList(e.getPUName(), listItems);
            Ensure.that(found).isTrue();
        });
    }

    @When("he toggles the detailed view")
    public void heExpandsTheFirstTile() {
        String path = "//button[@title = 'Toggle the detailed view' or @title = 'Basculer la vue des détails' or @title = 'Alternar la vista detallada']";
        String bntPath = "//div[contains(@class, 'PU-list')]//footer/button";
        List<WebElement> listItems = getDriver().findElements(By.xpath(path));
        baseStep.clickOnElement(listItems.get(0));
    }

    private void validateTileExpand() throws SQLException {
        String tileBnts = "//div[contains(@class, 'PU-list')]/article//footer/button";
        List<WebElement> listItems = getDriver().findElements(By.xpath(tileBnts));

        //click on first tile
        baseStep.clickOnElement(listItems.get(0));


        List<DBPUData> expectedResultList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();
        actor.attemptsTo(Ensure.that(listItems.size() == expectedResultList.size()).isTrue());

        expectedResultList.forEach((e) -> {
            boolean found = baseStep.findInElementList(e.getPUName(), listItems);
            Ensure.that(found).isTrue();
        });
    }

    @Then("{actor} validates KPI list")
    public void heValidatesKPIListWithOptions(Actor actor, DataTable table) throws SQLException, InterruptedException {
        String KPIDropdownListPath = "//div[@class='v-input__control']/div[@role='button']";
        List<WebElement> lists = baseStep.getDropdownListByAttributeAriaOwns(KPIDropdownListPath);
        Ensure.that(baseStep.validateElementListAgainstDataTable(lists, table)).isTrue();
    }

    @Then("{actor} should see KPI list with details")
    public void heShouldSeeKPIListWithDetails(Actor actor, DataTable table) throws SQLException, InterruptedException {
        String KPIDropdownListPath = "//div[@class='v-input__control']/div[@role='button']";// "//aside/article/ul/li/div/span[@class='mr-1']";
        List<WebElement> lists = baseStep.getDropdownListByAttributeAriaOwns(KPIDropdownListPath);
        Ensure.that(baseStep.validateElementListAgainstDataTable(lists, table)).isTrue();
    }


    @When("he expends {string} menu")
    public void heExpendsMenu(String admin) {

        TilePlusMainPageSteps.expandSideBar(actor);
        WebElement element = getDriver().findElement(By.xpath("//div[@class='v-list-item__title' and contains(text(), '" + admin + "')]"));
        baseStep.clickOnElement(element);
    }



    @Then("{actor} validates time period list in Overview page")
    public void heValidatesTimePeriodInOverviewPage(Actor actor, DataTable table) throws SQLException, InterruptedException {
//        String dropdownListPath = "//div[@class='v-input__control']/div[@role='button']";
//        WebElement dropdownBnt = getDriver().findElement(By.xpath(dropdownListPath));
//        baseStep.clickOnElement(dropdownBnt);

        String dropdownListPath= "//div[@aria-haspopup='listbox' and @role='button']";

        List<WebElement> lists = baseStep.getDropdownListByAttributeAriaOwns(dropdownListPath);
        Ensure.that(baseStep.validateElementListAgainstDataTable(lists, table)).isTrue();
    }


    @And("{actor} should see buttons")
    public void heShouldSeeButtons(Actor actor, DataTable table) {
        ArrayList<String> expectedResultList = helper.getValidations(table);

        //There are 3 views with exactly same elements, find active view
        WebElement parentView = getDriver().findElement(By.cssSelector("div.v-window-item.v-window-item--active"));
        String expStr = "//button[@title='<contains>']";

        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement bnt = parentView.findElement(By.xpath(newStr));
            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });
    }

    @And("{actor} should not see buttons")
    public void heShouldNotSeeButtons(Actor actor, DataTable table) {
        ArrayList<String> expectedResultList = helper.getValidations(table);

        //There are 3 views with exactly same elements, find active view
        WebElement parentView = getDriver().findElement(By.cssSelector("div.v-window-item.v-window-item--active"));
        String expStr = "//button[@title='<contains>']";

        expectedResultList.forEach((e) -> {
            System.out.println("Validate string =" + e);
            String newStr = expStr.replace("<contains>", e);
            System.out.println("replaced string =" + newStr);
            WebElement bnt = parentView.findElement(By.xpath(newStr));
            Ensure.that(ElementLocated.by(newStr)).isDisplayed();
        });
    }

    @Then("{actor} should see {string} tiles")
    public void heShouldSeeTiles(Actor actor, String tileCount) {
        int optionCount = Integer.parseInt(tileCount) + 1; //with a hidden 1st section
//        there are 2 items
        String activeview= "//div[contains(@class,'v-window-item--active')]//section[@class='tile-slider']";
        WebElement parentElement = getDriver().findElement(By.xpath(activeview));
        int sectionCount = parentElement.findElements(By.xpath("section")).size();
        int divCount     = parentElement.findElements(By.xpath("div")).size();
        int totalTile = sectionCount + divCount;
//        Total 5 tiles, one is disconnected tile which is hidden

        actor.attemptsTo(
                Ensure.that(totalTile).isEqualTo(optionCount)
        );

    }


    @When("he views PU measures activity of {string} PU")
    public void heEditPUByName(String PUType) throws InterruptedException {
        Thread.sleep(5000);
        String PUName;
        if(PUType.equalsIgnoreCase("SF4"))
            PUName = helper.getSF4PU();
        else
            PUName = helper.getNoSF4PU();

        String editBnt = "a[contains(@href, 'measurement-activity')]";
        boolean isFound = findInTableAndClick("//tbody/tr", 0, PUName, 4, editBnt);
        Ensure.that(isFound).isTrue();
    }

    @When("{actor} accesses dashboard of the {string} PU")
    public void heAccessesDashboardOfStPU(Actor actor, String SFTYpe) throws InterruptedException, SQLException {

//        List<DBPUData> expectedResultList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();
        String puName;
        if(SFTYpe.equalsIgnoreCase("SF4"))
            puName = helper.getSF4PU();
        else
            puName = helper.getNoSF4PU();


        WebElement element = getDriver().findElement(By.xpath("//div[@role='button']//div[contains(text(),'Dashboard') or contains(text(),'Tableau de bord') or contains(text(),'Tablero')]"));
        baseStep.clickOnElement(element);

        Thread.sleep(1000);
        WebElement element2 = getDriver().findElement(By.xpath("//div[@class='v-list-group__items']//div[contains(text(),'" + puName + "')]"));
        System.out.println("PUname =" + puName);
        baseStep.clickOnElement(element2);




//        String path = "//span[contains(text(), '" + puName + "')]";
//        List<WebElement> elements = getDriver().findElements(By.xpath(path));
//        Ensure.that(elements.size() > 0).isTrue();
//        Actions actions = new Actions(getDriver());
//        actions.moveToElement(elements.get(0)).perform();

        Actions actions = new Actions(getDriver());
        WebElement pelement = getDriver().findElement(By.xpath("//button[contains(@title, 'Settings') or contains(@title, 'Paramètres') or contains(@title, 'Configuración')]"));
        actions.moveToElement(pelement).build().perform();



        //Template solution : move focus on main page
       baseStep.goToMainPage();
    }



    @When("he clicks on the {string} PU")
    public void heClicksOnPUByName(String PUType) throws InterruptedException {
        String PUName = getPUNameBySFType(PUType);
        Thread.sleep(5000);
        String editBnt = "a[@title='Edit']";
        boolean isFound = findInTableAndClick("//tbody/tr", 0, PUName, 4, editBnt);
        Ensure.that(isFound).isTrue();
    }

    @When("he edits the {string} PU")
    public void heEditsOnPUByName(String PUType) throws InterruptedException {
        String PUName = getPUNameBySFType(PUType);
        Thread.sleep(5000);
        String editBnt = "href : measurement-activity";
        boolean isFound = findInTableAndClick("//tbody/tr", 0, PUName, 4, editBnt);
        Ensure.that(isFound).isTrue();
    }

    private String getPUNameBySFType(String SFType){
        String PUName;
        switch(SFType.toUpperCase()) {
            case "SF4":
                PUName = helper.getSF4PU();
            case "SF3":
                PUName = helper.getNoSF4PU();
            default:
                PUName = helper.getNoSF4PU();
        }
        return PUName;
    }

    @When("he saves the form")
    public void heSavesTheForm() {
        submitForm();

        String popupPath = "//p[contains(text(), 'Congratulations! The Product with')]";
        List<WebElement> Popup = getDriver().findElements(By.xpath(popupPath));
        Ensure.that(Popup.size()).isGreaterThan(0);
    }

    @Then("he should see page with title PU name of type {string}")
    public void heShouldSeePageWithTitlePUName(String PUType) {
        String PUName = getPUNameBySFType(PUType);
        List<WebElement> elements = getDriver().findElements(By.xpath("//h1[text()='" + PUName + "']"));
        Ensure.that(elements.size()).isGreaterThan(0);
    }

//    @Then("he should see effective period range of {string} month")
//    public void heShouldSeeEffectivePeriodRangeOfMonth(Actor actor, String numberOfMonth) {
//        String bnt = "//button[@aria-label='Previous month']";
//        WebElement bntElement = getDriver().findElement(By.xpath(bnt));
//        int count = Integer.parseInt(numberOfMonth);
//        Boolean done = false;
//        //        Click previous month button 15 times for tilelytics
//        for (int i=0; i<count && !done; i++) {
//            done = helper.isAttribtuePresent(bntElement, "disabled");
//            bntElement = getDriver().findElement(By.xpath(bnt));
//            baseStep.clickOnElement(bntElement);
//        }
//        String att = bntElement.getAttribute("disabled");
//        helper.logToReport("Tilelytics time range = 15 months", att);
//
//        actor.attemptsTo(
//                Ensure.that(att.equalsIgnoreCase("true")).isTrue()
//        );
//
//
//        //dismiss dropdown list
//
//        String path = "//button[@title='Close this window']";
//        List<WebElement> elements = getDriver().findElements(By.xpath(path));
//        if(elements.size()==1) {
//            baseStep.clickOnElement(elements.get(0));
//        }
//        if(elements.size()>1){
//            baseStep.clickOnElement(elements.get(elements.size()-1));
//        }
//    }
}
