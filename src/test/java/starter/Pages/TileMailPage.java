package starter.Pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.ensure.web.ElementLocated;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class TileMailPage extends PageObject {

    public static final Target HEADINGClass =  Target.the("article identifier").located(By.className("mw-page-title-main"));

    public static final Target FFACTORY_ICON =  Target.the("factory icon identifier").locatedBy("#factoryIcon");
    public static final Target OVERVIEW =  Target.the("main title identifier").located(By.className("main-title"));
    public static final Target OVERVIEWCSS =  Target.the("article identifier").locatedBy(".main-title");

    public static final Target PROFILE_ICON = Target.the("User Profile icon").located(By.cssSelector("[class^='v-avatar activator']"));
    public static final Target LIGHT_MODE = Target.the(" select Dark mode").located(By.cssSelector("class^='v-btn v-btn--has-bg theme--dark v-size--default secondary'"));
    public static final Target DARK_MODE = Target.the(" select Dark mode").locatedBy("//span[contains(text(),'Dark')]/parent::button");
    public static final Target ENGLISH_MODE = Target.the("English mode").locatedBy("//span[contains(text(),'English')]/parent::button");
    public static final Target LOGOUT =  Target.the("logout identifier").locatedBy("//span[normalize-space()='Sign Out']");

    //Side bar
    public static final Target TILEPLUS_ICON = Target.the("Tile+ Icon").locatedBy("//a[@title='Tile+']");
    public static final Target ADMIN_LINK =  Target.the("Admin identifier").locatedBy("//div[contains(text(),'Admin')]");
    public static final Target ADMIN_USERS =  Target.the("Admin identifier").locatedBy("//div[contains(text(),'Admin')]");

    public static final Target IS_MODE_DARK =  Target.the("Admin identifier").locatedBy("//div[contains(text(),'Users')]");


    // users= div[contains(text(),'Users')]
    // //div[contains(text(),'Shift Schedules')]
    // //div[contains(text(),'Downtime Reasons')]
    // //div[contains(text(),'Products')]
    // //div[contains(text(),'Production Units')]

    @FindBy(className = "v-navigation-drawer__content")
    private WebElement sideBar = null;

    ////div[@class='v-navigation-drawer__content']//div[@class='v-list-item__title']

    /* list of links in sidebar
    //a//div[@class='v-list-item__content']/div
     */

    public ArrayList<String> getSideBarLinks() {
        ArrayList<String> strList = new ArrayList();
        List<WebElement> webEleList = getDriver().findElements(By.xpath("//a//div[@class='v-list-item__content']/div"));
        System.out.println(webEleList.size());
        for (WebElement element : webEleList){
            strList.add(element.getText());
        }
        return strList;
    }

    public static final Target links =  Target.the("side bar links identifier").locatedBy("//a//div[@class='v-list-item__content']/div");
    public static final Target adminLinks =  Target.the("side bar links identifier").locatedBy("//div[@class='v-list-group__items']/a//div[@class='v-list-item__title']");

    public static Performable validateHighLevelLinks(ArrayList<String> expectedLinks) {

        return Task.where("{0} should contain 1st level links'" + expectedLinks.toString() + "'",
                Click.on(By.className("v-navigation-drawer__content")),
                Ensure.that(ElementLocated.by("//a//div[@class='v-list-item__content']/div")).textValues().contains("Overview")
//                Ensure.that(ElementLocated.by("//a//div[@class='v-list-item__content']/div")).textValues().containsElementsFrom(expectedLinks)
        );
    }

    public static Performable validateLinksUnderAdmin(ArrayList<String> expectedLinks) {

        return Task.where("{0} should contain links under Admin'" + expectedLinks.toString() + "'",
                Ensure.that(adminLinks).textValues().contains("Users"),
                Ensure.that(adminLinks).textValues().containsElementsFrom(expectedLinks)
        );
    }

}
