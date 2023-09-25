package starter.Pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

//@DefaultUrl("https://tileplus-staging-next-release.azurewebsites.net/home")
class LoginPage extends PageObject {
    public static Target SIGN_IN_BUTTON = Target
            .the("Sign In field")
            .locatedBy("//span[normalize-space()='Sign In']");
    public static Target SIGN_NAME = Target
            .the("user field")
            .locatedBy("#signInName");
    public static Target PASSWORD = Target
            .the("PW field")
            .locatedBy("#password");
    public static Target SIGNIN_BUTTON = Target
            .the("Signin field")
            .locatedBy("#next");
    public static Target TILELYTICS_SIGNINBNT = Target
            .the("Tilelytics SignIn comfirm page")
            .locatedBy("//span[@class='v-btn__content' and contains(text(), ' Sign In')]");
}
