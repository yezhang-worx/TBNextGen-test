package starter.navigation;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class NavigateTo {
    public static Performable theTilePlusMainPage() {
        return Task.where("{0} opens the TilePlus home page",
                Open.browserOn().the(TilePlusHomePage.class));
    }

    public static Performable theTilelyticsMainPage() {
        return Task.where("{0} opens the Tilelytics home page",
                Open.browserOn().the(TilelyticsHomePage.class));
    }
}
