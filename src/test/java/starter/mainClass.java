package starter;

import io.cucumber.java.Before;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import starter.Common.RegisteredUser;
import starter.navigation.NavigateTo;

import static net.serenitybdd.screenplay.GivenWhenThen.*;

@RunWith(SerenityRunner.class)
public class mainClass {

    @Managed(driver = "chrome")
    WebDriver browser;

    Actor kitty = Actor.named("kitty");

    @Before
    public void setup() {
        kitty.can(BrowseTheWeb.with(browser));
    }

    @Test
    public void whenGoogleBaeldungThenShouldSeeEugen() {

        RegisteredUser SUPERVISOR = new RegisteredUser(
                "supervisor", "testEric@worximity.com", "Test1234");

        givenThat(kitty).wasAbleTo(NavigateTo.theTilePlusMainPage());

//        when(kitty).attemptsTo(LoginPageSteps.Login(SUPERVISOR.username(), SUPERVISOR.password());

//        then(kitty).should(seeThat(GoogleSearchResults.displayed(),
//                hasItem(containsString("Eugen (Baeldung)"))));
    }
}
