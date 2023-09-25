package starter.Pages;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.Keys;

public class TilePlusMainPageSteps {

    public static Performable Login(String name, String pw) {
        return Task.where("{0} login for '" + name + "'",
                Enter.theValue(name)
                        .into(LoginPage.SIGN_NAME)
                        .thenHit(Keys.ENTER),
                Enter.theValue(pw)
                        .into(LoginPage.PASSWORD)
                        .thenHit(Keys.ENTER)
        );
    }

    public static Performable expandSideBar(Actor actor) {
        return Task.where("{0} login for ",
                Click.on(TileMailPage.TILEPLUS_ICON)
        );
      }

    public static Performable setDefaultProfile(Actor actor) {
        return Task.where("{0} login for ",
                Click.on(TileMailPage.PROFILE_ICON)
//                ,
//                Click.on(TileMailPage.ENGLISH_MODE),
//                Click.on(TileMailPage.DARK_MODE)
        );
    }


}

