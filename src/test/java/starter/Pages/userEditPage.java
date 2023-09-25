package starter.Pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import starter.dataClass.UserData;

import java.util.List;

public class userEditPage extends PageObject {

    String targetuserID = null;
    String targetUserRowPath = "";
    String targetUserEditIconPath = "//a[@aria-label='Edit']";
    String targetUserDeleteBntPath = "//button[@title='Delete']";
    WebElement editElement = null;
    WebElement deleteElement = null;



    JavascriptExecutor jse = (JavascriptExecutor) getDriver();

    @Step("Enter display name")
    public void inputUserName(String inputString) {
        if(isValidInput(inputString)) {
            WebElement element = $(By.xpath("//label[normalize-space()='Display Name *']/following-sibling::input"));
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
    }

    @Step("Enter Email name")
    public void inputEmail(String inputString) {
          WebElement element = $(By.xpath("//label[normalize-space()='E-mail']/following-sibling::input"));
//        JavascriptExecutor jse = (JavascriptExecutor)getDriver();
//        jse.executeScript("arguments[0].value='" + inputString + "';", element);

        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).click().perform();
        actions.moveToElement(element).sendKeys(inputString).perform();
    }

    @Step("Enter password")
    public void inputPW(String inputString) {
        WebElement element = $(By.xpath("//input[@type='password']"));
//        JavascriptExecutor jse = (JavascriptExecutor)getDriver();
//        jse.executeScript("arguments[0].value='" + inputString + "';", element);

        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).click().perform();
        actions.moveToElement(element).sendKeys(inputString).perform();
    }

    @Step("select role")
    public void selectRole(String inputString) {
        if(isValidInput(inputString)) {
            WebElement element = $(By.xpath("//label[normalize-space()='Role *']/following-sibling::input"));
            JavascriptExecutor jse = (JavascriptExecutor) getDriver();
            jse.executeScript("arguments[0].click();", element);
            element = $(By.xpath("//div[contains(text(),'" + inputString + "')]"));
            jse.executeScript("arguments[0].click();", element);
        }
    }

    @Step("select All PUAssociation")
    public void selectPUAssociation(String inputString) {
        //TODO
        inputString = "All";
        WebElement element = $(By.xpath("//div[@class='v-list-item__content']//span[contains(text(), 'All')]"));
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].click();", element);
        jse.executeScript("arguments[0].value='" + inputString + "';", element);
    }

    @Step("active user")
    public void clickActiveUser() {
        WebElement element = $(By.className("v-input--selection-controls__ripple"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    @Step("save user")
    public void saveUser() {
        WebElement element = $(By.xpath("//span[normalize-space()='Save']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(element).click().perform();
    }

    @Step("create a new user")
    public void createUser() {
        WebElement element = $(By.xpath("//span[normalize-space()='User']"));
        clickOnElement(element);
    }

    @Step("assert in Admin->Users page")
    public void ShouldInUserPage() {
        WebElement element = getDriver().findElement(org.openqa.selenium.By.tagName("h1"));
        Ensure.that(element.getText()).isEqualToIgnoringCase("Users");
    }

    @Step("fill user form")
    public void fillUser(UserData user) {
        inputUserName(user.displayName);
        inputEmail(user.email);
        inputPW(user.password);
        selectRole(user.role);
        fillAdvancedOptions(user);
        setActiveUserOption(user.activeUser);
        saveUser();
    }
    @Step("update user form")
    public void updateUser(UserData user) {
        inputUserName(user.displayName);
        selectRole(user.role);
        fillAdvancedOptions(user);
        setActiveUserOption(user.activeUser);
        saveUser();
    }

    @Step("fill advanced options")
    public void  fillAdvancedOptions(UserData user) {
        if(user.role.equalsIgnoreCase("Operation")){
            String elementPath = "//label[contains(text(), 'Administration rights')]/parent::div/div/input";
            boolean adminRight = switchOption("Administration rights", user.adminRight);
            switchOption("Tilelytics access", user.TilelyticsAccess);
            if(adminRight){
                //TODO : Select the first factory
                WebElement factoryDropdownList = getDriver().findElement(By.xpath("//div[@role='button']//div[@class='v-input__append-inner']"));
                clickOnElement(factoryDropdownList);
                WebElement firstFactory = getDriver().findElement(By.xpath("((//div[@role='listbox'])[2]//div[@class='v-list-item__title'])[1]"));
                clickOnElement(firstFactory);
            }
            else{
                //TODO : PUasociation = All
                WebElement PUdropdown = getDriver().findElement(By.xpath("//div[@class='v-select__selections']"));
                clickOnElement(PUdropdown);
                WebElement firstAll = getDriver().findElement(By.xpath("//span[normalize-space()='All']"));
                clickOnElement(firstAll);
            }

        }
        if(user.role.equalsIgnoreCase("Display")){
            //TODO : PUasociation = all
            WebElement PUdropdown = getDriver().findElement(By.xpath("//div[@class='v-select__selections']"));
            clickOnElement(PUdropdown);
            WebElement firstAll = getDriver().findElement(By.xpath("//span[normalize-space()='All']"));
            clickOnElement(firstAll);
        }
    }

    private boolean switchOption(String optionName, boolean expectedStatus){
        String elementPath = "//label[contains(text(), '"+ optionName +"')]/parent::div/div/input";
        WebElement element = getDriver().findElement(By.xpath(elementPath));
        boolean currentStatus = element.getAttribute("aria-checked").equalsIgnoreCase("false") ? false:true;
        if(currentStatus!=expectedStatus) {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("arguments[0].click();", element);
        }
        element = getDriver().findElement(By.xpath(elementPath));
        currentStatus = element.getAttribute("aria-checked").equalsIgnoreCase("false") ? false:true;
        Ensure.that(currentStatus==expectedStatus);
        return currentStatus;
    }
    @Step("set active user option")
    public void setActiveUserOption(boolean activeStatus) {
        switchOption("Active", activeStatus);
    }


    @Step("get count of users")
    public int getUserCount() {
        WebElement element = getDriver().findElement(org.openqa.selenium.By.className("v-data-footer__pagination"));
        String theTextIWant = getInnerText(element);
        String last = theTextIWant.substring(theTextIWant.lastIndexOf(' ') + 1);
        int count = Integer.parseInt(last);
        return count;
    }

    @Step("find user by email")
    public String findUser(String emailStr) {
        targetUserRowPath = "";
        WebElement RowFound = null;
        WebElement BooksTable = getDriver().findElement(By.xpath("//tbody"));
        int rowNum = getDriver().findElements(By.xpath("//tbody/tr")).size();
        int colNum = getDriver().findElements(By.xpath("//tbody/tr[1]/td")).size();
        boolean found = false;
        for (int i = 1; i <= rowNum; i++) {
            String currentRowPath = "//tbody/tr[" + i + "]";
            String currentEmailPath = currentRowPath + "/td[2]";
            WebElement element = getDriver().findElement(By.xpath(currentEmailPath));
            String getText = element.getText(); //return ""
            String innerText = getInnerText(element);
            if (innerText.equalsIgnoreCase(emailStr)) {
                //Edit and delete icon column index = 7
                String editIcon = currentRowPath + targetUserEditIconPath;
                String deleteBnt = currentRowPath + targetUserDeleteBntPath;
                editElement = getDriver().findElement(org.openqa.selenium.By.xpath(editIcon));
                deleteElement = getDriver().findElement(org.openqa.selenium.By.xpath(deleteBnt));
                String link = editElement.getAttribute("href");
                //TODO : get user ID
                System.out.println("User href=" + link);
                targetuserID = link.substring(link.lastIndexOf('/') + 1);
                targetUserRowPath = currentRowPath;
                System.out.println("userID = " + targetuserID);
                found = true;
                break;
            }
        }
        return targetUserRowPath;
    }
    @Step("validate user information")
    public void validateNewUserInfo(UserData userData){
        WebElement element = getDriver().findElement(By.xpath(targetUserRowPath + "/td[3]"));
//        String getText = element.getText(); //return ""
        String innerText = getInnerText(element);
        if(!userData.role.isEmpty() && !userData.role.equalsIgnoreCase("-")){
            Ensure.that(userData.role.equalsIgnoreCase(innerText));
        }
    }


    @Step("Edit user searching by email")
    public void editTargetUser(){
        String path = targetUserRowPath + targetUserEditIconPath;
        WebElement element = getDriver().findElement(By.xpath(path));
        clickOnElement(element);
    }
    @Step("delete user searching by email")
    public void deleteUser(String email){
        targetUserRowPath = findUser(email);
        String path = targetUserRowPath + targetUserDeleteBntPath;
        WebElement element = getDriver().findElement(By.xpath(path));
        clickOnElement(element);
        //dismiss popup
        path = "//div[@class='actions']/button[@title = 'Delete']";
        element = getDriver().findElement(By.xpath(path));
        clickOnElement(element);
    }

    @Step("User should not editable")
    public void userNotEditable() {
        String path = "//span[contains(text(), ' is not allowed to manage users')]";
        List<WebElement> elements = getDriver().findElements(By.xpath(path));
        Ensure.that(elements.size()).isGreaterThan(0);
    }

    private void fillUserToUpdate(UserData user){
        //TODO : different role; active; PUassociation
        inputUserName(user.displayName);
        clickActiveUser();
    }
    private String getInnerText(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return (String) js.executeScript("return arguments[0].innerText;", element);
    }

    private void clickOnElement(WebElement element) {
        WebDriverWait wait = null;
        Actions actions = new Actions(getDriver());
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            actions.moveToElement(element).click().perform();
        }
    }
    private boolean isValidInput(String inputString){
       return  (!inputString.isEmpty() && !inputString.trim().equalsIgnoreCase("-"));
    }
}
